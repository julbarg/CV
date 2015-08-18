package com.claro.cv.service;

import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.claro.cv.dto.MapDataDTO;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;


@Service
public interface SearchService {

   public ClientProfileEntity search(BigInteger idCliente, String codeService) throws Exception;

   public ArrayList<MapDataDTO> loadMap(ClientProfileEntity clientProfile) throws Exception;

   public ArrayList<ClientServiceEntity> loadDetail(String regionSelect) throws Exception;

}
