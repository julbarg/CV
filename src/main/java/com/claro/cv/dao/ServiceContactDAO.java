package com.claro.cv.dao;

import java.util.ArrayList;

import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.ServiceContactEntity;

public interface ServiceContactDAO {
   
   public ArrayList<ServiceContactEntity> findByClientService(ClientServiceEntity clientService) throws Exception;

}
