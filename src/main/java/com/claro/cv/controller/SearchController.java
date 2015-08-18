package com.claro.cv.controller;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.dto.MapDataDTO;
import com.claro.cv.entity.ClientProfileEntity;
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

   @Autowired
   private SearchService searchService;

   private ArrayList<MapDataDTO> listMapaData;

   @PostConstruct
   public void initialize() {
      clientProfile = new ClientProfileEntity();
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
         if (searchService.loadDetail(regionSelect) != null
            && searchService.loadDetail(regionSelect).size() > 0) {
            Util.addMessageInfo(searchService.loadDetail(regionSelect).size() + " Encontrados");
         } else {
            Util.addMessageError(Messages.NO_SERVICES_REGION);
         }
      } catch (Exception e) {
         LOGGER.error(Messages.LOAD_DETAIL_ERROR, e);
      }
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

}
