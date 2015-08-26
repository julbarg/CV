package com.claro.cv.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.claro.cv.entity.ClientContactEntity;


@Repository
public class ClientContactDAOImpl extends TemplateDAO<ClientContactEntity> implements ClientContactDAO,
   Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   @Override
   @Transactional(MANAGER)
   public void deleteByIdClientContact(BigInteger idClientContactDelete) throws Exception {

      Query query = entityManager
         .createQuery("DELETE FROM ClientContactEntity c WHERE c.idClientContact =:idClientContact");
      query.setParameter("idClientContact", idClientContactDelete);
      query.executeUpdate();

   }

   @Override
   public ClientContactEntity findByIdClientContact(BigInteger idClientContact) throws Exception {
      TypedQuery<ClientContactEntity> query = entityManager.createNamedQuery(
         "ClientContactEntity.findByIdClientContact", ClientContactEntity.class);
      query.setParameter("idClientContact", idClientContact);
      ArrayList<ClientContactEntity> results = (ArrayList<ClientContactEntity>) query.getResultList();
      if (results.size() > 0) {
         return results.get(0);
      } else {
         return null;
      }
   }

}
