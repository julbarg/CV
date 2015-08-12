package com.claro.cv.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/** The persistent class for the type_multivalue database table. */
@Entity
@Table(name = "type_multivalue")
@NamedQueries({
   @NamedQuery(name = "TypeMultivalueEntity.findAll", query = "SELECT t FROM TypeMultivalueEntity t"),
   @NamedQuery(name = "TypeMultivalueEntity.findByName",query = "SELECT t FROM TypeMultivalueEntity t WHERE t.name=:name") })
public class TypeMultivalueEntity implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(
      strategy = GenerationType.AUTO)
   @Column(
      name = "id_type_multivalue")
   private int idTypeMultivalue;

   private String name;

   // bi-directional many-to-one association to MultivalueEntity
   @OneToMany(
      mappedBy = "typeMultivalue")
   private List<MultivalueEntity> multivalues;

   public TypeMultivalueEntity() {
   }

   public int getIdTypeMultivalue() {
      return this.idTypeMultivalue;
   }

   public void setIdTypeMultivalue(int idTypeMultivalue) {
      this.idTypeMultivalue = idTypeMultivalue;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<MultivalueEntity> getMultivalues() {
      return this.multivalues;
   }

   public void setMultivalues(List<MultivalueEntity> multivalues) {
      this.multivalues = multivalues;
   }

   public MultivalueEntity addMultivalue(MultivalueEntity multivalue) {
      getMultivalues().add(multivalue);
      multivalue.setTypeMultivalue(this);

      return multivalue;
   }

   public MultivalueEntity removeMultivalue(MultivalueEntity multivalue) {
      getMultivalues().remove(multivalue);
      multivalue.setTypeMultivalue(null);

      return multivalue;
   }

}