package com.claro.cv.dao;

import com.claro.cv.entity.TypeMultivalueEntity;


public interface TypeMultivalueDAO {

   public TypeMultivalueEntity findByName(String name) throws Exception;

}
