package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.ServiceFileEntity;


@Repository
public class ServiceFileDAOImpl extends TemplateDAO<ServiceFileEntity> implements ServiceFileDAO,
   Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 8186306693180448580L;

   @Override
   public ArrayList<ServiceFileEntity> findByClientService(ClientServiceEntity clientService)
      throws Exception {
      TypedQuery<ServiceFileEntity> query = entityManager.createNamedQuery(
         "ServiceFileEntity.findByClientService", ServiceFileEntity.class);
      query.setParameter("clientService", clientService);
      return (ArrayList<ServiceFileEntity>) query.getResultList();
   }

}
