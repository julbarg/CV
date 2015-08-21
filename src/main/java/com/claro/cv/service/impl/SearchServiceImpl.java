package com.claro.cv.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cv.dao.ClientProfileDAO;
import com.claro.cv.dao.ClientServiceDAO;
import com.claro.cv.dao.CountryDAO;
import com.claro.cv.dao.DepartamentDAO;
import com.claro.cv.dao.MultivalueDAO;
import com.claro.cv.dao.ServiceContactDAO;
import com.claro.cv.dao.ServiceFileDAO;
import com.claro.cv.dao.TypeMultivalueDAO;
import com.claro.cv.dto.MapDataDTO;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.entity.ServiceFileEntity;
import com.claro.cv.entity.TypeMultivalueEntity;
import com.claro.cv.enums.TypeMultivalueEnum;
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

   @Autowired
   private CountryDAO countryDAO;

   @Autowired
   private MultivalueDAO multivalueDAO;

   @Autowired
   private TypeMultivalueDAO typeMultivalueDAO;

   @Autowired
   private ServiceContactDAO serviceContactDAO;

   @Autowired
   private ServiceFileDAO serviceFileDAO;

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
   public ArrayList<MapDataDTO> loadMapInt(ClientProfileEntity clientProfile) throws Exception {
      return clientProfileDAO.getMapDataInt(clientProfile.getIdClientProfile());
   }

   @Override
   public ArrayList<ClientServiceEntity> loadDetail(DepartamentEntity departamentEntity,
      ClientProfileEntity clientProfileEntity) throws Exception {
      ArrayList<ClientServiceEntity> listClientService = null;
      listClientService = clientServiceDAO.findByDepartament(departamentEntity, clientProfileEntity);
      return listClientService;
   }

   @Override
   public ArrayList<ClientServiceEntity> loadDetailInt(CountryEntity countrySelect,
      ClientProfileEntity clientProfile) throws Exception {
      ArrayList<ClientServiceEntity> listClientService = null;
      listClientService = clientServiceDAO.findByCountry(countrySelect, clientProfile);
      return listClientService;
   }

   @Override
   public DepartamentEntity loadDepartament(String regionSelect) throws Exception {
      return departamentDAO.findByGeoCode(regionSelect);
   }

   @Override
   public CountryEntity loadCountry(String regionSelect) throws Exception {
      return countryDAO.findByGeoCode(regionSelect);
   }

   @Override
   public ArrayList<MultivalueEntity> loadTypeServices() throws Exception {
      TypeMultivalueEntity typeMultivalue = typeMultivalueDAO.findByName(TypeMultivalueEnum.TIPO_SERVICIO
         .getValue());
      return multivalueDAO.findByTypeMultivalue(typeMultivalue);
   }

   @Override
   public ArrayList<MultivalueEntity> loadSchedules() throws Exception {
      TypeMultivalueEntity typeMultivalue = typeMultivalueDAO.findByName(TypeMultivalueEnum.HORARIO_ATENCION
         .getValue());
      return multivalueDAO.findByTypeMultivalue(typeMultivalue);
   }

   @Override
   public ArrayList<ServiceContactEntity> loadContact(ClientServiceEntity service) throws Exception {
      return serviceContactDAO.findByClientService(service);

   }

   @Override
   public ArrayList<ServiceFileEntity> loadServiceFiles(ClientServiceEntity clientService) throws Exception {
      return serviceFileDAO.findByClientService(clientService);
   }

}
