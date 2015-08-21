package com.claro.cv.service;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.claro.cv.dto.MapDataDTO;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.CountryEntity;
import com.claro.cv.entity.DepartamentEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.entity.ServiceFileEntity;


@Service
public interface SearchService {

   public ClientProfileEntity search(BigInteger idCliente, String codeService) throws Exception;

   public ArrayList<MapDataDTO> loadMap(ClientProfileEntity clientProfile) throws Exception;

   public ArrayList<ClientServiceEntity> loadDetail(DepartamentEntity departamentSelect,
      ClientProfileEntity clientProfile) throws Exception;

   public ArrayList<ClientServiceEntity> loadDetailInt(CountryEntity countrySelect,
      ClientProfileEntity clientProfile) throws Exception;

   public DepartamentEntity loadDepartament(String regionSelect) throws Exception;

   public CountryEntity loadCountry(String regionSelect) throws Exception;

   public ArrayList<MultivalueEntity> loadTypeServices() throws Exception;

   public ArrayList<MultivalueEntity> loadSchedules() throws Exception;

   public ArrayList<ServiceContactEntity> loadContact(ClientServiceEntity service) throws Exception;

   public ArrayList<ServiceFileEntity> loadServiceFiles(ClientServiceEntity data) throws Exception;

   public ArrayList<MapDataDTO> loadMapInt(ClientProfileEntity clientProfile) throws Exception;

}
