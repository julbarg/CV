package com.claro.cv.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the admin database table.
 * 
 */
@Entity
@Table(name="admin")
@NamedQueries({
   @NamedQuery(name="AdminEntity.findAll", query="SELECT a FROM AdminEntity a"),
   @NamedQuery(name="AdminEntity.findByUser", query="SELECT a FROM AdminEntity a WHERE a.user=:user")
})
public class AdminEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String user;

	public AdminEntity() {
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}