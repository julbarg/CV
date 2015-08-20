package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.TypeMultivalueEntity;


@Repository
public class TypeMultivalueDAOImpl extends TemplateDAO<TypeMultivalueEntity> implements TypeMultivalueDAO,
   Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -4826670023645416687L;

   @Override
   public TypeMultivalueEntity findByName(String name) throws Exception {
      ArrayList<TypeMultivalueEntity> results = new ArrayList<TypeMultivalueEntity>();
      TypedQuery<TypeMultivalueEntity> query = entityManager.createNamedQuery(
         "TypeMultivalueEntity.findByName", TypeMultivalueEntity.class);
      query.setParameter("name", name);
      results = (ArrayList<TypeMultivalueEntity>) query.getResultList();

      return results.get(0);
   }

}
