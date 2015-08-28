package com.claro.cv.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;


/**
 * The persistent class for the service_contact database table.
 * 
 */
@Entity
@Table(name="service_contact")
@NamedQueries({
   @NamedQuery(name="ServiceContactEntity.findAll", query="SELECT s FROM ServiceContactEntity s"),
   @NamedQuery(name="ServiceContactEntity.findByClientService", query="SELECT s FROM ServiceContactEntity s WHERE s.clientService =:clientService")
})
public class ServiceContactEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_service_contact")
	private BigInteger idServiceContact;

	private String email;

	private String mobil;

	@Column(name="name_contact")
	private String nameContact;

	private String phone;

	private String schedule;

	//bi-directional many-to-one association to ClientServiceEntity
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_client_service")
	private ClientServiceEntity clientService;

	public ServiceContactEntity() {
	}

	public BigInteger getIdServiceContact() {
		return this.idServiceContact;
	}

	public void setIdServiceContact(BigInteger idServiceContact) {
		this.idServiceContact = idServiceContact;
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

	public String getSchedule() {
		return this.schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public ClientServiceEntity getClientService() {
		return this.clientService;
	}

	public void setClientService(ClientServiceEntity clientService) {
		this.clientService = clientService;
	}

}