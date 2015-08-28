package com.claro.cv.service;

import java.math.BigInteger;
import java.util.ArrayList;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.LastSettingFileEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.entity.ServiceFileEntity;


public interface EditServiceService {

   ClientServiceEntity loadClientServiceEdit(BigInteger idClientServiceEdit) throws Exception;

   ArrayList<ServiceContactEntity> loadServiceContacts(ClientServiceEntity clientServiceEdit)
      throws Exception;

   void deleteServiceContact(BigInteger idClientContactDelete) throws Exception;

   LastSettingFileEntity saveLastSettingFile(LastSettingFileEntity lastSettingFile) throws Exception;

   void deleteFile(LastSettingFileEntity lastSettingFile) throws Exception;

   ArrayList<ServiceFileEntity> loadServiceFiles(ClientServiceEntity clientServiceEdit) throws Exception;

   void saveServiceFile(ServiceFileEntity serviceFile) throws Exception;

   void deleteServiceFile(BigInteger idServiceFileDelete) throws Exception;

   void updateServiceContact(ServiceContactEntity serviceContact) throws Exception;

   ClientServiceEntity update(ClientServiceEntity clientServiceEdit) throws Exception;

   CityEntity loadCity(String idCity) throws Exception;

   DepartamentEntity loadDepartament(String idDepartament) throws Exception;

   CountryEntity loadCountry(String idCountry) throws Exception;

}
