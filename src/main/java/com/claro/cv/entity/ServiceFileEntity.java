package com.claro.cv.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;


/** The persistent class for the service_file database table. */
@Entity
@Table(
   name = "service_file")
@NamedQueries({
   @NamedQuery(
      name = "ServiceFileEntity.findAll", query = "SELECT s FROM ServiceFileEntity s"),
   @NamedQuery(
      name = "ServiceFileEntity.findByClientService",
      query = "SELECT s FROM ServiceFileEntity s WHERE s.clientService=:clientService") })
public class ServiceFileEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(
      strategy = GenerationType.AUTO)
   @Column(
      name = "id_service_file")
   private BigInteger idServiceFile;

   @Column(
      name = "name_file")
   private String nameFile;

   private String url;

   // bi-directional many-to-one association to ClientServiceEntity
   @ManyToOne
   @JoinColumn(
      name = "id_client_service")
   private ClientServiceEntity clientService;

   public ServiceFileEntity() {
   }

   public BigInteger getIdServiceFile() {
      return this.idServiceFile;
   }

   public void setIdServiceFile(BigInteger idServiceFile) {
      this.idServiceFile = idServiceFile;
   }

   public String getNameFile() {
      return this.nameFile;
   }

   public void setNameFile(String nameFile) {
      this.nameFile = nameFile;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public ClientServiceEntity getClientService() {
      return this.clientService;
   }

   public void setClientService(ClientServiceEntity clientService) {
      this.clientService = clientService;
   }

}