package com.claro.cv.dto;

import java.io.Serializable;

public class MapDataDTO implements Serializable{
   
   /**
    * 
    */
   private static final long serialVersionUID = -4448643985633421321L;

   private String codeDepartament;
   
   private String nameDepartament;
   
   private int numberServices;

   public String getCodeDepartament() {
      return codeDepartament;
   }

   public String getNameDepartament() {
      return nameDepartament;
   }

   public int getNumberServices() {
      return numberServices;
   }

   public void setCodeDepartament(String codeDepartament) {
      this.codeDepartament = codeDepartament;
   }

   public void setNameDepartament(String nameDepartament) {
      this.nameDepartament = nameDepartament;
   }

   public void setNumberServices(int numberServices) {
      this.numberServices = numberServices;
   }

}
