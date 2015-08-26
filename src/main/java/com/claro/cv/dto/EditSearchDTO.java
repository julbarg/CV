package com.claro.cv.dto;

import java.io.Serializable;
import java.math.BigInteger;


public class EditSearchDTO implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 5969884020822990956L;

   private String nameClient;

   private BigInteger idClient;

   private String nitClient;

   private String codeService;

   private String state;

   public String getNameClient() {
      return nameClient != null ? nameClient : "";
   }

   public BigInteger getIdClient() {
      return idClient;
   }

   public String getNitClient() {
      return nitClient != null ? nitClient : "";
   }

   public String getCodeService() {
      return codeService != null ? codeService : "";
   }

   public void setNameClient(String nameClient) {
      this.nameClient = nameClient;
   }

   public void setIdClient(BigInteger idClient) {
      this.idClient = idClient;
   }

   public void setNitClient(String nitClient) {
      this.nitClient = nitClient;
   }

   public void setCodeService(String codeService) {
      this.codeService = codeService;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

}
