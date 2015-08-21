package com.claro.cv.dao;

import java.util.ArrayList;

import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.ServiceFileEntity;


public interface ServiceFileDAO {

   ArrayList<ServiceFileEntity> findByClientService(ClientServiceEntity clientService) throws Exception;

}
