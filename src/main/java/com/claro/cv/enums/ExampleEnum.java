package com.claro.cv.enums;

public enum ExampleEnum {

   ROUTE("route"), STORY("story");

   private String value;

   private ExampleEnum(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

}
