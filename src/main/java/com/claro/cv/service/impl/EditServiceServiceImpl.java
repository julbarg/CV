package com.claro.cv.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cv.dao.CityDAO;
import com.claro.cv.dao.ClientServiceDAO;
import com.claro.cv.dao.CountryDAO;
import com.claro.cv.dao.DepartamentDAO;
import com.claro.cv.dao.LastSettingFileDAO;
import com.claro.cv.dao.ServiceContactDAO;
import com.claro.cv.dao.ServiceFileDAO;
import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.entity.ServiceFileEntity;
import com.claro.cv.service.EditServiceService;


@Service
public class EditServiceServiceImpl implements EditServiceService, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -1264573883593537530L;

   @Autowired
   private ClientServiceDAO clientServiceDAO;

   @Autowired
   private DepartamentDAO departamentDAO;

   @Autowired
   private CountryDAO countryDAO;

   @Autowired
   private CityDAO cityDAO;

   @Autowired
   private ServiceContactDAO serviceContactDAO;

   @Autowired
   private LastSettingFileDAO lastSettingFileDAO;

   @Autowired
   private ServiceFileDAO serviceFileDAO;

   @Override
   public ClientServiceEntity loadClientServiceEdit(BigInteger idClientServiceEdit) throws Exception {
      return clientServiceDAO.findByIdClientService(idClientServiceEdit);

   }

   @Override
   public ArrayList<ServiceContactEntity> loadServiceContacts(ClientServiceEntity clientServiceEdit)
      throws Exception {
      return serviceContactDAO.findByClientService(clientServiceEdit);
   }

   @Override
   public void deleteServiceContact(BigInteger idServiceContact) throws Exception {
      serviceContactDAO.deleteByIdClientContact(idServiceContact);

   }

   @Override
   public LastSettingFileEntity saveLastSettingFile(LastSettingFileEntity lastSettingFile) throws Exception {
      return lastSettingFileDAO.update(lastSettingFile);
   }

   @Override
   public void deleteFile(LastSettingFileEntity lastSettingFile) throws Exception {
      lastSettingFileDAO.delete(lastSettingFile);

   }

   @Override
   public ArrayList<ServiceFileEntity> loadServiceFiles(ClientServiceEntity clientService) throws Exception {
      return serviceFileDAO.findByClientService(clientService);
   }

   @Override
   public void saveServiceFile(ServiceFileEntity serviceFile) throws Exception {
      serviceFileDAO.create(serviceFile);

   }

   @Override
   public void deleteServiceFile(BigInteger idServiceFileDelete) throws Exception {
      serviceFileDAO.deleteByIdServiceFile(idServiceFileDelete);

   }

   @Override
   public void updateServiceContact(ServiceContactEntity serviceContact) throws Exception {
      serviceContactDAO.update(serviceContact);
   }

   @Override
   public ClientServiceEntity update(ClientServiceEntity clientServiceEdit) throws Exception {
      return clientServiceDAO.update(clientServiceEdit);

   }

   @Override
   public CityEntity loadCity(String idCity) throws Exception {
      return cityDAO.findById(idCity);
   }

   @Override
   public DepartamentEntity loadDepartament(String idDepartament) throws Exception {
      return departamentDAO.findById(idDepartament);

   }

   @Override
   public CountryEntity loadCountry(String idCountry) throws Exception {
      return countryDAO.findCountryById(idCountry);

   }
}
