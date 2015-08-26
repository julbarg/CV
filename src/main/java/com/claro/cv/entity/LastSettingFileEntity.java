package com.claro.cv.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the last_setting_file database table.
 * 
 */
@Entity
@Table(name="last_setting_file")
@NamedQuery(name="LastSettingFileEntity.findAll", query="SELECT l FROM LastSettingFileEntity l")
public class LastSettingFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_last_setting_file")
	private String idLastSettingFile;

	@Column(name="name_file")
	private String nameFile;

	private String url;

	//bi-directional many-to-one association to ClientServiceEntity
	@OneToMany(mappedBy = "lastSettingFile", cascade = CascadeType.PERSIST)
	private List<ClientServiceEntity> clientServices;

	public LastSettingFileEntity() {
	}

	public String getIdLastSettingFile() {
		return this.idLastSettingFile;
	}

	public void setIdLastSettingFile(String idLastSettingFile) {
		this.idLastSettingFile = idLastSettingFile;
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

	public List<ClientServiceEntity> getClientServices() {
		return this.clientServices;
	}

	public void setClientServices(List<ClientServiceEntity> clientServices) {
		this.clientServices = clientServices;
	}

	public ClientServiceEntity addClientService(ClientServiceEntity clientService) {
		getClientServices().add(clientService);
		clientService.setLastSettingFile(this);

		return clientService;
	}

	public ClientServiceEntity removeClientService(ClientServiceEntity clientService) {
		getClientServices().remove(clientService);
		clientService.setLastSettingFile(null);

		return clientService;
	}

}