package com.claro.cv.dao;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.ClientFileEntity;
import com.claro.cv.entity.DepartamentEntity;


@Repository
public class ClientFileDAOImpl extends TemplateDAO<ClientFileEntity> implements ClientFileDAO, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 75200853045404853L;

}
