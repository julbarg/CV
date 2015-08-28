package com.claro.cv.service;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cv.dao.CityDAO;
import com.claro.cv.dao.CountryDAO;
import com.claro.cv.dao.DepartamentDAO;
import com.claro.cv.dao.MultivalueDAO;
import com.claro.cv.dao.TypeMultivalueDAO;
import com.claro.cv.entity.CityEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.TypeMultivalueEntity;
import com.claro.cv.enums.TypeMultivalueEnum;


@Service
public class UtilServiceImpl implements UtilService, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 3347754651231558665L;

   @Autowired
   private MultivalueDAO multivalueDAO;

   @Autowired
   private TypeMultivalueDAO typeMultivalueDAO;

   @Autowired
   private CountryDAO countryDAO;

   @Autowired
   private DepartamentDAO departamentDAO;

   @Autowired
   private CityDAO cityDAO;

   @Override
   public ArrayList<CountryEntity> loadCountries() throws Exception {
      return countryDAO.findAll();
   }

   @Override
   public ArrayList<CityEntity> loadCities() throws Exception {
      return cityDAO.findAll();
   }

   @Override
   public ArrayList<CityEntity> loadCitiesByDepartament(String idDepartament) throws Exception {
      DepartamentEntity departament = departamentDAO.findById(idDepartament);
      return cityDAO.findByDepartament(departament);
   }

   @Override
   public ArrayList<DepartamentEntity> loadDepartaments() throws Exception {
      return departamentDAO.findAll();
   }

   @Override
   public ArrayList<MultivalueEntity> loadMultiValue(TypeMultivalueEnum typeMultivalueEnum) throws Exception {
      TypeMultivalueEntity typeMultivalue = typeMultivalueDAO.findByName(typeMultivalueEnum.getValue());
      return multivalueDAO.findByTypeMultivalue(typeMultivalue);
   }

}
