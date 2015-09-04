package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.AdminEntity;


@Repository
public class AdminDAOImpl extends TemplateDAO<AdminEntity> implements AdminDAO, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -4405919468558904769L;

   @Override
   public AdminEntity findByUser(String user) throws Exception {
      TypedQuery<AdminEntity> query = entityManager.createNamedQuery("AdminEntity.findByUser",
         AdminEntity.class);
      query.setParameter("user", user);
      ArrayList<AdminEntity> results = (ArrayList<AdminEntity>) query.getResultList();
      if (results.size() > 0) {
         return results.get(0);
      }
      return null;
   }

}
