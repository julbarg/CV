package com.claro.cv.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.dto.EditSearchDTO;
import com.claro.cv.dto.MapDataDTO;
import com.claro.cv.entity.ClientProfileEntity;


@Repository
public class ClientProfileDAOImpl extends TemplateDAO<ClientProfileEntity> implements ClientProfileDAO,
   Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 7848732737052322587L;

   @Override
   public ArrayList<ClientProfileEntity> findAll() throws Exception {
      TypedQuery<ClientProfileEntity> query = entityManager.createNamedQuery("ClientProfileEntity.findAll",
         ClientProfileEntity.class);
      ArrayList<ClientProfileEntity> results = (ArrayList<ClientProfileEntity>) query.getResultList();

      return results;

   }

   @Override
   public ClientProfileEntity findByIDClient(BigInteger idClient) throws Exception {
      TypedQuery<ClientProfileEntity> query = entityManager.createNamedQuery(
         "ClientProfileEntity.findByIdClient", ClientProfileEntity.class);
      query.setParameter("idClient", idClient);
      ArrayList<ClientProfileEntity> results = (ArrayList<ClientProfileEntity>) query.getResultList();
      if (results.size() > 0) {
         return results.get(0);
      }
      return null;
   }

   @SuppressWarnings("unchecked")
   @Override
   public ArrayList<MapDataDTO> getMapData(BigInteger idClientProfile) throws Exception {
      ArrayList<MapDataDTO> listMapData = null;
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT d.geocode, d.name,  COUNT(s.id_client_service) FROM client_service s ");
      sql.append("LEFT JOIN departament d ON s.id_department = d.id_departament ");
      sql.append("WHERE s.id_client_profile = :idClientProfile ");
      sql.append("AND s.id_department IS NOT NULL ");
      sql.append("GROUP BY s.id_department; ");
      Query query = entityManager.createNativeQuery(sql.toString());
      query.setParameter("idClientProfile", idClientProfile);

      List<Object[]> result = query.getResultList();
      if (result.size() > 0) {
         MapDataDTO mapData;
         listMapData = new ArrayList<MapDataDTO>();
         for (Object[] obj : result) {
            mapData = new MapDataDTO();
            mapData.setCode((String) obj[0]);
            mapData.setName((String) obj[1]);
            mapData.setNumberServices(((BigInteger) obj[2]).intValue());

            listMapData.add(mapData);
         }
      }

      return listMapData;

   }

   @SuppressWarnings("unchecked")
   @Override
   public ArrayList<MapDataDTO> getMapDataInt(BigInteger idClientProfile) throws Exception {
      ArrayList<MapDataDTO> listMapData = null;
      StringBuffer sql = new StringBuffer();
      sql.append("SELECT c.geocode, c.name, COUNT(s.id_client_service) FROM client_service s ");
      sql.append("LEFT JOIN country c ON s.id_country = c.id_country ");
      sql.append("WHERE s.id_client_profile = :idClientProfile ");
      sql.append("AND s.id_country IS NOT NULL ");
      sql.append("GROUP BY s.id_country; ");

      Query query = entityManager.createNativeQuery(sql.toString());
      query.setParameter("idClientProfile", idClientProfile);

      List<Object[]> result = query.getResultList();
      if (result.size() > 0) {
         MapDataDTO mapData;
         listMapData = new ArrayList<MapDataDTO>();
         for (Object[] obj : result) {
            mapData = new MapDataDTO();
            mapData.setCode((String) obj[0]);
            mapData.setName((String) obj[1]);
            mapData.setNumberServices(((BigInteger) obj[2]).intValue());

            listMapData.add(mapData);
         }
      }

      return listMapData;
   }

   @SuppressWarnings("unchecked")
   @Override
   public ArrayList<ClientProfileEntity> findByEditSearch(EditSearchDTO editSearch) throws Exception {
      String nameClient = editSearch.getNameClient();
      BigInteger idClient = editSearch.getIdClient();
      String nitClient = editSearch.getNitClient();
      String codeService = editSearch.getCodeService();
      String state = editSearch.getState();

      Query query = entityManager.createQuery(getSQL(editSearch));

      if (nameClient.length() > 0) {
         query.setParameter("nameClient", getLike(nameClient));
      }
      if (idClient != null && idClient.intValue() > 0) {
         query.setParameter("idClient", idClient);
      }
      if (nitClient.length() > 0) {
         query.setParameter("nitClient", getLike(nitClient));
      }
      if (codeService.length() > 0) {
         query.setParameter("codeService", getLike(codeService));
      }
      if (state.length() > 0) {
         query.setParameter("state", state);
      }

      ArrayList<ClientProfileEntity> results = (ArrayList<ClientProfileEntity>) query.getResultList();

      return results;
   }

   private String getSQL(EditSearchDTO editSearch) {
      StringBuffer sql = new StringBuffer();
      String nameClient = editSearch.getNameClient();
      BigInteger idClient = editSearch.getIdClient();
      String nitClient = editSearch.getNitClient();
      String codeService = editSearch.getCodeService();
      String state = editSearch.getState();

      sql.append("SELECT DISTINCT s.clientProfile FROM ClientServiceEntity s ");
      sql.append("LEFT OUTER JOIN s.clientProfile c ");
      sql.append("WHERE 1=1 ");
      if (nameClient.length() > 0) {
         sql.append("AND c.nameClient LIKE :nameClient ");
      }

      if (idClient != null && idClient.intValue() > 0) {
         sql.append("AND c.idClient =:idClient ");
      }
      if (nitClient.length() > 0) {
         sql.append("AND c.nitClient LIKE :nitClient ");
      }
      if (codeService.length() > 0) {
         sql.append("AND s.codeService LIKE :codeService ");
      }
      if (state.length() > 0) {
         sql.append("AND s.state =:state ");
      }

      return sql.toString();
   }

   private String getLike(String value) {
      return "%" + value + "%";
   }
}
