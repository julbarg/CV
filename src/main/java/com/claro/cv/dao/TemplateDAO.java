package com.claro.cv.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;


/** TemplateDAO - Define los metodos genericos en entidades
 * de la base de datos CV
 * 
 * @author jbarragan
 *
 * @param <T> */
public class TemplateDAO<T> implements Serializable{

   /**
    * 
    */
   private static final long serialVersionUID = 5881273883150653204L;

   public static final String MANAGER = "CVTM";

   private Class<T> clase;

   @PersistenceContext(
      unitName = "CVPU")
   public EntityManager entityManager;

   public final void setClase(Class<T> entity) {
      this.clase = entity;
   }

   public T findOne(long id) {
      T t = entityManager.find(this.clase, id);

      return t;
   }

   @Transactional(MANAGER)
   public void create(T entity) throws Exception {
      entityManager.persist(entity);
   }

   @Transactional(MANAGER)
   public T update(T entity) throws Exception {
      T t = entityManager.merge(entity);

      return t;
   }

   @Transactional(MANAGER)
   public void delete(T entity) {
      entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));

   }

   @Transactional(MANAGER)
   public void deleteById(long entityId) {
      T entity = this.findOne(entityId);
      this.delete(entity);
   }

}
