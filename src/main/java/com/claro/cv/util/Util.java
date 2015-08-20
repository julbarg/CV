package com.claro.cv.util;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.claro.cv.entity.MultivalueEntity;


public class Util {

   public static String getRedirect(String page) {
      String redirect = page + Constant.XHTML + Constant.REDIRECT;
      return redirect;
   }

   public static String goPage(String page) {
      String redirect = page + Constant.XHTML;
      return redirect;
   }

   public static void addMessageFatal(String fatalMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, fatalMsg, null);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

   public static void addMessageWarn(String warnMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, warnMsg, null);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

   public static void addMessageInfo(String infoMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, infoMsg, null);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

   public static String getMeansFlag(String value) {
      String name;
      if (value != null) {
         name = value.equals("S") ? "Si" : "No";
         return name;
      }
      return value;
   }

   public static void addMessageInfoKeep(String infoMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, infoMsg, null);
      FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

   public static void addMessageError(String errorMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, null);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

   public static String getMeansMultiValue(ArrayList<MultivalueEntity> listMultivalue, String value) {
      for (MultivalueEntity multiValue : listMultivalue) {
         if (multiValue.getCode().equals(value)) {
            return multiValue.getName();
         }
      }
      return value;

   }

}
