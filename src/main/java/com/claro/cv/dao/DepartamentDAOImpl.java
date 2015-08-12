package com.claro.cv.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.DepartamentEntity;


@Repository
public class DepartamentDAOImpl extends TemplateDAO<DepartamentEntity> implements DepartamentDAO {

   @Override
   public ArrayList<DepartamentEntity> findAll() throws Exception {
      TypedQuery<DepartamentEntity> query = entityManager.createNamedQuery("DepartamentEntity.findAll",
         DepartamentEntity.class);
      ArrayList<DepartamentEntity> results = (ArrayList<DepartamentEntity>) query.getResultList();

      return results;
   }

   @Override
   public DepartamentEntity findById(String id) throws Exception {
      TypedQuery<DepartamentEntity> query = entityManager.createNamedQuery("DepartamentEntity.findById",
         DepartamentEntity.class);
      query.setParameter("idDepartament", id);
      ArrayList<DepartamentEntity> results = (ArrayList<DepartamentEntity>) query.getResultList();

      return results.get(0);
   }
}
