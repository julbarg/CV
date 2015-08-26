package com.claro.cv.dao;

import java.math.BigInteger;

import com.claro.cv.entity.ClientContactEntity;


public interface ClientContactDAO {

   public ClientContactEntity update(ClientContactEntity clientContactEntity) throws Exception;

   public void create(ClientContactEntity clientContactEntity) throws Exception;

   public void deleteByIdClientContact(BigInteger idClientContactDelete) throws Exception;

   public ClientContactEntity findByIdClientContact(BigInteger idClientContact) throws Exception;

}
