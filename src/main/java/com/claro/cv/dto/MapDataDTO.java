package com.claro.cv.dto;

import java.io.Serializable;


public class MapDataDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = -4448643985633421321L;

   private String code;

   private String name;

   private int numberServices;

   public String getCode() {
      return code;
   }

   public String getName() {
      return name;
   }

   public int getNumberServices() {
      return numberServices;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setNumberServices(int numberServices) {
      this.numberServices = numberServices;
   }

}
