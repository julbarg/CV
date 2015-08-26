package com.claro.cv.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;


/** The persistent class for the client_contact database table. */
@Entity
@Table(name = "client_contact")
@NamedQueries({
   @NamedQuery(name = "ClientContactEntity.findAll", query = "SELECT c FROM ClientContactEntity c"),
   @NamedQuery(name = "ClientContactEntity.findByIdClientContact", query = "SELECT c FROM ClientContactEntity c WHERE c.idClientContact =:idClientContact")
})
public class ClientContactEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(
      strategy = GenerationType.AUTO)
   @Column(
      name = "id_client_contact")
   private BigInteger idClientContact;

   private String email;

   private String mobil;

   @Column(
      name = "name_contact")
   private String nameContact;

   private String phone;

   @Column(
      name = "type_contact")
   private String typeContact;

   // bi-directional many-to-one association to ClientProfileEntity
   @ManyToOne(
      fetch = FetchType.EAGER)
   @JoinColumn(
      name = "id_client_profile")
   private ClientProfileEntity clientProfile;

   public ClientContactEntity() {
   }

   public BigInteger getIdClientContact() {
      return this.idClientContact;
   }

   public void setIdClientContact(BigInteger idClientContact) {
      this.idClientContact = idClientContact;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getMobil() {
      return this.mobil;
   }

   public void setMobil(String mobil) {
      this.mobil = mobil;
   }

   public String getNameContact() {
      return this.nameContact;
   }

   public void setNameContact(String nameContact) {
      this.nameContact = nameContact;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getTypeContact() {
      return this.typeContact;
   }

   public void setTypeContact(String typeContact) {
      this.typeContact = typeContact;
   }

   public ClientProfileEntity getClientProfile() {
      return this.clientProfile;
   }

   public void setClientProfile(ClientProfileEntity clientProfile) {
      this.clientProfile = clientProfile;
   }

}