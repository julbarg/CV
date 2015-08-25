package com.claro.cv.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.dto.UserDTO;
import com.claro.cv.service.LoginService;
import com.claro.cv.util.Constant;
import com.claro.cv.util.Messages;
import com.claro.cv.util.Util;


@Controller("login")
@Scope("session")
public class LoginController implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 7546439283549336959L;

   private static Logger LOGGER = LogManager.getLogger(LoginController.class.getName());

   private UserDTO user;

   @Autowired
   private LoginService loginService;

   @PostConstruct
   public void initialize() {
      user = new UserDTO();

   }

   public String authenticate() {
      try {
         if (loginService.authenticate(user)) {
            Util.logIn(user);
            return Util.getRedirect(Constant.SEARCH_PAGE);
         }else{
            Util.addMessageFatal(Messages.AUTHENTICATION_NO_VALIDATE);
         }
      } catch (Exception e) {
         Util.addMessageFatal(Messages.AUTHENTICATION_ERROR);
         LOGGER.error(Messages.AUTHENTICATION_ERROR, e);
      }
      return null;

   }

   public UserDTO getUser() {
      return user;
   }

   public void setUser(UserDTO user) {
      this.user = user;
   }

}
