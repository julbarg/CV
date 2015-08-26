package com.claro.cv.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.util.Messages;
import com.claro.cv.util.Util;


@Controller("util")
@Scope("session")
public class UtilController implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private static Logger LOGGER = LogManager.getLogger(UtilController.class.getName());

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

   public StreamedContent downloadFile(String url, String nameFile) {
      try {
         String extension = FilenameUtils.getExtension(url);
         InputStream stream = new FileInputStream(url);
         return new DefaultStreamedContent(stream, extension, nameFile + "." + extension);
      } catch (FileNotFoundException e) {
         LOGGER.error(Messages.DOWNLOAD_FILE_ERROR, e);
      }
      return null;
   }

}
