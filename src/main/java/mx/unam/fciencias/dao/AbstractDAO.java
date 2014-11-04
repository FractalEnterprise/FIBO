/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.fciencias.dao;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.persistence.EntityManager;
import javax.persistence.Persistence;


public abstract class AbstractDAO<T> {

    protected EntityManager em;
    protected Class<T> clase;

    public AbstractDAO(Class<T> c) {
        this.em = Persistence.createEntityManagerFactory("cienciasPU").createEntityManager();
        clase = c;
    }

    public void create(T t) {
        em.persist(t);
    }

    public T find(Long id) {
        return em.find(clase, id);
    }

    public T merge(T t) {
        return em.merge(t);
    }

    public void delete(String id) {
        T t = em.find(clase, id);
        if (t != null) {
            em.remove(t);
        }
    }

    public EntityManager getEm() {
        return em;
    }
}
