package com.claro.cv.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientFileEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.enums.TypeMultivalueEnum;


@Service
public interface CreateService {

   public ArrayList<MultivalueEntity> loadMultiValue(TypeMultivalueEnum typeMultivalue) throws Exception;

   public void save(ClientProfileEntity clientProfile) throws Exception;

   public ArrayList<DepartamentEntity> loadDepartaments() throws Exception;

   public ArrayList<CityEntity> loadCities() throws Exception;

   public ArrayList<CityEntity> loadCitiesByDepartament(String idDepartament) throws Exception;

   public CityEntity findCityById(String idCity) throws Exception;

   public LastSettingFileEntity saveLastSettingFile(LastSettingFileEntity lastSettingFile) throws Exception;

   public ClientFileEntity saveClientFile(ClientFileEntity clientFile) throws Exception;

   public ArrayList<CountryEntity> loadCountries() throws Exception;

   public CountryEntity findCountryById(String idCountry) throws Exception;

}
