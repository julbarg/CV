package com.claro.cv.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/** The persistent class for the country database table. */
@Entity
@Table(
   name = "country")
@NamedQueries({
   @NamedQuery(name = "CountryEntity.findAll", query = "SELECT c FROM CountryEntity c"),
   @NamedQuery(name = "CountryEntity.findCountryById",query = "SELECT c FROM CountryEntity c WHERE c.idCountry=:idCountry"),
   @NamedQuery(name = "CountryEntity.findByGeoCode",query = "SELECT c FROM CountryEntity c WHERE c.geocode=:geocode")
})
public class CountryEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(
      strategy = GenerationType.AUTO)
   @Column(
      name = "id_country")
   private String idCountry;

   private String geocode;

   @Column(
      name = "lat_center")
   private String latCenter;

   @Column(
      name = "lng_center")
   private String lngCenter;

   private String name;

   private int zoom;

   // bi-directional many-to-one association to ClientServiceEntity
   @OneToMany(
      mappedBy = "country")
   private List<ClientServiceEntity> clientServices;

   public CountryEntity() {
   }

   public String getIdCountry() {
      return this.idCountry;
   }

   public void setIdCountry(String idCountry) {
      this.idCountry = idCountry;
   }

   public String getGeocode() {
      return this.geocode;
   }

   public void setGeocode(String geocode) {
      this.geocode = geocode;
   }

   public String getLatCenter() {
      return this.latCenter;
   }

   public void setLatCenter(String latCenter) {
      this.latCenter = latCenter;
   }

   public String getLngCenter() {
      return this.lngCenter;
   }

   public void setLngCenter(String lngCenter) {
      this.lngCenter = lngCenter;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getZoom() {
      return this.zoom;
   }

   public void setZoom(int zoom) {
      this.zoom = zoom;
   }

   public List<ClientServiceEntity> getClientServices() {
      return this.clientServices;
   }

   public void setClientServices(List<ClientServiceEntity> clientServices) {
      this.clientServices = clientServices;
   }

   public ClientServiceEntity addClientService(ClientServiceEntity clientService) {
      getClientServices().add(clientService);
      clientService.setCountry(this);

      return clientService;
   }

   public ClientServiceEntity removeClientService(ClientServiceEntity clientService) {
      getClientServices().remove(clientService);
      clientService.setCountry(null);

      return clientService;
   }

}