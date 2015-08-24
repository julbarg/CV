package com.claro.cv.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the multivalue database table.
 * 
 */
@Entity
@Table(name="multivalue")
@NamedQueries({
   @NamedQuery(name = "MultivalueEntity.findAll", query = "SELECT m FROM MultivalueEntity m"),
   @NamedQuery(name = "MultivalueEntity.findByTypeMultivalue", query = "SELECT m FROM MultivalueEntity m WHERE m.typeMultivalue=:typeMultivalue ORDER BY m.code")
})
public class MultivalueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_multivalue")
	private int idMultivalue;

	private String code;

	private String name;

	//bi-directional many-to-one association to TypeMultivalueEntity
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_type_multivalue")
	private TypeMultivalueEntity typeMultivalue;

	public MultivalueEntity() {
	}

	public int getIdMultivalue() {
		return this.idMultivalue;
	}

	public void setIdMultivalue(int idMultivalue) {
		this.idMultivalue = idMultivalue;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeMultivalueEntity getTypeMultivalue() {
		return this.typeMultivalue;
	}

	public void setTypeMultivalue(TypeMultivalueEntity typeMultivalue) {
		this.typeMultivalue = typeMultivalue;
	}

}