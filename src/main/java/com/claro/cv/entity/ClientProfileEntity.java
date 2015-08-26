package com.claro.cv.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;
import java.util.List;


/** The persistent class for the client_profile database table. */
@Entity
@Table(
   name = "client_profile")
@NamedQueries({
   @NamedQuery(
      name = "ClientProfileEntity.findAll", query = "SELECT c FROM ClientProfileEntity c"),
   @NamedQuery(
      name = "ClientProfileEntity.findByIdClient",
      query = "SELECT c FROM ClientProfileEntity c WHERE c.idClient=:idClient") })
public class ClientProfileEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(
      strategy = GenerationType.AUTO)
   @Column(
      name = "id_client_profile")
   private BigInteger idClientProfile;

   @Column(
      name = "id_client")
   private BigInteger idClient;

   @Column(
      name = "name_client")
   private String nameClient;

   @Column(
      name = "nit_client")
   private String nitClient;

   private String state;

   // bi-directional many-to-one association to ClientContactEntity
   @OneToMany(
      mappedBy = "clientProfile", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
   private List<ClientContactEntity> clientContacts;

   // bi-directional many-to-one association to ClientServiceEntity
   @OneToMany(
      mappedBy = "clientProfile", cascade = CascadeType.PERSIST)
   private List<ClientServiceEntity> clientServices;

   // bi-directional many-to-one association to ClientFileEntity
   @OneToMany(
      mappedBy = "clientProfile", fetch = FetchType.EAGER)
   private List<ClientFileEntity> clientFiles;

   public ClientProfileEntity() {
   }

   public BigInteger getIdClientProfile() {
      return this.idClientProfile;
   }

   public void setIdClientProfile(BigInteger idClientProfile) {
      this.idClientProfile = idClientProfile;
   }

   public BigInteger getIdClient() {
      return this.idClient;
   }

   public void setIdClient(BigInteger idClient) {
      this.idClient = idClient;
   }

   public String getNameClient() {
      return this.nameClient;
   }

   public void setNameClient(String nameClient) {
      this.nameClient = nameClient;
   }

   public String getNitClient() {
      return this.nitClient;
   }

   public void setNitClient(String nitClient) {
      this.nitClient = nitClient;
   }

   public List<ClientContactEntity> getClientContacts() {
      return this.clientContacts;
   }

   public void setClientContacts(List<ClientContactEntity> clientContacts) {
      this.clientContacts = clientContacts;
   }

   public ClientContactEntity addClientContact(ClientContactEntity clientContact) {
      getClientContacts().add(clientContact);
      clientContact.setClientProfile(this);

      return clientContact;
   }

   public ClientContactEntity removeClientContact(ClientContactEntity clientContact) {
      getClientContacts().remove(clientContact);
      clientContact.setClientProfile(null);

      return clientContact;
   }

   public List<ClientServiceEntity> getClientServices() {
      return this.clientServices;
   }

   public void setClientServices(List<ClientServiceEntity> clientServices) {
      this.clientServices = clientServices;
   }

   public ClientServiceEntity addClientService(ClientServiceEntity clientService) {
      getClientServices().add(clientService);
      clientService.setClientProfile(this);

      return clientService;
   }

   public ClientServiceEntity removeClientService(ClientServiceEntity clientService) {
      getClientServices().remove(clientService);
      clientService.setClientProfile(null);

      return clientService;
   }

   public List<ClientFileEntity> getClientFiles() {
      return this.clientFiles;
   }

   public void setClientFiles(List<ClientFileEntity> clientFiles) {
      this.clientFiles = clientFiles;
   }

   public ClientFileEntity addClientFile(ClientFileEntity clientFile) {
      getClientFiles().add(clientFile);
      clientFile.setClientProfile(this);

      return clientFile;
   }

   public ClientFileEntity removeClientFile(ClientFileEntity clientFile) {
      getClientFiles().remove(clientFile);
      clientFile.setClientProfile(null);

      return clientFile;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

}