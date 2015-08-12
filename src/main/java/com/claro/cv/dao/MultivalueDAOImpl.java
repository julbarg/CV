package com.claro.cv.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.TypeMultivalueEntity;


@Repository
public class MultivalueDAOImpl extends TemplateDAO<MultivalueEntity> implements MultivalueDAO {

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

}
