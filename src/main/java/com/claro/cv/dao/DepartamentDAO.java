package com.claro.cv.dao;

import java.util.ArrayList;

import com.claro.cv.entity.DepartamentEntity;


public interface DepartamentDAO {

   public ArrayList<DepartamentEntity> findAll() throws Exception;

   public DepartamentEntity findById(String id) throws Exception;

}
