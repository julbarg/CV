package com.claro.cv.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


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

   public static void addMessageInfoKeep(String infoMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, infoMsg, null);
      FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

   public static void addMessageError(String errorMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, null);
      FacesContext.getCurrentInstance().addMessage(null, message);
   }

}
