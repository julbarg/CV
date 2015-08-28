package com.claro.cv.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientFileEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.LastSettingFileEntity;


@Service
public interface CreateService {

   public void save(ClientProfileEntity clientProfile) throws Exception;

   public CityEntity findCityById(String idCity) throws Exception;

   public LastSettingFileEntity saveLastSettingFile(LastSettingFileEntity lastSettingFile) throws Exception;

   public ClientFileEntity saveClientFile(ClientFileEntity clientFile) throws Exception;

   public CountryEntity findCountryById(String idCountry) throws Exception;

   public ClientProfileEntity loadProfile(BigInteger idClient) throws Exception;

}
