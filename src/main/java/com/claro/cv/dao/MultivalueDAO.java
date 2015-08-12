package com.claro.cv.dao;

import java.util.ArrayList;

import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.TypeMultivalueEntity;


public interface MultivalueDAO {

   public ArrayList<MultivalueEntity> findByTypeMultivalue(TypeMultivalueEntity typeMultivalue)
      throws Exception;

}
