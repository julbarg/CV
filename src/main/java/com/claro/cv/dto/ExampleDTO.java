package com.claro.cv.dto;

import java.io.Serializable;


public class ExampleDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private String valueOne;

   private String valueTwo;

   public String getValueOne() {
      return valueOne;
   }

   public void setValueOne(String valueOne) {
      this.valueOne = valueOne;
   }

   public String getValueTwo() {
      return valueTwo;
   }

   public void setValueTwo(String valueTwo) {
      this.valueTwo = valueTwo;
   }
}
