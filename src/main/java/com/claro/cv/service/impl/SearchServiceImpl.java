package com.claro.cv.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cv.dao.ClientProfileDAO;
import com.claro.cv.dao.ClientServiceDAO;
import com.claro.cv.dao.DepartamentDAO;
import com.claro.cv.dto.MapDataDTO;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.service.SearchService;


@Service
public class SearchServiceImpl implements SearchService, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1985267621493193707L;

   @Autowired
   private ClientProfileDAO clientProfileDAO;

   @Autowired
   private ClientServiceDAO clientServiceDAO;
   
   @Autowired
   private DepartamentDAO departamentDAO;

   @Override
   public ClientProfileEntity search(BigInteger idCliente, String codeService) throws Exception {
      if (idCliente != null && idCliente.intValue() != 0) {
         return clientProfileDAO.findByIDClient(idCliente);
      } else if (codeService != null && codeService.length() > 0) {
         ClientServiceEntity clientService = clientServiceDAO.findByCodeService(codeService);
         if (clientService != null) {
            return clientService.getClientProfile();
         }
      }
      return null;
   }

   @Override
   public ArrayList<MapDataDTO> loadMap(ClientProfileEntity clientProfile) throws Exception {
      return clientProfileDAO.getMapData(clientProfile.getIdClientProfile());
      
   }

   @Override
   public ArrayList<ClientServiceEntity> loadDetail(String regionSelect) throws Exception {
      ArrayList<ClientServiceEntity> listClientService = null;
      DepartamentEntity departamentEntity = departamentDAO.findByGeoCode(regionSelect);
      listClientService = clientServiceDAO.findByDepartament(departamentEntity);
      return listClientService;
   }

}
