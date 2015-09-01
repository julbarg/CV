package com.claro.cv.service.impl;

import java.io.Serializable;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cv.dao.CityDAO;
import com.claro.cv.dao.ClientProfileDAO;
import com.claro.cv.dao.ClientServiceDAO;
import com.claro.cv.dao.CountryDAO;
import com.claro.cv.dao.LastSettingFileDAO;
import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.service.AddServiceService;


@Service
public class AddServiceServiceImpl implements AddServiceService, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -1438396836418971904L;

   @Autowired
   private ClientProfileDAO clientProfileDAO;

   @Autowired
   private CityDAO cityDAO;

   @Autowired
   private CountryDAO countryDAO;

   @Autowired
   private LastSettingFileDAO lastSettingFileDAO;

   @Autowired
   private ClientServiceDAO clientServiceDAO;

   @Override
   public ClientProfileEntity loadClientProfileById(BigInteger idClientEdit) throws Exception {
      return clientProfileDAO.findByIDClient(idClientEdit);
   }

   @Override
   public CityEntity findCityById(String idCity) throws Exception {
      return cityDAO.findById(idCity);
   }

   @Override
   public CountryEntity findCountryById(String idCountry) throws Exception {
      return countryDAO.findCountryById(idCountry);
   }

   @Override
   public LastSettingFileEntity saveLastSettingFile(LastSettingFileEntity lastSettingFile) throws Exception {
      return lastSettingFileDAO.update(lastSettingFile);
   }

   @Override
   public void save(ClientServiceEntity clientService) throws Exception {
      clientServiceDAO.create(clientService);

   }

}
