package com.claro.cv.dao;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.claro.cv.entity.ClientFileEntity;


@Repository
public class ClientFileDAOImpl extends TemplateDAO<ClientFileEntity> implements ClientFileDAO, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 75200853045404853L;

   @Override
   @Transactional(MANAGER)
   public void deleteByIdClientConctact(BigInteger idClientFile) throws Exception {
      Query query = entityManager
         .createQuery("DELETE FROM ClientFileEntity c WHERE c.idClientFile =:idClientFile");
      query.setParameter("idClientFile", idClientFile);
      query.executeUpdate();

   }

}
