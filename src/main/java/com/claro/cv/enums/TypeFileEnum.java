package com.claro.cv.enums;

public enum TypeFileEnum {

   SETTINGS("SETTINGS"),

   ENGINEERING("ENGINEERING"), 
   
   ENGINEERING_SERVICE("ENGINEERING_SERVICE");

   private String value;

   private TypeFileEnum(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

}
