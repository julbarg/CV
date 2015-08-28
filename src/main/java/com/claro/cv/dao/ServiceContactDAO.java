package com.claro.cv.dao;

import java.math.BigInteger;
import java.util.ArrayList;

import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.ServiceContactEntity;


public interface ServiceContactDAO {

   public ArrayList<ServiceContactEntity> findByClientService(ClientServiceEntity clientService)
      throws Exception;

   public void deleteByIdClientContact(BigInteger idServiceContact) throws Exception;

   public ServiceContactEntity update(ServiceContactEntity serviceContactEntity) throws Exception;

}
