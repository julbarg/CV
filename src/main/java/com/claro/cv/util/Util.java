package com.claro.cv.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.claro.cv.dto.UserDTO;
import com.claro.cv.entity.MultivalueEntity;


public class Util {

   public static HttpSession getSession() {
      return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
   }

   public static void logIn(UserDTO userName) {
      Util.getSession().setAttribute(Constant.USER_NAME, userName.getUserName());
   }

   public static String getUserName() {
      try {
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
            .getSession(false);
         return session.getAttribute(Constant.USER_NAME).toString();
      } catch (Exception e) {
         return null;
      }
   }

   public static boolean validateLogIn() {
      try {
         if (getUserName() == null) {
            Util.addMessageErrorKeepLogOut(Messages.NOT_SESSION);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/CV/");
            return false;
         }
      } catch (IOException e) {
      }
      return true;
   }

   public static void logOut() {
      try {
         Util.getSession().setAttribute(Constant.USER_NAME, null);
         FacesContext.getCurrentInstance().getExternalContext().redirect("/CV/");
      } catch (IOException e) {
      }
   }

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

   public static void addMessageErrorKeepLogOut(String errorMsg) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMsg, null);
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

   public static void redirectFaces(String url) {
      try {
         FacesContext.getCurrentInstance().getExternalContext().redirect(url);
      } catch (IOException e) {
      }

   }

}
