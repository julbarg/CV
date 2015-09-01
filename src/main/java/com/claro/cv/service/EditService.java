package com.claro.cv.service;

import java.math.BigInteger;
import java.util.ArrayList;

import com.claro.cv.dto.EditSearchDTO;
import com.claro.cv.entity.ClientContactEntity;
import com.claro.cv.entity.ClientFileEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.ServiceContactEntity;
import com.claro.cv.entity.ServiceFileEntity;
import com.claro.cv.enums.TypeMultivalueEnum;


public interface EditService {

   ArrayList<ClientProfileEntity> loadClientProfileByEditSearch(EditSearchDTO editSearch) throws Exception;

   ClientProfileEntity loadClientProfileById(BigInteger idClientEdit) throws Exception;

   ArrayList<ClientServiceEntity> loadServicesByClientProfile(ClientProfileEntity clientEdit)
      throws Exception;

   void update(ClientProfileEntity clientEdit) throws Exception;

   void updateContacts(ArrayList<ClientContactEntity> listClientContact) throws Exception;

   void deleteContact(BigInteger idClientContactDelete) throws Exception;

   ClientFileEntity saveClientFile(ClientFileEntity clientFile) throws Exception;

   void deleteFile(BigInteger idClientContactDelete) throws Exception;

   String getNameFromMultivalue(TypeMultivalueEnum proveedorUltimaMilla, String idProviderLastMile)
      throws Exception;

   ArrayList<ServiceContactEntity> loadContact(ClientServiceEntity clientService) throws Exception;

   ArrayList<ServiceFileEntity> loadServiceFiles(ClientServiceEntity clientService) throws Exception;

}
