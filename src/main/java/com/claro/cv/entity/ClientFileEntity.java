package com.claro.cv.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the client_file database table.
 * 
 */
@Entity
@Table(name="client_file")
@NamedQuery(name="ClientFileEntity.findAll", query="SELECT c FROM ClientFileEntity c")
public class ClientFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_client_file")
	private String idClientFile;

	@Column(name="name_file")
	private String nameFile;

	private String url;

	//bi-directional many-to-one association to ClientProfileEntity
	@ManyToOne
	@JoinColumn(name="id_client_profile")
	private ClientProfileEntity clientProfile;

	public ClientFileEntity() {
	}

	public String getIdClientFile() {
		return this.idClientFile;
	}

	public void setIdClientFile(String idClientFile) {
		this.idClientFile = idClientFile;
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

	public ClientProfileEntity getClientProfile() {
		return this.clientProfile;
	}

	public void setClientProfile(ClientProfileEntity clientProfile) {
		this.clientProfile = clientProfile;
	}

}