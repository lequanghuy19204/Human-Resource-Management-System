package com.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.entity.Performance;
import java.util.List;

public class PerformanceDao {

    @PersistenceContext
    private EntityManager em;

    public void add(Performance performance) {
        em.persist(performance);
    }

    public void update(Performance performance) {
        em.merge(performance);
    }

    public void remove(Performance performance) {
        em.remove(performance);
    }

    public void removeById(String id) {
        Performance performance = em.find(Performance.class, id);
        if (performance != null) {
            em.remove(performance);
        }
    }

    public boolean has(String id) {
        return em.find(Performance.class, id) != null;
    }

    public List<Performance> getAll() {
        return em.createQuery("SELECT p FROM Performance p", Performance.class).getResultList();
    }

    public List<Performance> getWhere(String field, Object value) {
        return em.createQuery("SELECT p FROM Performance p WHERE p." + field + " = :value", Performance.class)
                .setParameter("value", value)
                .getResultList();
    }
}
