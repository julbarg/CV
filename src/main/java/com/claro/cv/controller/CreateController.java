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
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.enums.TypeMultivalueEnum;
import com.claro.cv.service.CreateService;
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

   private ClientProfileEntity clientProfile;

   private ClientContactEntity clientContact;

   private ClientServiceEntity clientService;

   private ArrayList<ClientServiceEntity> listClientService;

   private ServiceContactEntity serviceContact;

   private ArrayList<MultivalueEntity> listTypeContact;

   private ArrayList<MultivalueEntity> listTypeService;

   private ArrayList<MultivalueEntity> listProviderLastMile;

   private ArrayList<MultivalueEntity> listSchedule;

   private ArrayList<DepartamentEntity> listDepartamento;

   private ArrayList<CityEntity> listCiudad;

   private String idDepartament;

   private String idCity;

   private boolean puntoPrincipal;

   private boolean enlaceBackUp;

   private UploadedFile ultimaConfiguracionFile;

   @Autowired
   private CreateService createService;

   @PostConstruct
   public void initializate() {
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

      loadMultiValues();
      loadDepartaments();
      loadCities();
   }

   private void loadMultiValues() {
      try {
         listTypeContact = createService.loadMultiValue(TypeMultivalueEnum.TIPO_CONTACTO);
         listTypeService = createService.loadMultiValue(TypeMultivalueEnum.TIPO_SERVICIO);
         listProviderLastMile = createService.loadMultiValue(TypeMultivalueEnum.PROVEEDOR_ULTIMA_MILLA);
         listSchedule = createService.loadMultiValue(TypeMultivalueEnum.HORARIO_ATENCION);

      } catch (Exception e) {
         LOGGER.error("Ha ocurrido un error al cargar los Tipos de Contacto", e);
         Util.addMessageFatal("Ha ocurrido un error al cargar los Tipos de Contacto");
      }
   }

   private void loadDepartaments() {
      try {
         listDepartamento = createService.loadDepartaments();
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_DEPARTAMENT_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_DEPARTAMENT_ERROR);
      }

   }

   private void loadCities() {
      try {
         listCiudad = createService.loadCities();
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_CITY_ERROR);
      }
   }

   public void loadCitiesByDepartament() {
      try {
         listCiudad = createService.loadCitiesByDepartament(idDepartament);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
         Util.addMessageFatal(Messages.LOAD_CITY_ERROR);
      }
   }

   public void addContact() {
      if (validateContact()) {
         clientContact.setClientProfile(clientProfile);
         clientProfile.getClientContacts().add(clientContact);
         clientContact = new ClientContactEntity();
      } else {
         Util.addMessageWarn(Messages.VALIDATE_CONTACT_PROFILE);
      }

   }

   private boolean validateContact() {
      boolean validateName = clientContact.getNameContact() != null
         && clientContact.getNameContact().length() > 0;
      boolean validatePhone = clientContact.getPhone() != null && clientContact.getPhone().length() > 0;
      boolean validateMobil = clientContact.getMobil() != null && clientContact.getMobil().length() > 0;

      return validateName && (validatePhone || validateMobil) && clientContact.getTypeContact().length() > 0;

   }

   public void addContactService() {
      if (validateContactService()) {
         clientService.getServiceContacts().add(serviceContact);
         serviceContact = new ServiceContactEntity();
      } else {
         Util.addMessageWarn(Messages.VALIDATE_CONTACT_SERVICE);
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
            SecureRandom random = new SecureRandom();
            String fileName = clientService.getAlias().toUpperCase() + "-"
               + (new BigInteger(40, random)).toString(30).toUpperCase();
            String URL = createFileLastSettings(fileName);
            clientService.setLastSettingFile(setLastSettingFile(URL, fileName));
         }
         addCityAndDepartamentToClientService();
         String mainPoint = puntoPrincipal == true ? "S" : "N";
         clientService.setMainPoint(mainPoint);
         String backup = enlaceBackUp == true ? "S" : "N";
         clientService.setBackup(backup);
         listClientService.add(clientService);
         clientService = new ClientServiceEntity();
         clientService.setServiceContacts(new ArrayList<ServiceContactEntity>());
         idCity = "";
         idDepartament = "";
         puntoPrincipal = false;
         enlaceBackUp = false;
         ultimaConfiguracionFile = null;
      }
      RequestContext.getCurrentInstance().execute("loadMapOrigin()");
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
      boolean validateDirection = clientService.getDirection() != null
         && clientService.getDirection().length() > 0;
      boolean validateAlias = clientService.getAlias() != null && clientService.getAlias().length() > 0;
      boolean validateCodeService = clientService.getCodeService() != null
         && clientService.getCodeService().length() > 0;

      if (!validateMap) {
         Util.addMessageError(Messages.VALIDATE_MAP);
      }
      if (!validateDirection) {
         Util.addMessageError(Messages.VALIDATE_DIRECTION);
      }
      if (!validateAlias) {
         Util.addMessageError(Messages.VALIDATE_ALIAS);
      }
      if (!validateCodeService) {
         Util.addMessageError(Messages.VALIDATE_CODE_SERVICE);
      }

      return validateMap && validateDirection && validateAlias && validateCodeService;

   }

   private void addCityAndDepartamentToClientService() {
      try {
         CityEntity city = createService.findCityById(idCity);
         clientService.setCity(city);
         clientService.setDepartament(city.getDepartament());
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CITY_ERROR, e);
      }
   }

   public void nextStep() {
      RequestContext.getCurrentInstance().execute("next()");
   }

   public void uploadFileE(FileUploadEvent event) {
      try {
         ultimaConfiguracionFile = event.getFile();

         Util.addMessageFatal(Messages.FILE_UPLOAD_SUCESS);
      } catch (Exception e) {
         Util.addMessageFatal(Messages.USER_FILE_ERROR);
         LOGGER.error(Messages.USER_FILE_ERROR, e);
      }
   }

   public String save() {
      try {
         for (ClientServiceEntity service : listClientService) {
            service.setClientProfile(clientProfile);
            if (service.getServiceContacts() != null) {
               for (ServiceContactEntity serviceContact : service.getServiceContacts()) {
                  serviceContact.setClientService(service);
               }
            }
         }
         clientProfile.setClientServices(listClientService);
         printClientProfile();
         createService.save(clientProfile);
         Util.addMessageInfoKeep(Messages.SAVE_PROFILE_SUCESSFULL);
         clientProfile = new ClientProfileEntity();
         listClientService = new ArrayList<ClientServiceEntity>();
         return Util.getRedirect(Constant.CREATE_PAGE);
      } catch (Exception e) {
         LOGGER.error(Messages.SAVE_PROFILE_CLIENT_ERROR, e);
         Util.addMessageFatal(Messages.SAVE_PROFILE_CLIENT_ERROR);
         return null;
      }
   }

   private String createFileLastSettings(String fileName) {
      try {
         String fileNameFinal = Constant.PATH_UPLOAD_FILE + fileName + ".txt";
         InputStream in = ultimaConfiguracionFile.getInputstream();

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
         e.printStackTrace();
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

}
