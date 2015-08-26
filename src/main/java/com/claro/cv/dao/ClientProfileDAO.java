package com.claro.cv.dao;

import java.math.BigInteger;
import java.util.ArrayList;

import com.claro.cv.dto.EditSearchDTO;
import com.claro.cv.dto.MapDataDTO;
import com.claro.cv.entity.ClientProfileEntity;


public interface ClientProfileDAO {

   public ArrayList<ClientProfileEntity> findAll() throws Exception;

   public void create(ClientProfileEntity clientProfileEntity) throws Exception;
   
   public ClientProfileEntity update(ClientProfileEntity clientProfileEntity) throws Exception;

   public ClientProfileEntity findByIDClient(BigInteger idClient) throws Exception;

   public ArrayList<MapDataDTO> getMapData(BigInteger idClientProfile) throws Exception;

   public ArrayList<MapDataDTO> getMapDataInt(BigInteger idClientProfile) throws Exception;

   public ArrayList<ClientProfileEntity> findByEditSearch(EditSearchDTO editSearch) throws Exception;

}
