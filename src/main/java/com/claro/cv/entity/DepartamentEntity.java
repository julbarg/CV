package com.claro.cv.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the departament database table.
 * 
 */
@Entity
@Table(name="departament")
@NamedQueries({
   @NamedQuery(name="DepartamentEntity.findAll", query="SELECT d FROM DepartamentEntity d"),
   @NamedQuery(name="DepartamentEntity.findById", query="SELECT d FROM DepartamentEntity d WHERE d.idDepartament=:idDepartament"),
   @NamedQuery(name="DepartamentEntity.findByGeoCode", query="SELECT d FROM DepartamentEntity d WHERE d.geocode=:geocode")
})
public class DepartamentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_departament")
	private String idDepartament;

	private String geocode;

	@Column(name="lat_center")
	private String latCenter;

	@Column(name="lng_center")
	private String lngCenter;

	private String name;

	private int zoom;

	//bi-directional many-to-one association to CityEntity
	@OneToMany(mappedBy="departament")
	private List<CityEntity> cities;

	//bi-directional many-to-one association to ClientServiceEntity
	@OneToMany(mappedBy="departament")
	private List<ClientServiceEntity> clientServices;

	public DepartamentEntity() {
	}

	public String getIdDepartament() {
		return this.idDepartament;
	}

	public void setIdDepartament(String idDepartament) {
		this.idDepartament = idDepartament;
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

	public List<CityEntity> getCities() {
		return this.cities;
	}

	public void setCities(List<CityEntity> cities) {
		this.cities = cities;
	}

	public CityEntity addCity(CityEntity city) {
		getCities().add(city);
		city.setDepartament(this);

		return city;
	}

	public CityEntity removeCity(CityEntity city) {
		getCities().remove(city);
		city.setDepartament(null);

		return city;
	}

	public List<ClientServiceEntity> getClientServices() {
		return this.clientServices;
	}

	public void setClientServices(List<ClientServiceEntity> clientServices) {
		this.clientServices = clientServices;
	}

	public ClientServiceEntity addClientService(ClientServiceEntity clientService) {
		getClientServices().add(clientService);
		clientService.setDepartament(this);

		return clientService;
	}

	public ClientServiceEntity removeClientService(ClientServiceEntity clientService) {
		getClientServices().remove(clientService);
		clientService.setDepartament(null);

		return clientService;
	}

}