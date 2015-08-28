package com.claro.cv.service.impl;

import java.io.Serializable;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cv.dao.CityDAO;
import com.claro.cv.dao.ClientFileDAO;
import com.claro.cv.dao.ClientProfileDAO;
import com.claro.cv.dao.CountryDAO;
import com.claro.cv.dao.DepartamentDAO;
import com.claro.cv.dao.LastSettingFileDAO;
import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientFileEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.service.CreateService;


@Service
public class CreateServiceImpl implements CreateService, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -613844153982230323L;

   @Autowired
   private ClientProfileDAO clientProfileDAO;

   @Autowired
   private DepartamentDAO departamentDAO;

   @Autowired
   private CityDAO cityDAO;

   @Autowired
   private LastSettingFileDAO lastSettingFileDAO;

   @Autowired
   private ClientFileDAO clientFileDAO;

   @Autowired
   private CountryDAO countryDAO;

   @Override
   public void save(ClientProfileEntity clientProfile) throws Exception {
      clientProfileDAO.create(clientProfile);
   }

   @Override
   public CityEntity findCityById(String idCity) throws Exception {
      return cityDAO.findById(idCity);
   }

   @Override
   public LastSettingFileEntity saveLastSettingFile(LastSettingFileEntity lastSettingFile) throws Exception {
      return lastSettingFileDAO.update(lastSettingFile);

   }

   @Override
   public ClientFileEntity saveClientFile(ClientFileEntity clientFile) throws Exception {
      return clientFileDAO.update(clientFile);
   }

   @Override
   public CountryEntity findCountryById(String idCountry) throws Exception {
      return countryDAO.findCountryById(idCountry);
   }

   @Override
   public ClientProfileEntity loadProfile(BigInteger idClient) throws Exception {
      return clientProfileDAO.findByIDClient(idClient);

   }

}
