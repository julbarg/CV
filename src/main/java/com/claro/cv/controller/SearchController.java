package com.claro.cv.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.dto.MapDataDTO;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.entity.ServiceFileEntity;
import com.claro.cv.enums.TypeMultivalueEnum;
import com.claro.cv.service.SearchService;
import com.claro.cv.service.UtilService;
import com.claro.cv.util.Constant;
import com.claro.cv.util.MapJsonUtil;
import com.claro.cv.util.Messages;
import com.claro.cv.util.Util;


@Controller("search")
@Scope("session")
public class SearchController implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private static Logger LOGGER = LogManager.getLogger(SearchController.class.getName());

   @Autowired
   private SearchService searchService;

   @Autowired
   private UtilService utilService;

   private BigInteger idCliente;

   private String codigoServiicio;

   private ClientProfileEntity clientProfile;

   private String json;

   private String jsonInt;

   private String jsonURL;

   private DepartamentEntity departamentSelect;

   private CountryEntity countrySelect;

   private MapModel mapModel;

   private boolean international;

   private boolean sap;

   private ArrayList<MapDataDTO> listMapaData;

   private ArrayList<MapDataDTO> listMapaDataInt;

   private String centerMap;

   private int zoomMap;

   private ArrayList<ClientServiceEntity> listServicesBySelect;

   private Marker markerSelect;

   private ArrayList<MultivalueEntity> listTypeServices;

   private ArrayList<MultivalueEntity> listSchedules;

   @PostConstruct
   public void initialize() {
      clientProfile = new ClientProfileEntity();
      loadTypeServices();
      loadSchedules();
   }

   public void goSearch() {
      idCliente = null;
      codigoServiicio = "";
      Util.redirectFaces(Constant.SEARCH_PAGE_URL);
   }

   public boolean validateAuthentication() {
      if (Util.validateLogIn()) {
         return true;
      }
      Util.addMessageErrorKeepLogOut(Messages.NOT_SESSION);
      return false;
   }

   private void loadTypeServices() {
      try {
         listTypeServices = utilService.loadMultiValue(TypeMultivalueEnum.TIPO_SERVICIO);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_TYPE_SERVICE_ERROR, e);
      }
   }

   private void loadSchedules() {
      try {
         listSchedules = utilService.loadMultiValue(TypeMultivalueEnum.HORARIO_ATENCION);
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_SCHEDULE_ERROR, e);
      }
   }

   public String search() {
      try {
         if (!Util.validateLogIn()) {
            return null;
         }
         if (validateParameters()) {
            clientProfile = searchService.search(idCliente, codigoServiicio);
            if (clientProfile != null) {

               Util.addMessageInfo(clientProfile.getNameClient());
               idCliente = null;
               codigoServiicio = null;
               loadMap();
               loadMapInt();
               validateSAP();
               RequestContext.getCurrentInstance().execute("nextNormal();");
               return Util.getRedirect(Constant.SEARCH_DETAIL_PAGE);
            } else {
               Util.addMessageInfo(Messages.SEARCH_NOT_FOUND);
            }
         }

      } catch (Exception e) {
         LOGGER.error(Messages.SEARCH_ERROR, e);
         Util.addMessageFatal(Messages.SEARCH_ERROR);
      }

      return null;

   }

   private void validateSAP() {
      try {
         DepartamentEntity departament = searchService.loadDepartament(Constant.SAN_ANDRES_CODE);
         if (searchService.loadDetail(departament, clientProfile).size() > 0)
            sap = true;
         else
            sap = false;
      } catch (Exception e) {
         sap = false;
         LOGGER.error(Messages.VALIDATE_SAP_ERROR, e);
      }

   }

   private void loadMap() {
      try {
         listMapaData = searchService.loadMap(clientProfile);
         if (listMapaData != null && listMapaData.size() > 0) {
            json = MapJsonUtil.generateJson(listMapaData);
         } else {
            json = MapJsonUtil.generateJson(new ArrayList<MapDataDTO>());
         }

      } catch (Exception e) {
         LOGGER.error(Messages.ERROR_LOAD_MAP, e);
      }
   }

   private void loadMapInt() {
      try {
         listMapaDataInt = searchService.loadMapInt(clientProfile);
         if (listMapaDataInt != null && listMapaDataInt.size() > 0) {
            international = true;
            jsonInt = MapJsonUtil.generateJson(listMapaDataInt);
         } else {
            jsonInt = MapJsonUtil.generateJson(new ArrayList<MapDataDTO>());
            international = false;
         }
      } catch (Exception e) {
         LOGGER.error(Messages.ERROR_LOAD_MAP, e);
      }
   }

   public void loadDetail() {
      String regionSelect = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
         .get("regionSelect");
      try {
         departamentSelect = searchService.loadDepartament(regionSelect);
         listServicesBySelect = searchService.loadDetail(departamentSelect, clientProfile);
         if (listServicesBySelect != null && listServicesBySelect.size() > 0) {
            setUpMap();
            FacesContext.getCurrentInstance().getExternalContext()
               .redirect(Constant.DETAIL_PAGE + Constant.XHTML);
         } else {
            Util.addMessageError(Messages.NO_SERVICES_REGION);
         }
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_DETAIL_ERROR, e);
      }
   }

   public void loadDetailInt() {
      String regionSelect = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
         .get("regionSelect");
      try {
         countrySelect = searchService.loadCountry(regionSelect);
         listServicesBySelect = searchService.loadDetailInt(countrySelect, clientProfile);
         if (listServicesBySelect != null && listServicesBySelect.size() > 0) {
            setUpMapInt();
            FacesContext.getCurrentInstance().getExternalContext()
               .redirect(Constant.DETAIL_PAGE + Constant.XHTML);
         } else {
            Util.addMessageError(Messages.NO_SERVICES_REGION);
         }
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_DETAIL_ERROR, e);
      }
   }

   public void loadDetailSAP() {
      String regionSelect = Constant.SAN_ANDRES_CODE;
      try {
         departamentSelect = searchService.loadDepartament(regionSelect);
         listServicesBySelect = searchService.loadDetail(departamentSelect, clientProfile);
         if (listServicesBySelect != null && listServicesBySelect.size() > 0) {
            setUpMap();
            FacesContext.getCurrentInstance().getExternalContext()
               .redirect(Constant.DETAIL_PAGE + Constant.XHTML);
         } else {
            Util.addMessageError(Messages.NO_SERVICES_REGION);
         }
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_DETAIL_ERROR, e);
      }
   }

   private void setUpMap() {
      centerMap = departamentSelect.getLatCenter() + "," + departamentSelect.getLngCenter();
      zoomMap = departamentSelect.getZoom();
      getMarkers();
      jsonURL = Constant.URL_JOSON + departamentSelect.getGeocode() + Constant.JSON_EXTENSION;
   }

   private void setUpMapInt() {
      centerMap = countrySelect.getLatCenter() + "," + countrySelect.getLngCenter();
      zoomMap = countrySelect.getZoom();
      getMarkers();
      jsonURL = null;
   }

   private void getMarkers() {
      LatLng coord;
      Marker marker;
      double lat;
      double lng;
      mapModel = new DefaultMapModel();
      for (ClientServiceEntity service : listServicesBySelect) {
         lat = Double.parseDouble(service.getLat());
         lng = Double.parseDouble(service.getLng());

         service.setMainPoint(Util.getMeansFlag(service.getMainPoint()));
         service.setBackup(Util.getMeansFlag(service.getBackup()));
         service.setTypeService(getTypeService(service));
         service.setIdProviderLastMile(getNameProviderLastMile(service));

         coord = new LatLng(lat, lng);
         marker = new Marker(coord, service.getAlias(), service);
         if (service.getIdProviderLastMile() != null && service.getIdProviderLastMile().length() > 0) {
            marker.setIcon("/CV/resources/img/marker-green.png");
         }
         mapModel.addOverlay(marker);
      }
   }

   private String getNameProviderLastMile(ClientServiceEntity service) {
      try {
         if (service.getIdProviderLastMile() != null) {
            return searchService.getNameFromMultivalue(TypeMultivalueEnum.PROVEEDOR_ULTIMA_MILLA,
               service.getIdProviderLastMile());
         }
         return null;

      } catch (Exception e) {
         return null;
      }
   }

   private String getTypeService(ClientServiceEntity service) {
      return Util.getMeansMultiValue(listTypeServices, service.getTypeService());
   }

   public String getSchedule(String valueSchedule) {
      return Util.getMeansMultiValue(listSchedules, valueSchedule);
   }

   public void onMarkerSelect(OverlaySelectEvent event) {
      markerSelect = (Marker) event.getOverlay();
   }

   public ArrayList<ServiceContactEntity> getContactsMarkerSelect() {
      try {
         if (markerSelect != null && markerSelect.getData() != null)
            return searchService.loadContact((ClientServiceEntity) markerSelect.getData());
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_CONTACT_SERVICE_ERROR, e);

      }
      return new ArrayList<ServiceContactEntity>();
   }

   public ArrayList<ServiceFileEntity> getServiceFiles() {
      try {
         if (markerSelect != null && markerSelect.getData() != null) {
            return searchService.loadServiceFiles((ClientServiceEntity) markerSelect.getData());
         }
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_SERVICE_FILE_ERROR, e);

      }
      return new ArrayList<ServiceFileEntity>();
   }

   public boolean validateProvider(String idProviderLastMileP) {
      return idProviderLastMileP != null && idProviderLastMileP.length() > 0;
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

   public StreamedContent downloadFile(String url, String nameFile) {
      try {
         String extension = FilenameUtils.getExtension(url);
         InputStream stream = new FileInputStream(url);
         return new DefaultStreamedContent(stream, extension, nameFile + "." + extension);
      } catch (FileNotFoundException e) {
         LOGGER.error(Messages.DOWNLOAD_FILE_ERROR, e);
      }
      return null;
   }

   private boolean validateParameters() {
      if ((idCliente.intValue() == 0 || idCliente == null)
         && (codigoServiicio == null || codigoServiicio.length() == 0)) {
         Util.addMessageError(Messages.SEARCH_VALIDATE);
         idCliente = null;
         return false;
      }
      return true;
   }

   public String returnSearch() {
      return Util.getRedirect(Constant.SEARCH_PAGE);
   }

   public String returnSearchDetail() {
      return Util.getRedirect(Constant.SEARCH_DETAIL_PAGE);
   }

   public BigInteger getIdCliente() {
      return idCliente;
   }

   public void setIdCliente(BigInteger idCliente) {
      this.idCliente = idCliente;
   }

   public String getCodigoServiicio() {
      return codigoServiicio;
   }

   public void setCodigoServiicio(String codigoServiicio) {
      this.codigoServiicio = codigoServiicio;
   }

   public ClientProfileEntity getClientProfile() {
      return clientProfile;
   }

   public void setClientProfile(ClientProfileEntity clientProfile) {
      this.clientProfile = clientProfile;
   }

   public ArrayList<MapDataDTO> getListMapaData() {
      return listMapaData;
   }

   public void setListMapaData(ArrayList<MapDataDTO> listMapaData) {
      this.listMapaData = listMapaData;
   }

   public String getJson() {
      return json;
   }

   public void setJson(String json) {
      this.json = json;
   }

   public DepartamentEntity getDepartamentSelect() {
      return departamentSelect;
   }

   public void setDepartamentSelect(DepartamentEntity departamentSelect) {
      this.departamentSelect = departamentSelect;
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

   public MapModel getMapModel() {
      return mapModel;
   }

   public void setMapModel(MapModel mapModel) {
      this.mapModel = mapModel;
   }

   public ArrayList<ClientServiceEntity> getListServicesBySelect() {
      return listServicesBySelect;
   }

   public void setListServicesBySelect(ArrayList<ClientServiceEntity> listServicesByDepartamentSelect) {
      this.listServicesBySelect = listServicesByDepartamentSelect;
   }

   public Marker getMarkerSelect() {
      return markerSelect;
   }

   public void setMarkerSelect(Marker markerSelect) {
      this.markerSelect = markerSelect;
   }

   public ArrayList<MultivalueEntity> getListTypeServices() {
      return listTypeServices;
   }

   public void setListTypeServices(ArrayList<MultivalueEntity> listTypeServices) {
      this.listTypeServices = listTypeServices;
   }

   public ArrayList<MultivalueEntity> getListSchedules() {
      return listSchedules;
   }

   public void setListSchedules(ArrayList<MultivalueEntity> listSchedules) {
      this.listSchedules = listSchedules;
   }

   public String getJsonURL() {
      RequestContext.getCurrentInstance().execute("loadPolygon();");
      return jsonURL;
   }

   public void setJsonURL(String jsonURL) {
      this.jsonURL = jsonURL;
   }

   public String getJsonInt() {
      return jsonInt;
   }

   public void setJsonInt(String jsonInt) {
      this.jsonInt = jsonInt;
   }

   public ArrayList<MapDataDTO> getListMapaDataInt() {
      return listMapaDataInt;
   }

   public void setListMapaDataInt(ArrayList<MapDataDTO> listMapaDataInt) {
      this.listMapaDataInt = listMapaDataInt;
   }

   public CountryEntity getCountrySelect() {
      return countrySelect;
   }

   public void setCountrySelect(CountryEntity countrySelect) {
      this.countrySelect = countrySelect;
   }

   public boolean isInternational() {
      return international;
   }

   public void setInternational(boolean international) {
      this.international = international;
   }

   public boolean isSap() {
      return sap;
   }

   public void setSap(boolean sap) {
      this.sap = sap;
   }

}
