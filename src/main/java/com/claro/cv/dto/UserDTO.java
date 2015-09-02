package com.claro.cv.dto;

import java.io.Serializable;


public class UserDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1407194962464273668L;

   private String userName;

   private String password;

   public String getUserName() {
      return userName;
   }

   public String getPassword() {
      return password;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public void setPassword(String password) {
      this.password = password;
   }

}
