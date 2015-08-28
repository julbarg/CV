package com.claro.cv.dao;

import java.math.BigInteger;
import java.util.ArrayList;

import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.ServiceFileEntity;


public interface ServiceFileDAO {

   ArrayList<ServiceFileEntity> findByClientService(ClientServiceEntity clientService) throws Exception;

   public void create(ServiceFileEntity serviceFileEntity) throws Exception;

   void deleteByIdServiceFile(BigInteger idServiceFileDelete) throws Exception;

}
