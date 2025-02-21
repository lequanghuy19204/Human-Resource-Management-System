package com.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.entity.Task;
import java.util.List;

public class TaskDao {

    @PersistenceContext
    private EntityManager em;

    public void add(Task task) {
        em.persist(task);
    }

    public void update(Task task) {
        em.merge(task);
    }

    public void remove(Task task) {
        em.remove(task);
    }

    public void removeById(String id) {
        Task task = em.find(Task.class, id);
        if (task != null) {
            em.remove(task);
        }
    }

    public boolean has(String id) {
        return em.find(Task.class, id) != null;
    }

    public List<Task> getAll() {
        return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }

    public List<Task> getWhere(String field, Object value) {
        return em.createQuery("SELECT t FROM Task t WHERE t." + field + " = :value", Task.class)
                .setParameter("value", value)
                .getResultList();
    }
}
