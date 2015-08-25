package com.claro.cv.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.util.Util;


@Controller("util")
@Scope("session")
public class UtilController implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   @PostConstruct
   public void initialize() {
   }

   public boolean validateAuthentication() {
      if (Util.validateLogIn()) {
         return true;
      }
      return false;
   }

   public void logOut() {
      Util.logOut();
   }

}
