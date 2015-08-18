package com.claro.cv.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.DepartamentEntity;


@Repository
public class ClientServiceDAOImpl extends TemplateDAO<ClientServiceEntity> implements ClientServiceDAO {

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
   public ArrayList<ClientServiceEntity> findByDepartament(DepartamentEntity departament) {
      TypedQuery<ClientServiceEntity> query = entityManager.createNamedQuery(
         "ClientServiceEntity.findByDepartament", ClientServiceEntity.class);
      query.setParameter("departament", departament);
      ArrayList<ClientServiceEntity> results = (ArrayList<ClientServiceEntity>) query.getResultList();

      return results;

   }
}
