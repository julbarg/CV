package com.claro.cv.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.entity.CityEntity;
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
import com.claro.cv.service.EditServiceService;
import com.claro.cv.service.UtilService;
import com.claro.cv.util.Constant;
import com.claro.cv.util.Messages;
import com.claro.cv.util.Util;


@Controller("editService")
@Scope("session")
public class EditServiceController implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 360894991327221140L;

   private static Logger LOGGER = LogManager.getLogger(EditServiceController.class.getName());

   @Autowired
   private EditServiceService editServiceService;

   @Autowired
   private UtilService utilService;

   private BigInteger idClientServiceEdit;

   private ClientServiceEntity clientServiceEdit;

   private ArrayList<ServiceContactEntity> listServiceContact;

   private ArrayList<String> listTypeLocaltion;

   private String typeLocaltion;

   private boolean international;

   private boolean diferentProvider;

   private String idDepartament;

   private String idCity;

   private String idCountry;

   private ArrayList<MultivalueEntity> listTypeService;

   private ArrayList<MultivalueEntity> listProviderLastMile;

   private ArrayList<MultivalueEntity> listState;

   private ArrayList<MultivalueEntity> listSchedule;

   private ArrayList<CountryEntity> listCountry;

   private ArrayList<DepartamentEntity> listDepartamento;

   private ArrayList<CityEntity> listCiudad;

   private boolean mainPoint;

   private boolean enlaceBackUp;

   private ServiceContactEntity serviceContactNew;

   private BigInteger idServiceContactDelete;

   private LastSettingFileEntity lastSettingFileOld;

   private LastSettingFileEntity lastSettingFileNew;

   private ArrayList<ServiceFileEntity> listDetailEngineeringFile;

   private BigInteger idServiceFileDelete;

   private boolean noChangesUC;

   private String nameUpload;

   private boolean showObservationState;

   @PostConstruct
   public void initialize() {
      clientServiceEdit = new ClientServiceEntity();
      serviceContactNew = new ServiceContactEntity();
      loadTypeLocation();
      loadCountries();
      loadDepartaments();
      loadCities();
      loadMultiValues();
   }

   private void loadTypeLocation() {
      listTypeLocaltion = new ArrayList<String>();
      listTypeLocaltion.add(TypeLocationEnum.NATIONAL.getValue());
      listTypeLocaltion.add(TypeLocationEnum.INTERNATIONAL.getValue());
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
         listTypeService = utilService.loadMultiValue(TypeMultivalueEnum.TIPO_SERVICIO);
         listProviderLastMile = utilService.loadMultiValue(TypeMultivalueEnum.PROVEEDOR_ULTIMA_MILLA);
         listState = utilService.loadMultiValue(TypeMultivalueEnum.ESTADO_CLIENTE_SERVICIO);
         listSchedule = utilService.loadMultiValue(TypeMultivalueEnum.HORARIO_ATENCION);
      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al cargar los Tipos de Contacto", e);
         Util.addMessageFatal("Ha ocurrido un error al cargar los Tipos de Contacto");
      }
   }

   public String goServiceEdit() {
      loadClientServiceEdit();
      loadServiceContacts();
      loadServiceFiles();
      loadFlags();
      lastSettingFileOld = clientServiceEdit.getLastSettingFile();
      noChangesUC = true;
      nameUpload = lastSettingFileOld.getNameFile();
      return Util.getRedirect(Constant.ADMIN_EDIT_SERVICE);
   }

   private void refresh() {
      loadClientServiceEdit();
      loadServiceContacts();
      loadServiceFiles();
      loadFlags();
      lastSettingFileOld = clientServiceEdit.getLastSettingFile();
      noChangesUC = true;
      nameUpload = lastSettingFileOld.getNameFile();
   }

   private void loadClientServiceEdit() {
      try {
         clientServiceEdit = editServiceService.loadClientServiceEdit(idClientServiceEdit);
      } catch (Exception e) {
         Util.addMessageError(Messages.LOAD_CLIENT_SERVICE_ERROR);
         LOGGER.error(Messages.LOAD_CLIENT_SERVICE_ERROR, e);
      }
   }

   private void loadServiceContacts() {
      try {
         listServiceContact = new ArrayList<ServiceContactEntity>();
         listServiceContact = editServiceService.loadServiceContacts(clientServiceEdit);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_SERVICE_CONTACTS_ERROR, e);
      }

   }

   private void loadServiceFiles() {
      try {
         listDetailEngineeringFile = editServiceService.loadServiceFiles(clientServiceEdit);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_SERVICE_FILE_ERROR, e);
      }

   }

   private void loadFlags() {
      international = clientServiceEdit.getCountry() != null;
      typeLocaltion = international ? TypeLocationEnum.INTERNATIONAL.getValue() : TypeLocationEnum.NATIONAL
         .getValue();
      diferentProvider = validate(clientServiceEdit.getCodeServiceLastMile());
      mainPoint = "S".equals(clientServiceEdit.getMainPoint()) ? true : false;
      enlaceBackUp = "S".equals(clientServiceEdit.getBackup()) ? true : false;
      showObservationState = clientServiceEdit.getObservationState() != null
         && clientServiceEdit.getObservationState().length() > 0;

      loadLocations();
   }

   private void loadLocations() {
      if (international) {
         idCountry = clientServiceEdit.getCountry().getIdCountry();
      } else {
         idDepartament = clientServiceEdit.getDepartament().getIdDepartament();
         idCity = clientServiceEdit.getCity().getIdCity();
      }

   }

   public void changeTypeLocation() {
      if (TypeLocationEnum.INTERNATIONAL.getValue().equals(typeLocaltion)) {
         international = true;
      } else {
         international = false;
      }
      idDepartament = "";
      idCity = "";
      idCountry = "";
   }

   public void loadCitiesByDepartament() {
      try {
         if (idDepartament != null && idDepartament.length() > 0) {
            listCiudad = utilService.loadCitiesByDepartament(idDepartament);
         }

      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_CITY_ERROR);
      }
   }

   public void changeProvide() {
      clientServiceEdit.setCodeServiceLastMile(null);
      if (clientServiceEdit.getIdProviderLastMile() == null
         || clientServiceEdit.getIdProviderLastMile().length() == 0) {
         diferentProvider = false;
         return;
      }
      diferentProvider = true;
   }

   public void uploadFileLastSettings(FileUploadEvent event) {
      try {
         UploadedFile ultimaConfiguracionFile = event.getFile();
         nameUpload = ultimaConfiguracionFile.getFileName();
         noChangesUC = false;

         if (ultimaConfiguracionFile != null && ultimaConfiguracionFile.getFileName() != null) {
            SecureRandom random = new SecureRandom();
            String fileName = Constant.UC + clientServiceEdit.getAlias().toUpperCase() + "-"
               + (new BigInteger(40, random)).toString(30).toUpperCase();
            String URL = createFile(fileName, TypeFileEnum.SETTINGS.getValue(), ultimaConfiguracionFile);
            lastSettingFileNew = new LastSettingFileEntity();
            lastSettingFileNew.setUrl(URL);
            lastSettingFileNew.setNameFile(fileName);
            lastSettingFileNew = editServiceService.saveLastSettingFile(lastSettingFileNew);

         }

      } catch (Exception e) {
         Util.addMessageFatal(Messages.USER_FILE_ERROR);
         LOGGER.error(Messages.USER_FILE_ERROR, e);
      }
   }

   public void deleteFile(String url) {
      try {
         editServiceService.deleteServiceFile(idServiceFileDelete);
         clientServiceEdit = editServiceService.loadClientServiceEdit(idClientServiceEdit);
         File fileDelete = new File(url);
         fileDelete.delete();
         loadServiceFiles();
      } catch (Exception e) {
         LOGGER.error(Messages.DELETE_FILE_PROFILE_ERROR, e);
         Util.addMessageError(Messages.DELETE_FILE_PROFILE_ERROR);
      }
   }

   private void deleteFileUC(LastSettingFileEntity lastSettingFile) {
      try {
         editServiceService.deleteFile(lastSettingFile);
         File fileDelete = new File(lastSettingFile.getUrl());
         fileDelete.delete();
      } catch (Exception e) {
         LOGGER.error(Messages.DELETE_SERVICE_FILE_ERROR, e);
         Util.addMessageError(Messages.DELETE_SERVICE_FILE_ERROR);
      }
   }

   private String createFile(String fileName, String type, UploadedFile upload) {
      try {
         String path = null;
         if (TypeFileEnum.SETTINGS.getValue().equals(type)) {
            path = Constant.PATH_UPLOAD_FILE_SETTINGS;
         } else if (TypeFileEnum.ENGINEERING.getValue().equals(type)) {
            path = Constant.PATH_UPLOAD_FILE_ENGINEERING;
         } else if (TypeFileEnum.ENGINEERING_SERVICE.getValue().equals(type)) {
            path = Constant.PATH_UPLOAD_FILE_ENGINEERING_SERVICE;
         }
         String extension = FilenameUtils.getExtension(upload.getFileName());
         String fileNameFinal = path + fileName + "." + extension;
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

   public void addServiceContact() {
      serviceContactNew.setClientService(clientServiceEdit);
      listServiceContact.add(serviceContactNew);
      serviceContactNew = new ServiceContactEntity();
   }

   public void deleteServiceContact() {
      try {
         if (idServiceContactDelete.intValue() > 0) {
            editServiceService.deleteServiceContact(idServiceContactDelete);
            clientServiceEdit = editServiceService.loadClientServiceEdit(idClientServiceEdit);
         }
         loadServiceContacts();
      } catch (Exception e) {
         LOGGER.error(Messages.DELETE_SERVICE_CONTACT_ERROR, e);
         Util.addMessageError(Messages.DELETE_SERVICE_CONTACT_ERROR);
      }

   }

   public void uploadFileIngenieria(FileUploadEvent event) {
      try {
         UploadedFile serviceFile = event.getFile();
         saveDetailEngineeringFiles(serviceFile);
         Util.addMessageInfo(Messages.FILE_UPLOAD_SUCESS);
         clientServiceEdit = editServiceService.loadClientServiceEdit(idClientServiceEdit);
         loadServiceFiles();
      } catch (Exception e) {
         Util.addMessageFatal(Messages.USER_FILE_ERROR);
         LOGGER.error(Messages.USER_FILE_ERROR, e);
      }
   }

   private void saveDetailEngineeringFiles(UploadedFile uploadFile) {
      ServiceFileEntity serviceFileEntity;
      if (uploadFile != null && uploadFile.getFileName() != null) {
         serviceFileEntity = new ServiceFileEntity();
         serviceFileEntity.setClientService(clientServiceEdit);

         SecureRandom random = new SecureRandom();
         String fileName = Constant.ING + clientServiceEdit.getAlias().toUpperCase() + "-"
            + (new BigInteger(40, random)).toString(30).toUpperCase();
         String URL = createFile(fileName, TypeFileEnum.ENGINEERING_SERVICE.getValue(), uploadFile);
         serviceFileEntity.setNameFile(fileName);
         serviceFileEntity.setUrl(URL);
         saveServiceFile(serviceFileEntity);
      }

   }

   private void saveServiceFile(ServiceFileEntity serviceFile) {
      try {
         editServiceService.saveServiceFile(serviceFile);
      } catch (Exception e) {
         LOGGER.error(Messages.CREATE_CLIENT_FILE_ERROR, e);
      }

   }

   public void changeState() {
      showObservationState = !Constant.STATE_ACTIVE.equals(clientServiceEdit.getState());
   }

   public void update() {
      if (!validateUpdate()) {
         RequestContext.getCurrentInstance().execute("loadMapOrigin()");
         return;
      }
      updateLocations();
      updateServiceContacts();
      updateService();
      if (!noChangesUC) {
         deleteFileUC(lastSettingFileOld);
      }
      refresh();
      Util.addMessageInfo(Messages.UPDATE_SERVICE_SUCCESS);
      RequestContext.getCurrentInstance().execute("loadMapOrigin()");
   }

   private boolean validateUpdate() {

      boolean validateServiceContacts = listServiceContact.size() > 0;
      boolean validateDirection = validate(clientServiceEdit.getDirection());
      boolean validateTypeService = validate(clientServiceEdit.getTypeService());
      boolean validateCodeService = validate(clientServiceEdit.getCodeService());
      boolean validateDescription = validate(clientServiceEdit.getDescription());

      boolean validateCountry = getCountry() != null;
      boolean validateDepartamentoAndCity = getDepartament() != null && getCity() != null;

      boolean validateProveedor = validate(clientServiceEdit.getIdProviderLastMile());
      boolean validateCodeServiceUM = validate(clientServiceEdit.getCodeServiceLastMile());
      boolean validateObservation = validate(clientServiceEdit.getObservationState());

      boolean general = validateServiceContacts && validateDirection && validateTypeService
         && validateCodeService && validateDescription;
      boolean location = false;
      if (international) {
         location = validateCountry;
      } else {
         location = validateDepartamentoAndCity;
      }
      boolean proveedor = false;
      if (validateProveedor) {
         proveedor = validateCodeServiceUM;
      } else {
         proveedor = true;
      }
      boolean observation = false;
      if (showObservationState) {
         observation = validateObservation;
      } else {
         observation = true;
      }

      if (!validateDirection) {
         Util.addMessageError(Messages.VALIDATE_DIRECTION);
      }
      if (!validateTypeService) {
         Util.addMessageError(Messages.VALIDATE_TYPE_SERVICE);
      }
      if (!validateCodeService) {
         Util.addMessageError(Messages.VALIDATE_CODE_SERVICE);
      }

      if (!validateDescription) {
         Util.addMessageError(Messages.VALIDATE_DESCRIPTION);
      }
      if (!validateServiceContacts) {
         Util.addMessageError(Messages.VALIDATE_CONTACTS);
      }

      if (!location) {
         Util.addMessageError(Messages.VALIDATE_LOCATION);
      }
      if (!proveedor) {
         Util.addMessageError(Messages.VALIDATE_CODE_SERVICE_UM);
      }
      if (!observation) {
         Util.addMessageError(Messages.VALIDATE_OBSERVATION);
      }

      return general && location && proveedor && observation;
   }

   private boolean validate(String value) {
      return value != null && value.length() > 0;
   }

   private void updateLocations() {
      clientServiceEdit.setCity(getCity());
      clientServiceEdit.setDepartament(getDepartament());
      clientServiceEdit.setCountry(getCountry());

   }

   private CityEntity getCity() {

      if (idCity != null && idCity.length() > 0) {
         try {
            return editServiceService.loadCity(idCity);
         } catch (Exception e) {
            LOGGER.error(Messages.LOAD_CITY_ERROR);
         }
      }
      return null;
   }

   private DepartamentEntity getDepartament() {
      if (idDepartament != null && idDepartament.length() > 0) {
         try {
            return editServiceService.loadDepartament(idDepartament);
         } catch (Exception e) {
            LOGGER.error(Messages.LOAD_DEPARTAMENT_ERROR);
         }
      }
      return null;
   }

   private CountryEntity getCountry() {
      if (idCountry != null && idCountry.length() > 0) {
         try {
            return editServiceService.loadCountry(idCountry);
         } catch (Exception e) {
            LOGGER.error(Messages.LOAD_COUNTRY_ERROR);
         }

      }
      return null;
   }

   private void updateServiceContacts() {
      for (ServiceContactEntity serviceContact : listServiceContact) {
         try {
            editServiceService.updateServiceContact(serviceContact);
         } catch (Exception e) {
            LOGGER.error(Messages.UPDATE_SERVICE_CONTACT_ERROR, e);
            Util.addMessageError(Messages.UPDATE_SERVICE_CONTACT_ERROR);
         }

      }
   }

   private void updateService() {
      try {
         String mainPointE = mainPoint == true ? "S" : "N";
         clientServiceEdit.setMainPoint(mainPointE);
         String backup = enlaceBackUp == true ? "S" : "N";
         clientServiceEdit.setBackup(backup);
         if (!noChangesUC) {
            clientServiceEdit.setLastSettingFile(lastSettingFileNew);
         }
         clientServiceEdit = editServiceService.update(clientServiceEdit);
      } catch (Exception e) {
         LOGGER.error(Messages.UPDATE_SERVICE_ERROR, e);
         Util.addMessageError(Messages.UPDATE_SERVICE_ERROR);
      }
   }

   public String getSchedule(String valueSchedule) {
      return Util.getMeansMultiValue(listSchedule, valueSchedule);
   }

   public BigInteger getIdClientServiceEdit() {
      return idClientServiceEdit;
   }

   public void setIdClientServiceEdit(BigInteger idClientServiceEdit) {
      this.idClientServiceEdit = idClientServiceEdit;
   }

   public ClientServiceEntity getClientServiceEdit() {
      return clientServiceEdit;
   }

   public void setClientServiceEdit(ClientServiceEntity clientServiceEdit) {
      this.clientServiceEdit = clientServiceEdit;
   }

   public ArrayList<String> getListTypeLocaltion() {
      return listTypeLocaltion;
   }

   public void setListTypeLocaltion(ArrayList<String> listTypeLocaltion) {
      this.listTypeLocaltion = listTypeLocaltion;
   }

   public String getTypeLocaltion() {
      return typeLocaltion;
   }

   public void setTypeLocaltion(String typeLocaltion) {
      this.typeLocaltion = typeLocaltion;
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

   public ArrayList<CityEntity> getListCiudad() {
      return listCiudad;
   }

   public void setListCiudad(ArrayList<CityEntity> listCiudad) {
      this.listCiudad = listCiudad;
   }

   public ArrayList<DepartamentEntity> getListDepartamento() {
      return listDepartamento;
   }

   public void setListDepartamento(ArrayList<DepartamentEntity> listDepartamento) {
      this.listDepartamento = listDepartamento;
   }

   public ArrayList<MultivalueEntity> getListTypeService() {
      return listTypeService;
   }

   public void setListTypeService(ArrayList<MultivalueEntity> listTypeService) {
      this.listTypeService = listTypeService;
   }

   public ArrayList<MultivalueEntity> getListProviderLastMile() {
      return listProviderLastMile;
   }

   public void setListProviderLastMile(ArrayList<MultivalueEntity> listProviderLastMile) {
      this.listProviderLastMile = listProviderLastMile;
   }

   public ArrayList<MultivalueEntity> getListState() {
      return listState;
   }

   public void setListState(ArrayList<MultivalueEntity> listState) {
      this.listState = listState;
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

   public ArrayList<MultivalueEntity> getListSchedule() {
      return listSchedule;
   }

   public void setListSchedule(ArrayList<MultivalueEntity> listSchedule) {
      this.listSchedule = listSchedule;
   }

   public ServiceContactEntity getServiceContactNew() {
      return serviceContactNew;
   }

   public void setServiceContactNew(ServiceContactEntity serviceContactNew) {
      this.serviceContactNew = serviceContactNew;
   }

   public ArrayList<ServiceContactEntity> getListServiceContact() {
      return listServiceContact;
   }

   public void setListServiceContact(ArrayList<ServiceContactEntity> listServiceContact) {
      this.listServiceContact = listServiceContact;
   }

   public BigInteger getIdServiceContactDelete() {
      return idServiceContactDelete;
   }

   public void setIdServiceContactDelete(BigInteger idServiceContactDelete) {
      this.idServiceContactDelete = idServiceContactDelete;
   }

   public String getNameUpload() {
      return nameUpload;
   }

   public void setNameUpload(String nameUpload) {
      this.nameUpload = nameUpload;
   }

   public ArrayList<ServiceFileEntity> getListDetailEngineeringFile() {
      return listDetailEngineeringFile;
   }

   public void setListDetailEngineeringFile(ArrayList<ServiceFileEntity> listDetailEngineeringFile) {
      this.listDetailEngineeringFile = listDetailEngineeringFile;
   }

   public BigInteger getIdServiceFileDelete() {
      return idServiceFileDelete;
   }

   public void setIdServiceFileDelete(BigInteger idServiceFileDelete) {
      this.idServiceFileDelete = idServiceFileDelete;
   }

   public LastSettingFileEntity getLastSettingFileOld() {
      return lastSettingFileOld;
   }

   public void setLastSettingFileOld(LastSettingFileEntity lastSettingFileOld) {
      this.lastSettingFileOld = lastSettingFileOld;
   }

   public boolean isNoChangesUC() {
      return noChangesUC;
   }

   public void setNoChangesUC(boolean noChangesUC) {
      this.noChangesUC = noChangesUC;
   }

   public boolean isShowObservationState() {
      return showObservationState;
   }

   public void setShowObservationState(boolean showObservationState) {
      this.showObservationState = showObservationState;
   }

}
