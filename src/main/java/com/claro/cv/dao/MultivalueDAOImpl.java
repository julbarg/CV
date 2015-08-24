package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.TypeMultivalueEntity;


@Repository
public class MultivalueDAOImpl extends TemplateDAO<MultivalueEntity> implements MultivalueDAO, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 2315977054460289846L;

   @Override
   public ArrayList<MultivalueEntity> findByTypeMultivalue(TypeMultivalueEntity typeMultivalue)
      throws Exception {
      ArrayList<MultivalueEntity> results = new ArrayList<MultivalueEntity>();
      TypedQuery<MultivalueEntity> query = entityManager.createNamedQuery(
         "MultivalueEntity.findByTypeMultivalue", MultivalueEntity.class);
      query.setParameter("typeMultivalue", typeMultivalue);
      results = (ArrayList<MultivalueEntity>) query.getResultList();

      return results;
   }

   @Override
   public MultivalueEntity findByTypeMultivalueAndValue(TypeMultivalueEntity typeMultivalue, String code)
      throws Exception {
      ArrayList<MultivalueEntity> results = new ArrayList<MultivalueEntity>();
      TypedQuery<MultivalueEntity> query = entityManager.createNamedQuery("MultivalueEntity.findByValue",
         MultivalueEntity.class);
      query.setParameter("typeMultivalue", typeMultivalue);
      query.setParameter("code", code);
      results = (ArrayList<MultivalueEntity>) query.getResultList();

      return results.get(0);
   }

}
