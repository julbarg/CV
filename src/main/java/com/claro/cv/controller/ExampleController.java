package com.claro.cv.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.claro.cv.dto.ExampleDTO;


@Controller("example")
@Scope("session")
public class ExampleController implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private ExampleDTO exampleDTO;

   @PostConstruct
   private void initialize() throws IOException {
      exampleDTO = new ExampleDTO();
   }

   public void save() {

   }

   public ExampleDTO getExampleDTO() {
      return exampleDTO;
   }

   public void setExampleDTO(ExampleDTO exampleDTO) {
      this.exampleDTO = exampleDTO;
   }

}
