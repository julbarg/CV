package com.claro.cv.dao;

import java.math.BigInteger;
import java.util.ArrayList;

import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;


public interface ClientServiceDAO {

   public ClientServiceEntity findByCodeService(String codeService) throws Exception;

   public ArrayList<ClientServiceEntity> findByDepartament(DepartamentEntity departamentEntity,
      ClientProfileEntity clientProfileEntity);

   public ArrayList<ClientServiceEntity> findByCountry(CountryEntity country,
      ClientProfileEntity clientProfile) throws Exception;

   public ArrayList<ClientServiceEntity> findByClientProfile(ClientProfileEntity clientProfile)
      throws Exception;

   public ClientServiceEntity findByIdClientService(BigInteger idClientServiceEdit) throws Exception;

   public ClientServiceEntity update(ClientServiceEntity clientServiceEntity) throws Exception;
   
   public void create(ClientServiceEntity clientServiceEntity) throws Exception;

}
