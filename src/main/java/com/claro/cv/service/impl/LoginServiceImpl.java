package com.claro.cv.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LDAP.LDAPAuthenticationServices;
import LDAP.LDAPAuthenticationServicesServiceLocator;

import com.claro.cv.dao.AdminDAO;
import com.claro.cv.dto.UserDTO;
import com.claro.cv.entity.AdminEntity;
import com.claro.cv.service.LoginService;


@Service
public class LoginServiceImpl implements LoginService, Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -6184533953749389887L;

   private static final String DOMAIN_NAME = "co.attla.corp";

   @Autowired
   private AdminDAO adminDAO;

   @Override
   public boolean authenticate(UserDTO user) throws Exception {
      LDAPAuthenticationServicesServiceLocator ldapL = new LDAPAuthenticationServicesServiceLocator();
      LDAPAuthenticationServices query;
      query = ldapL.getLDAPAuthenticationServices();
      if (query.userAuthentication(user.getUserName(), user.getPassword(), DOMAIN_NAME)) {
         return true;
      }
      return false;
   }

   @Override
   public boolean validateAdminByUser(UserDTO user) throws Exception {
      String userName = user.getUserName();
      AdminEntity admin = adminDAO.findByUser(userName);
      return admin != null;

   }

}
