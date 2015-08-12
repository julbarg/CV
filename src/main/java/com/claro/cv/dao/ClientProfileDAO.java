package com.claro.cv.dao;

import java.util.ArrayList;

import com.claro.cv.entity.ClientProfileEntity;


public interface ClientProfileDAO {

   public ArrayList<ClientProfileEntity> findAll() throws Exception;

   public void create(ClientProfileEntity clientProfileEntity) throws Exception;

}
