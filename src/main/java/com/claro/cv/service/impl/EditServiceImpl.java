package com.claro.cv.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.cv.dao.ClientContactDAO;
import com.claro.cv.dao.ClientFileDAO;
import com.claro.cv.dao.ClientProfileDAO;
import com.claro.cv.dao.ClientServiceDAO;
import com.claro.cv.dao.MultivalueDAO;
import com.claro.cv.dao.TypeMultivalueDAO;
import com.claro.cv.dto.EditSearchDTO;
import com.claro.cv.entity.ClientContactEntity;
import com.claro.cv.entity.ClientFileEntity;
import com.claro.cv.entity.ClientProfileEntity;
import com.claro.cv.entity.ClientServiceEntity;
import com.claro.cv.entity.MultivalueEntity;
import com.claro.cv.entity.TypeMultivalueEntity;
import com.claro.cv.enums.TypeMultivalueEnum;
import com.claro.cv.service.EditService;


@Service
public class EditServiceImpl implements EditService, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 5589406898524444244L;

   @Autowired
   private ClientProfileDAO clientProfileDAO;

   @Autowired
   private MultivalueDAO multivalueDAO;

   @Autowired
   private TypeMultivalueDAO typeMultivalueDAO;

   @Autowired
   private ClientServiceDAO clientServiceDAO;

   @Autowired
   private ClientContactDAO clientContactDAO;

   @Autowired
   private ClientFileDAO clientFileDAO;

   @Override
   public ArrayList<ClientProfileEntity> loadClientProfileByEditSearch(EditSearchDTO editSearch)
      throws Exception {
      return clientProfileDAO.findByEditSearch(editSearch);

   }

   @Override
   public ClientProfileEntity loadClientProfileById(BigInteger idClient) throws Exception {
      return clientProfileDAO.findByIDClient(idClient);
   }

   @Override
   public ArrayList<ClientServiceEntity> loadServicesByClientProfile(ClientProfileEntity clientProfile)
      throws Exception {
      return clientServiceDAO.findByClientProfile(clientProfile);
   }

   @Override
   public void update(ClientProfileEntity clientEdit) throws Exception {
      clientProfileDAO.update(clientEdit);

   }

   @Override
   public void updateContacts(ArrayList<ClientContactEntity> listClientContact) throws Exception {
      for (ClientContactEntity contact : listClientContact) {
         clientContactDAO.update(contact);
      }

   }

   @Override
   public void deleteContact(BigInteger idClientContactDelete) throws Exception {
      clientContactDAO.deleteByIdClientContact(idClientContactDelete);

   }

   @Override
   public void deleteFile(BigInteger idClientContactDelete) throws Exception {
      clientFileDAO.deleteByIdClientConctact(idClientContactDelete);

   }

   @Override
   public ClientFileEntity saveClientFile(ClientFileEntity clientFile) throws Exception {
      return clientFileDAO.update(clientFile);
   }

   @Override
   public String getNameFromMultivalue(TypeMultivalueEnum proveedorUltimaMilla, String idProviderLastMile)
      throws Exception {
      TypeMultivalueEntity typeMultivalue = typeMultivalueDAO.findByName(proveedorUltimaMilla.getValue());
      MultivalueEntity multivalue = multivalueDAO.findByTypeMultivalueAndValue(typeMultivalue,
         idProviderLastMile);
      return multivalue.getName();
   }

}
