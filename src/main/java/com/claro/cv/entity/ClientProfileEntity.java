package com.claro.cv.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;


/** The persistent class for the client_profile database table. */
@Entity
@Table(
   name = "client_profile")
@NamedQuery(
   name = "ClientProfileEntity.findAll", query = "SELECT c FROM ClientProfileEntity c")
public class ClientProfileEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(
      strategy = GenerationType.AUTO)
   @Column(
      name = "id_client_profile")
   private String idClientProfile;

   @Column(
      name = "id_client")
   private BigInteger idClient;

   @Column(
      name = "name_client")
   private String nameClient;

   @Column(
      name = "nit_client")
   private String nitClient;

   // bi-directional many-to-one association to ClientContactEntity
   @OneToMany(
      mappedBy = "clientProfile", cascade = CascadeType.ALL)
   private List<ClientContactEntity> clientContacts;

   // bi-directional many-to-one association to ClientServiceEntity
   @OneToMany(
      mappedBy = "clientProfile", cascade = CascadeType.ALL)
   private List<ClientServiceEntity> clientServices;

   public ClientProfileEntity() {
   }

   public String getIdClientProfile() {
      return this.idClientProfile;
   }

   public void setIdClientProfile(String idClientProfile) {
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

}