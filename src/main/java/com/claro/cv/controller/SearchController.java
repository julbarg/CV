package com.claro.cv.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

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
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.service.SearchService;
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

   private BigInteger idCliente;

   private String codigoServiicio;

   private ClientProfileEntity clientProfile;

   private String json;

   private String jsonURL;

   private DepartamentEntity departamentSelect;

   private MapModel mapModel;

   @Autowired
   private SearchService searchService;

   private ArrayList<MapDataDTO> listMapaData;

   private String centerMap;

   private int zoomMap;

   private ArrayList<ClientServiceEntity> listServicesByDepartamentSelect;

   private Marker markerSelect;

   private ArrayList<MultivalueEntity> listTypeServices;

   private ArrayList<MultivalueEntity> listSchedules;

   @PostConstruct
   public void initialize() {
      clientProfile = new ClientProfileEntity();
      loadTypeServices();
      loadSchedules();
   }

   private void loadTypeServices() {
      try {
         listTypeServices = searchService.loadTypeServices();
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_TYPE_SERVICE_ERROR, e);
      }
   }

   private void loadSchedules() {
      try {
         listSchedules = searchService.loadSchedules();
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_SCHEDULE_ERROR, e);
      }
   }

   public String search() {
      try {
         if (validateParameters()) {
            clientProfile = searchService.search(idCliente, codigoServiicio);
            if (clientProfile != null) {

               Util.addMessageInfo(clientProfile.getNameClient());
               idCliente = null;
               codigoServiicio = null;
               loadMap();
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

   private void loadMap() {
      try {
         listMapaData = searchService.loadMap(clientProfile);
         json = MapJsonUtil.generateJson(listMapaData);
      } catch (Exception e) {
         LOGGER.error(Messages.ERROR_LOAD_MAP, e);
      }
   }

   public void loadDetail() {
      String regionSelect = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
         .get("regionSelect");
      try {
         departamentSelect = searchService.loadDepartament(regionSelect);
         listServicesByDepartamentSelect = searchService.loadDetail(departamentSelect);
         if (listServicesByDepartamentSelect != null && listServicesByDepartamentSelect.size() > 0) {
            setUpMap();
            FacesContext.getCurrentInstance().getExternalContext()
               .redirect(Constant.DETAIL_PAGE + Constant.XHTML);
            loadPolygon();
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

   private void getMarkers() {
      LatLng coord;
      Marker marker;
      double lat;
      double lng;
      mapModel = new DefaultMapModel();
      for (ClientServiceEntity service : listServicesByDepartamentSelect) {
         lat = Double.parseDouble(service.getLat());
         lng = Double.parseDouble(service.getLng());

         service.setMainPoint(Util.getMeansFlag(service.getMainPoint()));
         service.setBackup(Util.getMeansFlag(service.getBackup()));
         service.setTypeService(getTypeService(service));

         coord = new LatLng(lat, lng);
         marker = new Marker(coord, service.getAlias(), service);
         mapModel.addOverlay(marker);
      }
   }

   private void loadPolygon() {
   }

   private String getTypeService(ClientServiceEntity service) {
      return Util.getMeansMultiValue(listTypeServices, service.getTypeService());
   }

   public String getSchedule(String valueSchedule) {
      return Util.getMeansMultiValue(listSchedules, valueSchedule);
   }

   public void onMarkerSelect(OverlaySelectEvent event) {
      setMarkerSelect((Marker) event.getOverlay());
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

   public StreamedContent downloadLastSettingsFile() {
      try {
         LastSettingFileEntity file = ((ClientServiceEntity) markerSelect.getData()).getLastSettingFile();
         InputStream stream = new FileInputStream(file.getUrl());
         return new DefaultStreamedContent(stream, Constant.TXT, file.getNameFile() + Constant.TXT_EXTENSION);
      } catch (FileNotFoundException e) {
         LOGGER.error(Messages.LOAD_LAST_SETTINGS_FILE, e);
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

   public ArrayList<ClientServiceEntity> getListServicesByDepartamentSelect() {
      return listServicesByDepartamentSelect;
   }

   public void setListServicesByDepartamentSelect(
      ArrayList<ClientServiceEntity> listServicesByDepartamentSelect) {
      this.listServicesByDepartamentSelect = listServicesByDepartamentSelect;
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

}
