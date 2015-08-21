package com.claro.cv.enums;

public enum TypeLocationEnum {

   NATIONAL("Nacional"),

   INTERNATIONAL("Internacional");

   private String value;

   private TypeLocationEnum(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

}
