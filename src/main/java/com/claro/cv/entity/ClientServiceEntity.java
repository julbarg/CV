package com.claro.cv.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/** The persistent class for the client_service database table. */
@Entity
@Table(
   name = "client_service")
@NamedQueries({
   @NamedQuery(name = "ClientServiceEntity.findAll", query = "SELECT c FROM ClientServiceEntity c"),
   @NamedQuery(name = "ClientServiceEntity.findByDepartament",query = "SELECT c FROM ClientServiceEntity c WHERE c.departament=:departament"),
})
public class ClientServiceEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(
      strategy = GenerationType.AUTO)
   @Column(
      name = "id_client_service")
   private String idClientService;

   private String alias;

   private String backup;

   @Column(
      name = "code_service")
   private String codeService;

   @Column(
      name = "code_service_last_mile")
   private String codeServiceLastMile;

   private String direction;

   @Column(
      name = "id_provider_last_mile")
   private String idProviderLastMile;

   private String lat;

   private String lng;

   @Column(
      name = "main_point")
   private String mainPoint;

   @Column(
      name = "tye_service")
   private String tyeService;

   // bi-directional many-to-one association to CityEntity
   @ManyToOne(
      fetch = FetchType.EAGER)
   @JoinColumn(
      name = "id_city")
   private CityEntity city;

   // bi-directional many-to-one association to ClientProfileEntity
   @ManyToOne(
      fetch = FetchType.EAGER)
   @JoinColumn(
      name = "id_client_profile")
   private ClientProfileEntity clientProfile;

   // bi-directional many-to-one association to DepartamentEntity
   @ManyToOne(
      fetch = FetchType.EAGER)
   @JoinColumn(
      name = "id_department")
   private DepartamentEntity departament;

   // bi-directional many-to-one association to LastSettingFileEntity
   @OneToOne(
      fetch = FetchType.LAZY)
   @JoinColumn(
      name = "id_last_settings_file")
   private LastSettingFileEntity lastSettingFile;

   // bi-directional many-to-one association to ServiceContactEntity
   @OneToMany(
      mappedBy = "clientService", cascade = CascadeType.ALL)
   private List<ServiceContactEntity> serviceContacts;

   public ClientServiceEntity() {
   }

   public String getIdClientService() {
      return this.idClientService;
   }

   public void setIdClientService(String idClientService) {
      this.idClientService = idClientService;
   }

   public String getAlias() {
      return this.alias;
   }

   public void setAlias(String alias) {
      this.alias = alias;
   }

   public String getBackup() {
      return this.backup;
   }

   public void setBackup(String backup) {
      this.backup = backup;
   }

   public String getCodeService() {
      return this.codeService;
   }

   public void setCodeService(String codeService) {
      this.codeService = codeService;
   }

   public String getCodeServiceLastMile() {
      return this.codeServiceLastMile;
   }

   public void setCodeServiceLastMile(String codeServiceLastMile) {
      this.codeServiceLastMile = codeServiceLastMile;
   }

   public String getDirection() {
      return this.direction;
   }

   public void setDirection(String direction) {
      this.direction = direction;
   }

   public String getIdProviderLastMile() {
      return this.idProviderLastMile;
   }

   public void setIdProviderLastMile(String idProviderLastMile) {
      this.idProviderLastMile = idProviderLastMile;
   }

   public String getLat() {
      return this.lat;
   }

   public void setLat(String lat) {
      this.lat = lat;
   }

   public String getLng() {
      return this.lng;
   }

   public void setLng(String lng) {
      this.lng = lng;
   }

   public String getMainPoint() {
      return this.mainPoint;
   }

   public void setMainPoint(String mainPoint) {
      this.mainPoint = mainPoint;
   }

   public String getTyeService() {
      return this.tyeService;
   }

   public void setTyeService(String tyeService) {
      this.tyeService = tyeService;
   }

   public CityEntity getCity() {
      return this.city;
   }

   public void setCity(CityEntity city) {
      this.city = city;
   }

   public ClientProfileEntity getClientProfile() {
      return this.clientProfile;
   }

   public void setClientProfile(ClientProfileEntity clientProfile) {
      this.clientProfile = clientProfile;
   }

   public DepartamentEntity getDepartament() {
      return this.departament;
   }

   public void setDepartament(DepartamentEntity departament) {
      this.departament = departament;
   }

   public LastSettingFileEntity getLastSettingFile() {
      return this.lastSettingFile;
   }

   public void setLastSettingFile(LastSettingFileEntity lastSettingFile) {
      this.lastSettingFile = lastSettingFile;
   }

   public List<ServiceContactEntity> getServiceContacts() {
      return this.serviceContacts;
   }

   public void setServiceContacts(List<ServiceContactEntity> serviceContacts) {
      this.serviceContacts = serviceContacts;
   }

   public ServiceContactEntity addServiceContact(ServiceContactEntity serviceContact) {
      getServiceContacts().add(serviceContact);
      serviceContact.setClientService(this);

      return serviceContact;
   }

   public ServiceContactEntity removeServiceContact(ServiceContactEntity serviceContact) {
      getServiceContacts().remove(serviceContact);
      serviceContact.setClientService(null);

      return serviceContact;
   }

}