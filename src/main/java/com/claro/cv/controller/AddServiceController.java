package com.claro.cv.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientContactEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.entity.ServiceFileEntity;
import com.claro.cv.enums.TypeFileEnum;
import com.claro.cv.enums.TypeLocationEnum;
import com.claro.cv.enums.TypeMultivalueEnum;
import com.claro.cv.service.AddServiceService;
import com.claro.cv.service.UtilService;
import com.claro.cv.util.Constant;
import com.claro.cv.util.Messages;
import com.claro.cv.util.Util;


@Controller("addService")
public class AddServiceController implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private static Logger LOGGER = LogManager.getLogger(AddServiceController.class.getName());

   @Autowired
   private AddServiceService addServiceService;

   @Autowired
   private UtilService utilService;

   private BigInteger idClientEdit;

   private ClientProfileEntity clientProfile;

   private ClientServiceEntity clientService;

   private ArrayList<ClientContactEntity> listClientContact;

   private ArrayList<ServiceFileEntity> listServiceFile;

   private ArrayList<String> listTypeLocaltion;

   private String selectTypeLocaltion;

   private boolean international;

   private boolean diferentProvider;

   private String idCountry;

   private String idDepartament;

   private String idCity;

   private ArrayList<CountryEntity> listCountry;

   private ArrayList<DepartamentEntity> listDepartamento;

   private ArrayList<CityEntity> listCiudad;

   private ArrayList<MultivalueEntity> listTypeService;

   private ArrayList<MultivalueEntity> listTypeContact;

   private ArrayList<MultivalueEntity> listProviderLastMile;

   private ArrayList<MultivalueEntity> listSchedule;

   private boolean mainPoint;

   private boolean enlaceBackUp;

   private String nameUploadFile;

   private ArrayList<UploadedFile> listDetailEngineeringFileService;

   private UploadedFile lastSettingsnFile;

   private ServiceContactEntity serviceContact;

   @PostConstruct
   private void initialize() {
      clientProfile = new ClientProfileEntity();
      loadCountries();
      loadDepartaments();
      loadCities();
      loadMultiValues();

   }

   private void loadTypeLocation() {
      listTypeLocaltion = new ArrayList<String>();
      listTypeLocaltion.add(TypeLocationEnum.NATIONAL.getValue());
      listTypeLocaltion.add(TypeLocationEnum.INTERNATIONAL.getValue());

      selectTypeLocaltion = TypeLocationEnum.NATIONAL.getValue();
      international = false;
      diferentProvider = false;
   }

   private void loadCountries() {
      try {
         listCountry = utilService.loadCountries();
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_COUNTRY_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_COUNTRY_ERROR);
      }

   }

   private void loadDepartaments() {
      try {
         listDepartamento = utilService.loadDepartaments();
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_DEPARTAMENT_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_DEPARTAMENT_ERROR);
      }
   }

   private void loadCities() {
      try {
         listCiudad = utilService.loadCities();
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_CITY_ERROR);
      }
   }

   private void loadMultiValues() {
      try {
         listTypeContact = utilService.loadMultiValue(TypeMultivalueEnum.TIPO_CONTACTO);
         listTypeService = utilService.loadMultiValue(TypeMultivalueEnum.TIPO_SERVICIO);
         listProviderLastMile = utilService.loadMultiValue(TypeMultivalueEnum.PROVEEDOR_ULTIMA_MILLA);
         listSchedule = utilService.loadMultiValue(TypeMultivalueEnum.HORARIO_ATENCION);

      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al cargar los Tipos de Contacto", e);
         Util.addMessageFatal("Ha ocurrido un error al cargar los Tipos de Contacto");
      }
   }

   public void loadCitiesByDepartament() {
      try {
         if (idDepartament != null && idDepartament.length() > 0)
            listCiudad = utilService.loadCitiesByDepartament(idDepartament);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_CITY_ERROR);
      }
   }

   public String goEditAddService() {
      clientService = new ClientServiceEntity();
      clientService.setServiceContacts(new ArrayList<ServiceContactEntity>());
      loadClientProfile();
      idCountry = "";
      idDepartament = "";
      idCity = "";
      mainPoint = false;
      enlaceBackUp = false;
      listClientContact = new ArrayList<ClientContactEntity>();
      listServiceFile = new ArrayList<ServiceFileEntity>();
      listDetailEngineeringFileService = new ArrayList<UploadedFile>();
      serviceContact = new ServiceContactEntity();
      nameUploadFile = "";
      loadTypeLocation();

      return Util.getRedirect(Constant.ADMIN_NEW_SERVICE);
   }

   private void loadClientProfile() {
      try {
         clientProfile = addServiceService.loadClientProfileById(idClientEdit);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CLIENT_PROFILE_ERROR, e);
      }

   }

   public void changeTypeLocation() {
      if (TypeLocationEnum.INTERNATIONAL.getValue().equals(selectTypeLocaltion)) {
         international = true;
      } else {
         international = false;
      }
      idDepartament = "";
      idCity = "";
      idCountry = "";
   }

   public void changeProvider() {
      clientService.setCodeServiceLastMile(null);
      if (clientService.getIdProviderLastMile() == null
         || clientService.getIdProviderLastMile().length() == 0) {
         diferentProvider = false;
         return;
      }
      diferentProvider = true;
   }

   public void uploadFileE(FileUploadEvent event) {
      try {
         lastSettingsnFile = event.getFile();
         nameUploadFile = lastSettingsnFile.getFileName();

         Util.addMessageFatal(Messages.FILE_UPLOAD_SUCESS);
      } catch (Exception e) {
         Util.addMessageFatal(Messages.USER_FILE_ERROR);
         LOGGER.error(Messages.USER_FILE_ERROR, e);
      }
   }

   public void uploadFileIngenieriaService(FileUploadEvent event) {
      try {
         UploadedFile detailEngineeringFile = event.getFile();
         listDetailEngineeringFileService.add(detailEngineeringFile);
         Util.addMessageFatal(Messages.FILE_UPLOAD_SUCESS);
      } catch (Exception e) {
         Util.addMessageFatal(Messages.USER_FILE_ERROR);
         LOGGER.error(Messages.USER_FILE_ERROR, e);
      }
   }

   public void addContactService() {
      if (validateContactService()) {
         serviceContact.setClientService(clientService);
         clientService.getServiceContacts().add(serviceContact);
         serviceContact = new ServiceContactEntity();
      } else {
         Util.addMessageError(Messages.VALIDATE_CONTACT_SERVICE);
      }

   }

   private boolean validateContactService() {
      boolean validateName = serviceContact.getNameContact() != null
         && serviceContact.getNameContact().length() > 0;
      boolean validatePhone = serviceContact.getPhone() != null && serviceContact.getPhone().length() > 0;
      boolean validateMobil = serviceContact.getMobil() != null && serviceContact.getMobil().length() > 0;

      return validateName && (validatePhone || validateMobil) && serviceContact.getSchedule().length() > 0;
   }

   public void save() {
      if (!validateAddService()) {
         RequestContext.getCurrentInstance().execute("loadMapOrigin()");
         return;
      }
      addLastSettingsFile();
      addServiceFiles();
      addCityAndDepartamentToClientService();
      addCountryClientService();
      addMoreInformation();
      saveService();

      Util.addMessageInfoKeep(Messages.SAVE_SERVICE_SUCESSFULL + clientService.getAlias());
      RequestContext.getCurrentInstance().execute("loadMapOrigin()");

      Util.redirectFaces("/" + Constant.NAME_APLICATION + "/pages/admin_edit.xhtml");
   }

   private boolean validateAddService() {
      boolean validateMap = clientService.getLat() != null && clientService.getLat().length() > 0;
      boolean validateLocation;
      if (international) {
         validateLocation = idCountry != null && idCountry.length() > 0;
      } else {
         validateLocation = idDepartament != null && idDepartament.length() > 0 && idCity != null
            && idCity.length() > 0;
      }
      boolean validateDirection = clientService.getDirection() != null
         && clientService.getDirection().length() > 0;
      boolean validateAlias = clientService.getAlias() != null && clientService.getAlias().length() > 0;
      boolean validateTypeService = clientService.getTypeService() != null
         && clientService.getTypeService().length() > 0;
      boolean validateCodeService = clientService.getCodeService() != null
         && clientService.getCodeService().length() > 0;
      boolean validateLasSettingsFile = nameUploadFile != null && nameUploadFile.length() > 0;
      boolean validateCodeServiceUM = true;
      if (diferentProvider) {
         validateCodeServiceUM = clientService.getCodeServiceLastMile() != null
            && clientService.getCodeServiceLastMile().length() > 0;
      }
      boolean validateDescription = clientService.getDescription() != null
         && clientService.getDescription().length() > 0;
      boolean validateContactsService = clientService.getServiceContacts() != null
         && clientService.getServiceContacts().size() > 0;

      if (!validateMap) {
         Util.addMessageError(Messages.VALIDATE_MAP);
      }
      if (!validateLocation) {
         Util.addMessageError(Messages.VALIDATE_LOCATION);
      }
      if (!validateDirection) {
         Util.addMessageError(Messages.VALIDATE_DIRECTION);
      }
      if (!validateAlias) {
         Util.addMessageError(Messages.VALIDATE_ALIAS);
      }
      if (!validateTypeService) {
         Util.addMessageError(Messages.VALIDATE_TYPE_SERVICE);
      }
      if (!validateCodeService) {
         Util.addMessageError(Messages.VALIDATE_CODE_SERVICE);
      }
      if (!validateLasSettingsFile) {
         Util.addMessageError(Messages.VALIDATE_LAST_SETTINGS_FILE);
      }
      if (!validateCodeServiceUM) {
         Util.addMessageError(Messages.VALIDATE_CODE_SERVICE_UM);
      }
      if (!validateDescription) {
         Util.addMessageError(Messages.VALIDATE_DESCRIPTION);
      }
      if (!validateContactsService) {
         Util.addMessageError(Messages.VALIDATE_CONTACTS);
      }

      return validateMap && validateLocation && validateDirection && validateAlias && validateTypeService
         && validateCodeService && validateLasSettingsFile && validateCodeServiceUM && validateDescription
         && validateContactsService;

   }

   private void addLastSettingsFile() {
      if (lastSettingsnFile != null && lastSettingsnFile.getFileName() != null) {

         String fileName = lastSettingsnFile.getFileName();

         String idClient = clientProfile.getIdClient().toString();
         String idCodeService = clientService.getCodeService();

         String URL = createFile(fileName, TypeFileEnum.SETTINGS.getValue(), lastSettingsnFile, idClient,
            idCodeService);
         clientService.setLastSettingFile(setLastSettingFile(URL, fileName));

      }
   }

   private void addServiceFiles() {
      ArrayList<ServiceFileEntity> listServiceFile = new ArrayList<ServiceFileEntity>();
      for (UploadedFile uploadFile : listDetailEngineeringFileService) {
         ServiceFileEntity serviceFile;
         if (uploadFile != null && uploadFile.getFileName() != null) {
            serviceFile = new ServiceFileEntity();

            String fileName = uploadFile.getFileName();

            String idClient = clientProfile.getIdClient().toString();
            String idCodeService = clientService.getCodeService();

            String URL = createFile(fileName, TypeFileEnum.ENGINEERING_SERVICE.getValue(), uploadFile,
               idClient, idCodeService);
            serviceFile.setNameFile(fileName);
            serviceFile.setUrl(URL);
            serviceFile.setClientService(clientService);

            listServiceFile.add(serviceFile);
         }
      }
      listDetailEngineeringFileService = new ArrayList<UploadedFile>();

      clientService.setServiceFiles(listServiceFile);

   }

   private void addCityAndDepartamentToClientService() {
      try {
         if (idCity == null || idCity.length() == 0) {
            clientService.setCity(null);
            clientService.setDepartament(null);
            return;
         }
         CityEntity city = addServiceService.findCityById(idCity);
         clientService.setCity(city);
         clientService.setDepartament(city.getDepartament());
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
      }
   }

   private void addCountryClientService() {
      try {
         if (idCountry == null || idCountry.length() == 0) {
            clientService.setCountry(null);
            return;
         }
         CountryEntity country = addServiceService.findCountryById(idCountry);
         clientService.setCountry(country);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
      }
   }

   private void addMoreInformation() {
      String mainPointP = mainPoint == true ? "S" : "N";
      clientService.setMainPoint(mainPointP);
      String backup = enlaceBackUp == true ? "S" : "N";
      clientService.setBackup(backup);
      clientService.setState(Constant.STATE_ACTIVE);
   }

   private String createFile(String fileName, String type, UploadedFile upload, String idClient,
      String idCodeService) {
      try {
         String path = null;
         if (TypeFileEnum.SETTINGS.getValue().equals(type)) {
            path = Constant.PATH_UPLOAD_FILE_SETTINGS;
            path = path.replaceAll(Constant.TAG_ID_CLIENT, idClient);
            path = path.replaceAll(Constant.TAG_CODE_SERVICE, idCodeService);
         } else if (TypeFileEnum.ENGINEERING_SERVICE.getValue().equals(type)) {
            path = Constant.PATH_UPLOAD_FILE_ENGINEERING_SERVICE;
            path = path.replaceAll(Constant.TAG_ID_CLIENT, idClient);
            path = path.replaceAll(Constant.TAG_CODE_SERVICE, idCodeService);
         }

         File folder = new File(path);
         folder.mkdirs();

         String fileNameFinal = path + fileName;
         LOGGER.info("FILE NAME: " + fileNameFinal);
         InputStream in = upload.getInputstream();

         File fileTo = new File(fileNameFinal);
         OutputStream out = new FileOutputStream(fileTo);
         int read = 0;
         byte[] bytes = new byte[1024];
         while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
         }
         in.close();
         out.flush();
         out.close();

         return fileNameFinal;

      } catch (IOException e) {
         LOGGER.error(Messages.LOAD_LAST_SETTINGS_FILE, e);
         return null;
      }

   }

   private LastSettingFileEntity setLastSettingFile(String URL, String fileName) {
      LastSettingFileEntity lastSettingFile = new LastSettingFileEntity();
      lastSettingFile.setUrl(URL);
      lastSettingFile.setNameFile(fileName);
      try {
         lastSettingFile = addServiceService.saveLastSettingFile(lastSettingFile);
      } catch (Exception e) {
         LOGGER.error(Messages.LAST_SETTINGS_FILE_ERROR, e);
         Util.addMessageFatal(Messages.LAST_SETTINGS_FILE_ERROR);
      }

      return lastSettingFile;
   }

   public String getNameSchedule(String schedule) {
      for (MultivalueEntity multivalue : listSchedule) {
         if (multivalue.getCode().equals(schedule)) {
            return multivalue.getName();
         }
      }
      return null;
   }

   public BigInteger getIdClientEdit() {
      return idClientEdit;
   }

   public void saveService() {
      try {
         clientService.setClientProfile(clientProfile);
         addServiceService.save(clientService);
      } catch (Exception e) {
         Util.addMessageError(Messages.CREATE_SERVICE_ERROR);
         LOGGER.error(Messages.CREATE_SERVICE_ERROR, e);
      }
   }

   public void setIdClientEdit(BigInteger idClientEdit) {
      this.idClientEdit = idClientEdit;
   }

   public ClientProfileEntity getClientProfile() {
      return clientProfile;
   }

   public void setClientProfile(ClientProfileEntity clientProfile) {
      this.clientProfile = clientProfile;
   }

   public ClientServiceEntity getClientService() {
      return clientService;
   }

   public void setClientService(ClientServiceEntity clientService) {
      this.clientService = clientService;
   }

   public ArrayList<ClientContactEntity> getListClientContact() {
      return listClientContact;
   }

   public void setListClientContact(ArrayList<ClientContactEntity> listClientContact) {
      this.listClientContact = listClientContact;
   }

   public ArrayList<ServiceFileEntity> getListServiceFile() {
      return listServiceFile;
   }

   public void setListServiceFile(ArrayList<ServiceFileEntity> listServiceFile) {
      this.listServiceFile = listServiceFile;
   }

   public ArrayList<String> getListTypeLocaltion() {
      return listTypeLocaltion;
   }

   public void setListTypeLocaltion(ArrayList<String> listTypeLocaltion) {
      this.listTypeLocaltion = listTypeLocaltion;
   }

   public String getSelectTypeLocaltion() {
      return selectTypeLocaltion;
   }

   public void setSelectTypeLocaltion(String selectTypeLocaltion) {
      this.selectTypeLocaltion = selectTypeLocaltion;
   }

   public boolean isInternational() {
      return international;
   }

   public void setInternational(boolean international) {
      this.international = international;
   }

   public boolean isDiferentProvider() {
      return diferentProvider;
   }

   public void setDiferentProvider(boolean diferentProvider) {
      this.diferentProvider = diferentProvider;
   }

   public String getIdCountry() {
      return idCountry;
   }

   public void setIdCountry(String idCountry) {
      this.idCountry = idCountry;
   }

   public String getIdDepartament() {
      return idDepartament;
   }

   public void setIdDepartament(String idDepartament) {
      this.idDepartament = idDepartament;
   }

   public String getIdCity() {
      return idCity;
   }

   public void setIdCity(String idCity) {
      this.idCity = idCity;
   }

   public ArrayList<CountryEntity> getListCountry() {
      return listCountry;
   }

   public void setListCountry(ArrayList<CountryEntity> listCountry) {
      this.listCountry = listCountry;
   }

   public ArrayList<DepartamentEntity> getListDepartamento() {
      return listDepartamento;
   }

   public void setListDepartamento(ArrayList<DepartamentEntity> listDepartamento) {
      this.listDepartamento = listDepartamento;
   }

   public ArrayList<CityEntity> getListCiudad() {
      return listCiudad;
   }

   public void setListCiudad(ArrayList<CityEntity> listCiudad) {
      this.listCiudad = listCiudad;
   }

   public ArrayList<MultivalueEntity> getListTypeService() {
      return listTypeService;
   }

   public void setListTypeService(ArrayList<MultivalueEntity> listTypeService) {
      this.listTypeService = listTypeService;
   }

   public ArrayList<MultivalueEntity> getListTypeContact() {
      return listTypeContact;
   }

   public void setListTypeContact(ArrayList<MultivalueEntity> listTypeContact) {
      this.listTypeContact = listTypeContact;
   }

   public ArrayList<MultivalueEntity> getListProviderLastMile() {
      return listProviderLastMile;
   }

   public void setListProviderLastMile(ArrayList<MultivalueEntity> listProviderLastMile) {
      this.listProviderLastMile = listProviderLastMile;
   }

   public ArrayList<MultivalueEntity> getListSchedule() {
      return listSchedule;
   }

   public void setListSchedule(ArrayList<MultivalueEntity> listSchedule) {
      this.listSchedule = listSchedule;
   }

   public boolean isMainPoint() {
      return mainPoint;
   }

   public void setMainPoint(boolean mainPoint) {
      this.mainPoint = mainPoint;
   }

   public boolean isEnlaceBackUp() {
      return enlaceBackUp;
   }

   public void setEnlaceBackUp(boolean enlaceBackUp) {
      this.enlaceBackUp = enlaceBackUp;
   }

   public String getNameUploadFile() {
      return nameUploadFile;
   }

   public void setNameUploadFile(String nameUploadFile) {
      this.nameUploadFile = nameUploadFile;
   }

   public ArrayList<UploadedFile> getListDetailEngineeringFileService() {
      return listDetailEngineeringFileService;
   }

   public void setListDetailEngineeringFileService(ArrayList<UploadedFile> listDetailEngineeringFileService) {
      this.listDetailEngineeringFileService = listDetailEngineeringFileService;
   }

   public ServiceContactEntity getServiceContact() {
      return serviceContact;
   }

   public void setServiceContact(ServiceContactEntity serviceContact) {
      this.serviceContact = serviceContact;
   }

}
