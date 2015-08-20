package com.claro.cv.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

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
      sql.append("WHERE id_client_profile = :idClientProfile ");
      sql.append("GROUP BY id_department; ");
      Query query = entityManager.createNativeQuery(sql.toString());
      query.setParameter("idClientProfile", idClientProfile);

      List<Object[]> result = query.getResultList();
      if (result.size() > 0) {
         MapDataDTO mapData;
         listMapData = new ArrayList<MapDataDTO>();
         for (Object[] obj : result) {
            mapData = new MapDataDTO();
            mapData.setCodeDepartament((String) obj[0]);
            mapData.setNameDepartament((String) obj[1]);
            mapData.setNumberServices(((BigInteger) obj[2]).intValue());

            listMapData.add(mapData);
         }
      }

      return listMapData;

   }
}
