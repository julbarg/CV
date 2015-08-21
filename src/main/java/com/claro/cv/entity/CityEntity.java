package com.claro.cv.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the city database table.
 * 
 */
@Entity
@Table(name="city")
@NamedQueries({
   @NamedQuery(name="CityEntity.findAll", query="SELECT c FROM CityEntity c"),
   @NamedQuery(name="CityEntity.findByDepartament", query="SELECT c FROM CityEntity c WHERE c.departament=:departament"),
   @NamedQuery(name="CityEntity.findById", query="SELECT c FROM CityEntity c WHERE c.idCity=:idCity")
})
public class CityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_city")
	private String idCity;

	private String name;

	//bi-directional many-to-one association to DepartamentEntity
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_departament")
	private DepartamentEntity departament;

	//bi-directional many-to-one association to ClientServiceEntity
	@OneToMany(mappedBy="city")
	private List<ClientServiceEntity> clientServices;

	public CityEntity() {
	}

	public String getIdCity() {
		return this.idCity;
	}

	public void setIdCity(String idCity) {
		this.idCity = idCity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DepartamentEntity getDepartament() {
		return this.departament;
	}

	public void setDepartament(DepartamentEntity departament) {
		this.departament = departament;
	}

	public List<ClientServiceEntity> getClientServices() {
		return this.clientServices;
	}

	public void setClientServices(List<ClientServiceEntity> clientServices) {
		this.clientServices = clientServices;
	}

	public ClientServiceEntity addClientService(ClientServiceEntity clientService) {
		getClientServices().add(clientService);
		clientService.setCity(this);

		return clientService;
	}

	public ClientServiceEntity removeClientService(ClientServiceEntity clientService) {
		getClientServices().remove(clientService);
		clientService.setCity(null);

		return clientService;
	}

}