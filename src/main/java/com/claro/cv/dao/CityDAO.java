package com.claro.cv.dao;

import java.util.ArrayList;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.DepartamentEntity;


public interface CityDAO {

   public ArrayList<CityEntity> findAll() throws Exception;

   public ArrayList<CityEntity> findByDepartament(DepartamentEntity departament) throws Exception;

   public CityEntity findById(String idCity) throws Exception;

}
