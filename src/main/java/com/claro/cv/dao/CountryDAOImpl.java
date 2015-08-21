package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.CountryEntity;


@Repository
public class CountryDAOImpl extends TemplateDAO<CountryEntity> implements CountryDAO, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -4322598618156440673L;

   @Override
   public ArrayList<CountryEntity> findAll() throws Exception {
      TypedQuery<CountryEntity> query = entityManager.createNamedQuery("CountryEntity.findAll",
         CountryEntity.class);
      ArrayList<CountryEntity> results = (ArrayList<CountryEntity>) query.getResultList();

      return results;
   }

   @Override
   public CountryEntity findCountryById(String idCountry) throws Exception {
      TypedQuery<CountryEntity> query = entityManager.createNamedQuery("CountryEntity.findCountryById",
         CountryEntity.class);
      query.setParameter("idCountry", idCountry);
      ArrayList<CountryEntity> results = (ArrayList<CountryEntity>) query.getResultList();

      return results.get(0);
   }

   @Override
   public CountryEntity findByGeoCode(String geocode) throws Exception {
      TypedQuery<CountryEntity> query = entityManager.createNamedQuery("CountryEntity.findByGeoCode",
         CountryEntity.class);
      query.setParameter("geocode", geocode);
      ArrayList<CountryEntity> results = (ArrayList<CountryEntity>) query.getResultList();

      return results.get(0);
   }
}
