package com.claro.cv.dao;

import java.math.BigInteger;

import com.claro.cv.entity.ClientFileEntity;


public interface ClientFileDAO {

   public ClientFileEntity update(ClientFileEntity clientFileEntity) throws Exception;

   public void deleteByIdClientConctact(BigInteger idClientContactDelete) throws Exception;
}
