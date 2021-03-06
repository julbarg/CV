package com.claro.cv.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.dto.EditSearchDTO;
import com.claro.cv.entity.ClientContactEntity;
import com.claro.cv.entity.ClientFileEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.entity.ServiceFileEntity;
import com.claro.cv.enums.TypeMultivalueEnum;
import com.claro.cv.service.EditService;
import com.claro.cv.service.UtilService;
import com.claro.cv.util.Constant;
import com.claro.cv.util.Messages;
import com.claro.cv.util.Util;


@Controller("edit")
@Scope("session")
public class EditController implements Serializable {

   /**
    *  
    */
   private static final long serialVersionUID = -7876964547484376643L;

   private static Logger LOGGER = LogManager.getLogger(EditController.class.getName());

   @Autowired
   private EditService editService;

   @Autowired
   private UtilService utilService;

   private EditSearchDTO editSearch;

   private ArrayList<ClientProfileEntity> listClientProfileSearch;

   private ClientProfileEntity clientEdit;

   private ArrayList<ClientServiceEntity> listClientServiceEdit;

   private BigInteger idClientEdit;

   private ArrayList<MultivalueEntity> listTypeContact;

   private ArrayList<MultivalueEntity> listTypeService;

   private ArrayList<MultivalueEntity> listState;

   private ArrayList<MultivalueEntity> listSchedules;

   private ClientContactEntity clientContact;

   private ArrayList<ClientContactEntity> listClientContact;

   private BigInteger idClientContactDelete;

   private ArrayList<ClientFileEntity> listDetailEngineeringFile;

   private BigInteger idClientFileDelete;

   private MapModel mapModel;

   private String centerMap;

   private int zoomMap;

   private boolean showObservationState;

   private Marker markerSelect;

   private String loadGetMatkers;

   @PostConstruct
   public void initialize() {
      editSearch = new EditSearchDTO();
      clientEdit = new ClientProfileEntity();
      clientContact = new ClientContactEntity();
      listClientProfileSearch = new ArrayList<ClientProfileEntity>();
      listClientServiceEdit = new ArrayList<ClientServiceEntity>();
      listClientContact = new ArrayList<ClientContactEntity>();
      listDetailEngineeringFile = new ArrayList<ClientFileEntity>();
      loadMultivalue();
   }

   public void goAdmin() {
      editSearch = new EditSearchDTO();
      clientEdit = new ClientProfileEntity();
      clientContact = new ClientContactEntity();
      listClientProfileSearch = new ArrayList<ClientProfileEntity>();
      listClientServiceEdit = new ArrayList<ClientServiceEntity>();
      listClientContact = new ArrayList<ClientContactEntity>();
      listDetailEngineeringFile = new ArrayList<ClientFileEntity>();
      loadMultivalue();

      Util.redirectFaces(Constant.ADMIN_PAGE_URL);
   }

   private void loadMultivalue() {
      try {
         listTypeContact = utilService.loadMultiValue(TypeMultivalueEnum.TIPO_CONTACTO);
         listState = utilService.loadMultiValue(TypeMultivalueEnum.ESTADO_CLIENTE_SERVICIO);
         listTypeService = utilService.loadMultiValue(TypeMultivalueEnum.TIPO_SERVICIO);
         listSchedules = utilService.loadMultiValue(TypeMultivalueEnum.HORARIO_ATENCION);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_TYPE_CONTACT_ERROR, e);
      }

   }

   public void search() {
      try {
         if (!validateEditSearch()) {
            Util.addMessageError(Messages.VALIDATE_EDIT_SEARCH_ERROR);
            return;
         }
         listClientProfileSearch = editService.loadClientProfileByEditSearch(editSearch);
         if (editSearch.getIdClient() == null || editSearch.getIdClient().intValue() <= 0) {
            editSearch.setIdClient(null);
         }
         validateResultEditSearch();
      } catch (Exception e) {
         Util.addMessageError(Messages.LOAD_EDIT_SEARCH_ERROR);
         LOGGER.error(Messages.LOAD_EDIT_SEARCH_ERROR, e);
      }
   }

   private boolean validateEditSearch() {
      boolean validateNameClient = editSearch.getNameClient() != null
         && editSearch.getNameClient().length() > 0;
      boolean validateID = editSearch.getIdClient() != null && editSearch.getIdClient().intValue() > 0;
      boolean validateNIT = editSearch.getNitClient() != null && editSearch.getNitClient().length() > 0;
      boolean validateCodeService = editSearch.getCodeService() != null
         && editSearch.getCodeService().length() > 0;

      return validateNameClient || validateID || validateNIT || validateCodeService;
   }

   private void validateResultEditSearch() {
      if (listClientProfileSearch == null || listClientProfileSearch.size() == 0) {
         Util.addMessageError(Messages.LOAD_EDIT_SEARCH_RESULT_EMPTY);
      }

   }

   public void addContact() {
      if (validateContact()) {
         clientContact.setClientProfile(clientEdit);
         listClientContact.add(clientContact);
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

   public String edit() {
      try {
         clientEdit = editService.loadClientProfileById(idClientEdit);
         changeState();
         loadContactsProfile();
         loadDetailEngineeringFile();
         editSearch = new EditSearchDTO();
         listClientProfileSearch = new ArrayList<ClientProfileEntity>();
         loadServicesEdit();
         getMarkers();
         return Util.getRedirect(Constant.ADMIN_EDIT);
      } catch (Exception e) {
         Util.addMessageError(Messages.LOAD_CLIENT_EDIT_ERROR);
         LOGGER.error(Messages.LOAD_CLIENT_EDIT_ERROR, e);
         return null;
      }
   }

   public void refresh() {
      try {
         clientEdit = editService.loadClientProfileById(idClientEdit);
         changeState();
         loadContactsProfile();
         loadDetailEngineeringFile();
         editSearch = new EditSearchDTO();
         listClientProfileSearch = new ArrayList<ClientProfileEntity>();
         loadServicesEdit();
         getMarkers();
      } catch (Exception e) {
         Util.addMessageError(Messages.LOAD_CLIENT_EDIT_ERROR);
         LOGGER.error(Messages.LOAD_CLIENT_EDIT_ERROR, e);
      }
   }

   private void loadContactsProfile() {
      listClientContact = new ArrayList<ClientContactEntity>();
      for (ClientContactEntity contact : clientEdit.getClientContacts()) {
         listClientContact.add(contact);
      }
   }

   private void loadDetailEngineeringFile() {
      listDetailEngineeringFile = new ArrayList<ClientFileEntity>();
      for (ClientFileEntity file : clientEdit.getClientFiles()) {
         listDetailEngineeringFile.add(file);
      }
   }

   private void loadServicesEdit() {
      try {
         listClientServiceEdit = editService.loadServicesByClientProfile(clientEdit);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_SERVICES_ERROR, e);
      }
   }

   public void update() {
      if (!validateUpdate())
         return;
      if (updateClient() && updateContacts()) {
         Util.addMessageInfoKeep(Messages.UPDATE_CLIENT_PROFILE_SUCESS);
      }
   }

   private boolean validateUpdate() {
      boolean validateObservation = clientEdit.getObservationState() != null
         && clientEdit.getObservationState().length() > 0;
      boolean validateContacts = listClientContact != null && listClientContact.size() > 0;

      boolean observation = false;
      if (showObservationState) {
         observation = validateObservation;
      } else {
         observation = true;
      }

      if (!validateContacts) {
         Util.addMessageError(Messages.VALIDATE_CONTACTS);
      }
      if (!observation) {
         Util.addMessageError(Messages.VALIDATE_OBSERVATION);
      }

      return validateContacts && observation;
   }

   private boolean updateClient() {
      try {
         editService.update(clientEdit);
         return true;
      } catch (Exception e) {
         LOGGER.error(Messages.UPDATE_CLIENT_PROFILE_ERROR, e);
         Util.addMessageError(Messages.UPDATE_CLIENT_PROFILE_ERROR);
      }
      return false;

   }

   private boolean updateContacts() {
      try {
         editService.updateContacts(listClientContact);
         return true;
      } catch (Exception e) {
         LOGGER.error(Messages.UPDATE_CONTACTS_PROFILE_ERROR, e);
         Util.addMessageError(Messages.UPDATE_CONTACTS_PROFILE_ERROR);
      }
      return false;

   }

   public String getNameTypeContact(String typeContact) {
      for (MultivalueEntity multivalue : listTypeContact) {
         if (multivalue.getCode().equals(typeContact)) {
            return multivalue.getName();
         }
      }
      return null;
   }

   public String getNameState(String state) {
      for (MultivalueEntity multivalue : listState) {
         if (multivalue.getCode().equals(state)) {
            return multivalue.getName();
         }
      }
      return null;
   }

   public String getNameTypeService(String typeService) {
      for (MultivalueEntity multivalue : listTypeService) {
         if (multivalue.getCode().equals(typeService)) {
            return multivalue.getName();
         }
      }
      return null;
   }

   public void deleteContact() {
      try {
         editService.deleteContact(idClientContactDelete);
         clientEdit = editService.loadClientProfileById(idClientEdit);
         loadContactsProfile();
      } catch (Exception e) {
         LOGGER.error(Messages.DELETE_CONTACT_PROFILE_ERROR, e);
         Util.addMessageError(Messages.DELETE_CONTACT_PROFILE_ERROR);
      }

   }

   public void deleteFile(String url) {
      try {
         editService.deleteFile(idClientFileDelete);
         clientEdit = editService.loadClientProfileById(idClientEdit);
         File fileDelete = new File(url);
         fileDelete.delete();
         loadDetailEngineeringFile();
      } catch (Exception e) {
         LOGGER.error(Messages.DELETE_FILE_PROFILE_ERROR, e);
         Util.addMessageError(Messages.DELETE_FILE_PROFILE_ERROR);
      }
   }

   public void uploadFileIngenieria(FileUploadEvent event) {
      try {
         UploadedFile detailEngineeringFile = event.getFile();

         if (validateIfExitsFile(detailEngineeringFile)) {
            Util.addMessageError(Messages.VALIDATE_IF_EXISTS_ERROR);
            return;
         }

         saveDetailEngineeringFiles(detailEngineeringFile);
         Util.addMessageInfo(Messages.FILE_UPLOAD_SUCESS);
         clientEdit = editService.loadClientProfileById(idClientEdit);
         loadDetailEngineeringFile();
      } catch (Exception e) {
         Util.addMessageFatal(Messages.USER_FILE_ERROR);
         LOGGER.error(Messages.USER_FILE_ERROR, e);
      }
   }

   private boolean validateIfExitsFile(UploadedFile uploadedFileP) {
      String fileName = uploadedFileP.getFileName();
      String idClient = clientEdit.getIdClient().toString();
      String path = Constant.PATH_UPLOAD_FILE_ENGINEERING;
      path = path.replaceAll(Constant.TAG_ID_CLIENT, idClient);

      String fileNameFinal = path + fileName;
      File fileToValidate = new File(fileNameFinal);
      if (fileToValidate.exists() && !fileToValidate.isDirectory()) {
         return true;
      }
      return false;

   }

   private void saveDetailEngineeringFiles(UploadedFile uploadFile) {
      ClientFileEntity clientFile;
      if (uploadFile != null && uploadFile.getFileName() != null) {
         clientFile = new ClientFileEntity();
         clientFile.setClientProfile(clientEdit);

         String fileName = uploadFile.getFileName();
         String idClient = clientEdit.getIdClient().toString();

         String URL = createFile(fileName, uploadFile, idClient);
         clientFile.setNameFile(fileName);
         clientFile.setUrl(URL);
         saveClientFile(clientFile);
      }

   }

   private String createFile(String fileName, UploadedFile upload, String idClient) {
      try {
         String path = Constant.PATH_UPLOAD_FILE_ENGINEERING;
         path = path.replaceAll(Constant.TAG_ID_CLIENT, idClient);

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

   private void saveClientFile(ClientFileEntity clientFile) {
      try {
         editService.saveClientFile(clientFile);
      } catch (Exception e) {
         LOGGER.error(Messages.CREATE_CLIENT_FILE_ERROR, e);
      }

   }

   private void getMarkers() {
      boolean hasInternationalSerivices = false;
      LatLng coord;
      Marker marker;
      double lat;
      double lng;
      mapModel = new DefaultMapModel();
      for (ClientServiceEntity service : getListClientServiceEdit()) {
         lat = Double.parseDouble(service.getLat());
         lng = Double.parseDouble(service.getLng());

         service.setMainPoint(Util.getMeansFlag(service.getMainPoint()));
         service.setBackup(Util.getMeansFlag(service.getBackup()));
         service.setTypeService(getTypeService(service));
         service.setIdProviderLastMile(getNameProviderLastMile(service));

         coord = new LatLng(lat, lng);
         marker = new Marker(coord, service.getAlias(), service);
         if (service.getIdProviderLastMile() != null && service.getIdProviderLastMile().length() > 0) {
            marker.setIcon("/" + Constant.NAME_APLICATION + "/resources/img/marker-green.png");
         }
         mapModel.addOverlay(marker);

         if (!hasInternationalSerivices && service.getCountry() != null) {
            hasInternationalSerivices = true;
         }
      }
      setUpMap(hasInternationalSerivices);
   }

   public void onMarkerSelect(OverlaySelectEvent event) {
      markerSelect = (Marker) event.getOverlay();
   }

   public ArrayList<ServiceContactEntity> getContactsMarkerSelect() {
      try {
         if (markerSelect != null && markerSelect.getData() != null)
            return editService.loadContact((ClientServiceEntity) markerSelect.getData());
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CONTACT_SERVICE_ERROR, e);

      }
      return new ArrayList<ServiceContactEntity>();
   }

   public StreamedContent downloadLastSettingsFile() {
      try {
         LastSettingFileEntity file = ((ClientServiceEntity) markerSelect.getData()).getLastSettingFile();
         InputStream stream = new FileInputStream(file.getUrl());
         String extension = FilenameUtils.getExtension(file.getUrl());
         return new DefaultStreamedContent(stream, extension, file.getNameFile() + "." + extension);
      } catch (FileNotFoundException e) {
         LOGGER.error(Messages.LOAD_LAST_SETTINGS_FILE, e);
      }
      return null;
   }

   public String getSchedule(String valueSchedule) {
      return Util.getMeansMultiValue(listSchedules, valueSchedule);
   }

   public ArrayList<ServiceFileEntity> getServiceFiles() {
      try {
         if (markerSelect != null && markerSelect.getData() != null) {
            return editService.loadServiceFiles((ClientServiceEntity) markerSelect.getData());
         }
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_SERVICE_FILE_ERROR, e);

      }
      return new ArrayList<ServiceFileEntity>();
   }

   public boolean validateProvider(String idProviderLastMileP) {
      return idProviderLastMileP != null && idProviderLastMileP.length() > 0;
   }

   private void setUpMap(boolean hasInternationalSerivices) {
      if (hasInternationalSerivices) {
         centerMap = "-21.7351043,-63.2812499";
         zoomMap = 3;
      } else {
         centerMap = "4.1156602,-72.953885";
         zoomMap = 5;
      }

   }

   private String getTypeService(ClientServiceEntity service) {
      ArrayList<MultivalueEntity> listTypeServices;
      try {
         listTypeServices = utilService.loadMultiValue(TypeMultivalueEnum.TIPO_SERVICIO);
         return Util.getMeansMultiValue(listTypeServices, service.getTypeService());
      } catch (Exception e) {
         return "";
      }

   }

   private String getNameProviderLastMile(ClientServiceEntity service) {
      try {
         if (service.getIdProviderLastMile() != null) {
            return editService.getNameFromMultivalue(TypeMultivalueEnum.PROVEEDOR_ULTIMA_MILLA,
               service.getIdProviderLastMile());
         }
         return null;

      } catch (Exception e) {
         return null;
      }
   }

   public void changeState() {
      showObservationState = !Constant.STATE_ACTIVE.equals(clientEdit.getState());
   }

   public EditSearchDTO getEditSearch() {
      return editSearch;
   }

   public void setEditSearch(EditSearchDTO editSearch) {
      this.editSearch = editSearch;
   }

   public ArrayList<ClientProfileEntity> getListClientProfileSearch() {
      return listClientProfileSearch;
   }

   public void setListClientProfileSearch(ArrayList<ClientProfileEntity> listClientProfileSearch) {
      this.listClientProfileSearch = listClientProfileSearch;
   }

   public ClientProfileEntity getClientEdit() {
      return clientEdit;
   }

   public void setClientEdit(ClientProfileEntity clientEdit) {
      this.clientEdit = clientEdit;
   }

   public BigInteger getIdClientEdit() {
      return idClientEdit;
   }

   public void setIdClientEdit(BigInteger idClientEdit) {
      this.idClientEdit = idClientEdit;
   }

   public ArrayList<MultivalueEntity> getListTypeContact() {
      return listTypeContact;
   }

   public void setListTypeContact(ArrayList<MultivalueEntity> listTypeContact) {
      this.listTypeContact = listTypeContact;
   }

   public ArrayList<ClientServiceEntity> getListClientServiceEdit() {
      loadServicesEdit();
      return listClientServiceEdit;
   }

   public void setListClientServiceEdit(ArrayList<ClientServiceEntity> listClientServiceEdit) {
      this.listClientServiceEdit = listClientServiceEdit;
   }

   public ArrayList<MultivalueEntity> getListState() {
      return listState;
   }

   public void setListState(ArrayList<MultivalueEntity> listState) {
      this.listState = listState;
   }

   public ClientContactEntity getClientContact() {
      return clientContact;
   }

   public void setClientContact(ClientContactEntity clientContact) {
      this.clientContact = clientContact;
   }

   public ArrayList<ClientContactEntity> getListClientContact() {
      return listClientContact;
   }

   public void setListClientContact(ArrayList<ClientContactEntity> listClientContact) {
      this.listClientContact = listClientContact;
   }

   public BigInteger getIdClientContactDelete() {
      return idClientContactDelete;
   }

   public void setIdClientContactDelete(BigInteger idClientContactDelete) {
      this.idClientContactDelete = idClientContactDelete;
   }

   public ArrayList<ClientFileEntity> getListDetailEngineeringFile() {
      return listDetailEngineeringFile;
   }

   public void setListDetailEngineeringFile(ArrayList<ClientFileEntity> listDetailEngineeringFile) {
      this.listDetailEngineeringFile = listDetailEngineeringFile;
   }

   public BigInteger getIdClientFileDelete() {
      return idClientFileDelete;
   }

   public void setIdClientFileDelete(BigInteger idClientFileDelete) {
      this.idClientFileDelete = idClientFileDelete;
   }

   public MapModel getMapModel() {
      return mapModel;
   }

   public void setMapModel(MapModel mapModel) {
      this.mapModel = mapModel;
   }

   public String getCenterMap() {
      return centerMap;
   }

   public void setCenterMap(String centerMap) {
      this.centerMap = centerMap;
   }

   public int getZoomMap() {
      return zoomMap;
   }

   public void setZoomMap(int zoomMap) {
      this.zoomMap = zoomMap;
   }

   public boolean isShowObservationState() {
      return showObservationState;
   }

   public void setShowObservationState(boolean showObservationState) {
      this.showObservationState = showObservationState;
   }

   public Marker getMarkerSelect() {
      return markerSelect;
   }

   public void setMarkerSelect(Marker markerSelect) {
      this.markerSelect = markerSelect;
   }

   public String getLoadGetMatkers() {
      getMarkers();
      return loadGetMatkers;
   }

   public void setLoadGetMatkers(String loadGetMatkers) {
      this.loadGetMatkers = loadGetMatkers;
   }

}
