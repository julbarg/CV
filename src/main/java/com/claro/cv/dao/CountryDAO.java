package com.claro.cv.dao;

import java.util.ArrayList;

import com.claro.cv.entity.CountryEntity;


public interface CountryDAO {

   public ArrayList<CountryEntity> findAll() throws Exception;

   public CountryEntity findCountryById(String idCountry) throws Exception;

   public CountryEntity findByGeoCode(String geocode) throws Exception;

}
