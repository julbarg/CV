package com.claro.cv.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

   @Override
   @Transactional(MANAGER)
   public void deleteByIdServiceFile(BigInteger idServiceFile) throws Exception {
      Query query = entityManager
         .createQuery("DELETE FROM ServiceFileEntity c WHERE c.idServiceFile =:idServiceFile");
      query.setParameter("idServiceFile", idServiceFile);
      query.executeUpdate();

   }

}
