package com.claro.cv.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientContactEntity;
import com.claro.cv.entity.ClientFileEntity;
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
import com.claro.cv.service.CreateService;
import com.claro.cv.service.UtilService;
import com.claro.cv.util.Constant;
import com.claro.cv.util.Messages;
import com.claro.cv.util.Util;


@Controller("create")
@Scope("session")
public class CreateController implements Serializable {

   /** 
    * 
    */
   private static final long serialVersionUID = 2605767751437345720L;

   private static Logger LOGGER = LogManager.getLogger(CreateController.class.getName());

   @Autowired
   private CreateService createService;

   @Autowired
   private UtilService utilService;

   private ClientProfileEntity clientProfile;

   private ClientContactEntity clientContact;

   private ClientServiceEntity clientService;

   private ArrayList<ClientServiceEntity> listClientService;

   private ServiceContactEntity serviceContact;

   private ArrayList<MultivalueEntity> listTypeContact;

   private ArrayList<MultivalueEntity> listTypeService;

   private ArrayList<MultivalueEntity> listProviderLastMile;

   private ArrayList<MultivalueEntity> listSchedule;

   private ArrayList<CountryEntity> listCountry;

   private ArrayList<DepartamentEntity> listDepartamento;

   private ArrayList<CityEntity> listCiudad;

   private String idCountry;

   private String idDepartament;

   private String idCity;

   private boolean puntoPrincipal;

   private boolean enlaceBackUp;

   private UploadedFile ultimaConfiguracionFile;

   private ArrayList<UploadedFile> listDetailEngineeringFile;

   private ArrayList<UploadedFile> listDetailEngineeringFileService;

   private ArrayList<String> listTypeLocaltion;

   private String nameUploadFile;

   private String selectTypeLocaltion;

   private boolean international;

   private boolean diferentProvider;

   @PostConstruct
   public void initializate() {
      if (!Util.validateLogIn()) {
         return;
      }
      clientProfile = new ClientProfileEntity();
      clientProfile.setClientContacts(new ArrayList<ClientContactEntity>());
      clientContact = new ClientContactEntity();
      listTypeContact = new ArrayList<MultivalueEntity>();
      listTypeService = new ArrayList<MultivalueEntity>();
      listProviderLastMile = new ArrayList<MultivalueEntity>();
      listSchedule = new ArrayList<MultivalueEntity>();
      clientService = new ClientServiceEntity();
      clientService.setServiceContacts(new ArrayList<ServiceContactEntity>());
      listClientService = new ArrayList<ClientServiceEntity>();
      serviceContact = new ServiceContactEntity();
      listDetailEngineeringFile = new ArrayList<UploadedFile>();
      listDetailEngineeringFileService = new ArrayList<UploadedFile>();
      nameUploadFile = "";

      loadMultiValues();
      loadCountries();
      loadDepartaments();
      loadCities();
      loadTypeLocation();
   }

   public void goCreate() {
      clientProfile = new ClientProfileEntity();
      clientProfile.setClientContacts(new ArrayList<ClientContactEntity>());
      clientContact = new ClientContactEntity();
      clientService = new ClientServiceEntity();
      clientProfile.setClientContacts(new ArrayList<ClientContactEntity>());
      clientService.setServiceContacts(new ArrayList<ServiceContactEntity>());
      listClientService = new ArrayList<ClientServiceEntity>();
      serviceContact = new ServiceContactEntity();
      listDetailEngineeringFile = new ArrayList<UploadedFile>();
      listDetailEngineeringFileService = new ArrayList<UploadedFile>();
      nameUploadFile = "";
      idDepartament = "";
      idCity = "";
      idCountry = "";

      Util.redirectFaces(Constant.CREATE_PAGE_URL);
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

   private void loadTypeLocation() {
      listTypeLocaltion = new ArrayList<String>();
      listTypeLocaltion.add(TypeLocationEnum.NATIONAL.getValue());
      listTypeLocaltion.add(TypeLocationEnum.INTERNATIONAL.getValue());

      selectTypeLocaltion = TypeLocationEnum.NATIONAL.getValue();
      international = false;
      diferentProvider = false;
   }

   public void loadCitiesByDepartament() {
      try {
         listCiudad = utilService.loadCitiesByDepartament(idDepartament);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_CITY_ERROR);
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

   public void changeProvide() {
      clientService.setCodeServiceLastMile(null);
      if (clientService.getIdProviderLastMile() == null
         || clientService.getIdProviderLastMile().length() == 0) {
         diferentProvider = false;
         return;
      }
      diferentProvider = true;
   }

   public void addContact() {
      if (validateContact()) {
         clientContact.setClientProfile(clientProfile);
         clientProfile.getClientContacts().add(clientContact);
         clientContact = new ClientContactEntity();
      } else {
         Util.addMessageError(Messages.VALIDATE_CONTACT_PROFILE);
      }

   }

   private boolean validateContact() {
      if (clientContact == null)
         return false;
      boolean validateName = clientContact.getNameContact() != null
         && clientContact.getNameContact().length() > 0;
      boolean validatePhone = clientContact.getPhone() != null && clientContact.getPhone().length() > 0;
      boolean validateMobil = clientContact.getMobil() != null && clientContact.getMobil().length() > 0;
      boolean validateTypeContact = clientContact.getTypeContact().length() > 0;

      return validateName && (validatePhone || validateMobil) && validateTypeContact;
   }

   public void addContactService() {
      if (validateContactService()) {
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

   public void addService() {
      if (validateAddService()) {
         if (ultimaConfiguracionFile != null && ultimaConfiguracionFile.getFileName() != null) {
            String fileName = ultimaConfiguracionFile.getFileName();
            String idClient = clientProfile.getIdClient().toString();
            String idCodeService = clientService.getCodeService();

            String URL = createFile(fileName, TypeFileEnum.SETTINGS.getValue(), ultimaConfiguracionFile,
               idClient, idCodeService);
            clientService.setLastSettingFile(setLastSettingFile(URL, fileName));
            clientService.setServiceFiles(addServiceFiles());
         }
         addCityAndDepartamentToClientService();
         addCountryClientService();
         String mainPoint = puntoPrincipal == true ? "S" : "N";
         clientService.setMainPoint(mainPoint);
         String backup = enlaceBackUp == true ? "S" : "N";
         clientService.setBackup(backup);
         clientService.setState(Constant.STATE_ACTIVE);
         listClientService.add(clientService);
         initializeService();

      }
      RequestContext.getCurrentInstance().execute("loadMapOrigin()");
   }

   private void initializeService() {
      clientService = new ClientServiceEntity();
      clientService.setServiceContacts(new ArrayList<ServiceContactEntity>());
      idCity = "";
      idDepartament = "";
      idCountry = "";
      puntoPrincipal = false;
      enlaceBackUp = false;
      ultimaConfiguracionFile = null;
      nameUploadFile = "";
      diferentProvider = false;
   }

   private ArrayList<ServiceFileEntity> addServiceFiles() {
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

      return listServiceFile;

   }

   private LastSettingFileEntity setLastSettingFile(String URL, String fileName) {
      LastSettingFileEntity lastSettingFile = new LastSettingFileEntity();
      lastSettingFile.setUrl(URL);
      lastSettingFile.setNameFile(fileName);

      try {
         lastSettingFile = createService.saveLastSettingFile(lastSettingFile);
      } catch (Exception e) {
         LOGGER.error(Messages.LAST_SETTINGS_FILE_ERROR, e);
         Util.addMessageFatal(Messages.LAST_SETTINGS_FILE_ERROR);
      }

      return lastSettingFile;
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

   private void addCountryClientService() {
      try {
         if (idCountry == null || idCountry.length() == 0) {
            clientService.setCountry(null);
            return;
         }
         CountryEntity country = createService.findCountryById(idCountry);
         clientService.setCountry(country);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
      }
   }

   private void addCityAndDepartamentToClientService() {
      try {
         if (idCity == null || idCity.length() == 0) {
            clientService.setCity(null);
            clientService.setDepartament(null);
            return;
         }
         CityEntity city = createService.findCityById(idCity);
         clientService.setCity(city);
         clientService.setDepartament(city.getDepartament());
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
      }
   }

   public void nextStep() {
      if (!validateUniqueProfile()) {
         Util.addMessageError(Messages.VALIDATE_PROFILE_UNIQUE);
         return;
      }
      if (clientProfile.getClientContacts().size() == 0) {
         Util.addMessageError(Messages.VALIDATE_PROFILE_CONTACTS);
      } else {
         RequestContext.getCurrentInstance().execute("next()");
      }

   }

   private boolean validateUniqueProfile() {
      try {
         ClientProfileEntity repeatedClient = createService.loadProfile(clientProfile.getIdClient());
         if (repeatedClient != null && repeatedClient.getNameClient().length() > 0) {
            return false;
         }
      } catch (Exception e) {
      }
      return true;

   }

   public void uploadFileE(FileUploadEvent event) {
      try {
         ultimaConfiguracionFile = event.getFile();
         nameUploadFile = ultimaConfiguracionFile.getFileName();

         Util.addMessageFatal(Messages.FILE_UPLOAD_SUCESS);
      } catch (Exception e) {
         Util.addMessageFatal(Messages.USER_FILE_ERROR);
         LOGGER.error(Messages.USER_FILE_ERROR, e);
      }
   }

   public void uploadFileIngenieria(FileUploadEvent event) {
      try {
         UploadedFile detailEngineeringFile = event.getFile();
         listDetailEngineeringFile.add(detailEngineeringFile);
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

   public String save() {
      try {
         if (!Util.validateLogIn())
            return null;
         if (!validateSave()) {
            RequestContext.getCurrentInstance().execute("loadMapOrigin()");
            return null;
         }

         setUpServiceContact();
         clientProfile.setClientServices(listClientService);
         clientProfile.setState(Constant.STATE_ACTIVE);
         createService.save(clientProfile);
         printClientProfile();
         saveDetailEngineeringFiles();
         Util.addMessageInfoKeep(Messages.SAVE_PROFILE_SUCESSFULL + clientProfile.getNameClient());
         clientProfile = new ClientProfileEntity();
         clientProfile.setClientContacts(new ArrayList<ClientContactEntity>());
         listClientService = new ArrayList<ClientServiceEntity>();
         return Util.getRedirect(Constant.CREATE_PAGE);
      } catch (Exception e) {
         LOGGER.error(Messages.SAVE_PROFILE_CLIENT_ERROR, e);
         Util.addMessageFatal(Messages.SAVE_PROFILE_CLIENT_ERROR);
         return null;
      }
   }

   private boolean validateSave() {
      boolean validateService = listClientService.size() > 0;
      if (!validateService) {
         Util.addMessageError(Messages.VALIDATE_SERVICES);
      }
      return validateService;
   }

   private void setUpServiceContact() {
      for (ClientServiceEntity service : listClientService) {
         service.setClientProfile(clientProfile);
         if (service.getServiceContacts() != null) {
            for (ServiceContactEntity serviceContact : service.getServiceContacts()) {
               serviceContact.setClientService(service);
            }
         }
      }

   }

   private void saveDetailEngineeringFiles() {
      for (UploadedFile uploadFile : listDetailEngineeringFile) {
         ClientFileEntity clientFile;
         if (uploadFile != null && uploadFile.getFileName() != null) {
            clientFile = new ClientFileEntity();
            clientFile.setClientProfile(clientProfile);

            String fileName = uploadFile.getFileName();
            String idClient = clientProfile.getIdClient().toString();

            String URL = createFile(fileName, TypeFileEnum.ENGINEERING.getValue(), uploadFile, idClient, null);
            clientFile.setNameFile(fileName);
            clientFile.setUrl(URL);
            saveClientFile(clientFile);
         }
      }
      listDetailEngineeringFile = new ArrayList<UploadedFile>();

   }

   private void saveClientFile(ClientFileEntity clientFile) {
      try {
         createService.saveClientFile(clientFile);
      } catch (Exception e) {
         LOGGER.error(Messages.CREATE_CLIENT_FILE_ERROR, e);
      }

   }

   private String createFile(String fileName, String type, UploadedFile upload, String idClient,
      String idCodeService) {
      try {
         String path = null;
         if (TypeFileEnum.ENGINEERING.getValue().equals(type)) {

            path = Constant.PATH_UPLOAD_FILE_ENGINEERING;
            path = path.replaceAll(Constant.TAG_ID_CLIENT, idClient);
         } else if (TypeFileEnum.SETTINGS.getValue().equals(type)) {

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

   private void printClientProfile() {
      inf("NOMBRE CLIENTE: " + clientProfile.getNameClient());
      inf("ID CLIENTE: " + clientProfile.getIdClient());
      inf("NIT: " + clientProfile.getNitClient());
      inf("SIZE CLIENT CONTACTS: " + clientProfile.getClientContacts().size());
      if (clientProfile.getClientContacts().size() > 0) {
         for (ClientContactEntity contact : clientProfile.getClientContacts()) {
            inf("NOMBRE DEL CONTACTO PERFIL: " + contact.getNameContact());
            inf("EMAIL DEL CONTACTO PERFIL:" + contact.getEmail());
            inf("TELEFONO DEL CONTACTO PERFIL:" + contact.getPhone());
            inf("CELULAR DEL CONTACTO PERFIL:" + contact.getMobil());
            inf("TIPO DEL CONTACTO PERFIL:" + contact.getTypeContact());
         }
      }

      inf("SIZE CLIENT SERVICE: " + clientProfile.getClientServices().size());
      if (clientProfile.getClientServices().size() > 0) {
         for (ClientServiceEntity service : clientProfile.getClientServices()) {
            inf("LAT SERVICE: " + service.getLat());
            inf("LNG SERVICE: " + service.getLng());
            if (service.getDepartament() != null) {
               inf("DEPARTAMENTO SERVICE: " + service.getDepartament().getName());
            }
            if (service.getCity() != null) {
               inf("DEPARTAMENTO SERVICE: " + service.getCity().getName());
            }

            inf("DIRECCION SERVICE: " + service.getDirection());
            inf("ALIAS SERVICE: " + service.getAlias());
            inf("TIPO SERVICE: " + service.getTypeService());
            inf("PUNTO PRINCIPAL SERVICE: " + service.getMainPoint());
            inf("ENLACE BACKUP SERVICE: " + service.getBackup());
            inf("PROVEEDOR DE ULTIMA MILLA SERVICE: " + service.getIdProviderLastMile());
            inf("CODIGO DE SERVICIO UM: " + service.getCodeServiceLastMile());

            if (service.getServiceContacts().size() > 0) {
               for (ServiceContactEntity contact : service.getServiceContacts()) {
                  inf("NAME CONTACT SERVICE: " + contact.getNameContact());
                  inf("NAME CONTACT SERVICE: " + contact.getPhone());
                  inf("NAME CONTACT SERVICE: " + contact.getMobil());
                  inf("NAME CONTACT SERVICE: " + contact.getSchedule());
               }
            }
            if (service.getServiceFiles() != null && service.getServiceFiles().size() > 0) {
               for (ServiceFileEntity serviceFile : service.getServiceFiles()) {
                  inf("NAME FILE SERVICE FILE" + serviceFile.getNameFile());
                  inf("URL FILE SERVICE FILE" + serviceFile.getUrl());
               }
            }
         }
      }

   }

   private void inf(String message) {
      System.out.println(message);

   }

   public String getNameTypeContact(String typeContact) {
      for (MultivalueEntity multivalue : listTypeContact) {
         if (multivalue.getCode().equals(typeContact)) {
            return multivalue.getName();
         }
      }
      return null;
   }

   public String getNameSchedule(String schedule) {
      for (MultivalueEntity multivalue : listSchedule) {
         if (multivalue.getCode().equals(schedule)) {
            return multivalue.getName();
         }
      }
      return null;
   }

   public ClientProfileEntity getClientProfile() {
      return clientProfile;
   }

   public void setClientProfile(ClientProfileEntity clientProfile) {
      this.clientProfile = clientProfile;
   }

   public ClientContactEntity getClientContact() {
      return clientContact;
   }

   public void setClientContact(ClientContactEntity clientContact) {
      this.clientContact = clientContact;
   }

   public ArrayList<MultivalueEntity> getListTypeContact() {
      return listTypeContact;
   }

   public void setListTypeContact(ArrayList<MultivalueEntity> listTypeContact) {
      this.listTypeContact = listTypeContact;
   }

   public ClientServiceEntity getClientService() {
      return clientService;
   }

   public void setClientService(ClientServiceEntity clientService) {
      this.clientService = clientService;
   }

   public ArrayList<ClientServiceEntity> getListClientService() {
      return listClientService;
   }

   public void setListClientService(ArrayList<ClientServiceEntity> listClientService) {
      this.listClientService = listClientService;
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

   public ArrayList<MultivalueEntity> getListTypeService() {
      return listTypeService;
   }

   public void setListTypeService(ArrayList<MultivalueEntity> listTypeService) {
      this.listTypeService = listTypeService;
   }

   public boolean isPuntoPrincipal() {
      return puntoPrincipal;
   }

   public void setPuntoPrincipal(boolean puntoPrincipal) {
      this.puntoPrincipal = puntoPrincipal;
   }

   public boolean isEnlaceBackUp() {
      return enlaceBackUp;
   }

   public void setEnlaceBackUp(boolean enlaceBackUp) {
      this.enlaceBackUp = enlaceBackUp;
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

   public ServiceContactEntity getServiceContact() {
      return serviceContact;
   }

   public void setServiceContact(ServiceContactEntity serviceContact) {
      this.serviceContact = serviceContact;
   }

   public UploadedFile getUltimaConfiguracionFile() {
      return ultimaConfiguracionFile;
   }

   public void setUltimaConfiguracionFile(UploadedFile ultimaConfiguracionFile) {
      this.ultimaConfiguracionFile = ultimaConfiguracionFile;
   }

   public ArrayList<UploadedFile> getListDetailEngineeringFile() {
      return listDetailEngineeringFile;
   }

   public void setListDetailEngineeringFile(ArrayList<UploadedFile> listDetailEngineeringFile) {
      this.listDetailEngineeringFile = listDetailEngineeringFile;
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

   public String getIdCountry() {
      return idCountry;
   }

   public void setIdCountry(String idCountry) {
      this.idCountry = idCountry;
   }

   public ArrayList<CountryEntity> getListCountry() {
      return listCountry;
   }

   public void setListCountry(ArrayList<CountryEntity> listCountry) {
      this.listCountry = listCountry;
   }

   public boolean isDiferentProvider() {
      return diferentProvider;
   }

   public void setDiferentProvider(boolean diferentProvider) {
      this.diferentProvider = diferentProvider;
   }

}
