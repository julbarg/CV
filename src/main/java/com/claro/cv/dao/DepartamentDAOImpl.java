package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.DepartamentEntity;


@Repository
public class DepartamentDAOImpl extends TemplateDAO<DepartamentEntity> implements DepartamentDAO,
   Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 8204739158167076194L;

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

      if (results.size() > 0) {
         return results.get(0);
      }

      return null;

   }

   @Override
   public DepartamentEntity findByGeoCode(String geocode) throws Exception {
      TypedQuery<DepartamentEntity> query = entityManager.createNamedQuery("DepartamentEntity.findByGeoCode",
         DepartamentEntity.class);
      query.setParameter("geocode", geocode);
      ArrayList<DepartamentEntity> results = (ArrayList<DepartamentEntity>) query.getResultList();

      return results.get(0);
   }
}
