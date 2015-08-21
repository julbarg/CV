package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;


@Repository
public class ClientServiceDAOImpl extends TemplateDAO<ClientServiceEntity> implements ClientServiceDAO,
   Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 7274911152818052741L;

   @Override
   public ClientServiceEntity findByCodeService(String codeService) throws Exception {
      TypedQuery<ClientServiceEntity> query = entityManager.createNamedQuery(
         "ClientServiceEntity.findByCodeService", ClientServiceEntity.class);
      query.setParameter("codeService", codeService);
      ArrayList<ClientServiceEntity> results = (ArrayList<ClientServiceEntity>) query.getResultList();
      if (results.size() > 0) {
         return results.get(0);
      } else {
         return null;
      }
   }

   @Override
   public ArrayList<ClientServiceEntity> findByDepartament(DepartamentEntity departament,
      ClientProfileEntity clientProfile) {
      TypedQuery<ClientServiceEntity> query = entityManager.createNamedQuery(
         "ClientServiceEntity.findByDepartament", ClientServiceEntity.class);
      query.setParameter("departament", departament);
      query.setParameter("clientProfile", clientProfile);

      ArrayList<ClientServiceEntity> results = (ArrayList<ClientServiceEntity>) query.getResultList();

      return results;

   }

   @Override
   public ArrayList<ClientServiceEntity> findByCountry(CountryEntity country,
      ClientProfileEntity clientProfile) throws Exception {
      TypedQuery<ClientServiceEntity> query = entityManager.createNamedQuery(
         "ClientServiceEntity.findByCountry", ClientServiceEntity.class);
      query.setParameter("country", country);
      query.setParameter("clientProfile", clientProfile);

      ArrayList<ClientServiceEntity> results = (ArrayList<ClientServiceEntity>) query.getResultList();

      return results;
   }
}
