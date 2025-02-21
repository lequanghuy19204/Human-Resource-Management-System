package com.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.entity.Salary;
import java.util.List;

public class SalaryDao {

    @PersistenceContext
    private EntityManager em;

    public void add(Salary salary) {
        em.persist(salary);
    }

    public void update(Salary salary) {
        em.merge(salary);
    }

    public void remove(Salary salary) {
        em.remove(salary);
    }

    public void removeById(String id) {
        Salary salary = em.find(Salary.class, id);
        if (salary != null) {
            em.remove(salary);
        }
    }

    public boolean has(String id) {
        return em.find(Salary.class, id) != null;
    }

    public List<Salary> getAll() {
        return em.createQuery("SELECT s FROM Salary s", Salary.class).getResultList();
    }

    public List<Salary> getWhere(String field, Object value) {
        return em.createQuery("SELECT s FROM Salary s WHERE s." + field + " = :value", Salary.class)
                .setParameter("value", value)
                .getResultList();
    }
}
