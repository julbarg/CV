package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.ServiceContactEntity;


@Repository
public class ServiceContactDAOImpl extends TemplateDAO<ServiceContactEntity> implements ServiceContactDAO,
   Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 8186306693180448580L;

   @Override
   public ArrayList<ServiceContactEntity> findByClientService(ClientServiceEntity clientService)
      throws Exception {
      TypedQuery<ServiceContactEntity> query = entityManager.createNamedQuery(
         "ServiceContactEntity.findByClientService", ServiceContactEntity.class);
      query.setParameter("clientService", clientService);
      return (ArrayList<ServiceContactEntity>) query.getResultList();

   }

}
