package com.claro.cv.service;

import java.util.ArrayList;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.enums.TypeMultivalueEnum;


public interface UtilService {

   ArrayList<MultivalueEntity> loadMultiValue(TypeMultivalueEnum tipoServicio) throws Exception;

   ArrayList<CountryEntity> loadCountries() throws Exception;

   ArrayList<CityEntity> loadCities() throws Exception;

   ArrayList<CityEntity> loadCitiesByDepartament(String idDepartament) throws Exception;

   ArrayList<DepartamentEntity> loadDepartaments() throws Exception;

}
