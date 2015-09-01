package com.claro.cv.service;

import java.math.BigInteger;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.LastSettingFileEntity;


public interface AddServiceService {

   ClientProfileEntity loadClientProfileById(BigInteger idClientEdit) throws Exception;

   CityEntity findCityById(String idCity) throws Exception;

   CountryEntity findCountryById(String idCountry) throws Exception;

   LastSettingFileEntity saveLastSettingFile(LastSettingFileEntity lastSettingFile) throws Exception;

   void save(ClientServiceEntity clientService) throws Exception;

}
