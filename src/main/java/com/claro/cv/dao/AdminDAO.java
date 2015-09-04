package com.claro.cv.dao;

import com.claro.cv.entity.AdminEntity;


public interface AdminDAO {

   AdminEntity findByUser(String userName) throws Exception;

}
