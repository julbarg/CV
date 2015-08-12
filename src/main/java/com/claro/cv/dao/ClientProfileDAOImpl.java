package com.claro.cv.dao;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.ClientProfileEntity;


@Repository
public class ClientProfileDAOImpl extends TemplateDAO<ClientProfileEntity> implements ClientProfileDAO {

   @Override
   public ArrayList<ClientProfileEntity> findAll() throws Exception {
      TypedQuery<ClientProfileEntity> query = entityManager.createNamedQuery("ClientProfileEntity.findAll",
         ClientProfileEntity.class);
      ArrayList<ClientProfileEntity> results = (ArrayList<ClientProfileEntity>) query.getResultList();

      return results;

   }

}
