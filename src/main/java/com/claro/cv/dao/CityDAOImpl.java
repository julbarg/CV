package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.DepartamentEntity;


@Repository
public class CityDAOImpl extends TemplateDAO<CityEntity> implements CityDAO, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 4047835375837563523L;

   @Override
   public ArrayList<CityEntity> findAll() throws Exception {
      TypedQuery<CityEntity> query = entityManager.createNamedQuery("CityEntity.findAll", CityEntity.class);
      ArrayList<CityEntity> results = (ArrayList<CityEntity>) query.getResultList();

      return results;
   }

   @Override
   public ArrayList<CityEntity> findByDepartament(DepartamentEntity departament) throws Exception {
      TypedQuery<CityEntity> query = entityManager.createNamedQuery("CityEntity.findByDepartament",
         CityEntity.class);
      query.setParameter("departament", departament);
      ArrayList<CityEntity> results = (ArrayList<CityEntity>) query.getResultList();

      return results;
   }

   @Override
   public CityEntity findById(String idCity) throws Exception {
      TypedQuery<CityEntity> query = entityManager.createNamedQuery("CityEntity.findById", CityEntity.class);
      query.setParameter("idCity", idCity);
      ArrayList<CityEntity> results = (ArrayList<CityEntity>) query.getResultList();
      if (results.size() > 0) {
         return results.get(0);
      }
      return null;

   }

}
