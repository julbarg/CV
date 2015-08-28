package com.claro.cv.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.claro.cv.entity.LastSettingFileEntity;


@Repository
public class LastSettingFileDAOImpl extends TemplateDAO<LastSettingFileEntity> implements LastSettingFileDAO, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -3928528963648329636L;

}
