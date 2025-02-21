package com.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.entity.Reward;
import java.util.List;

public class RewardDao {

    @PersistenceContext
    private EntityManager em;

    public void add(Reward reward) {
        em.persist(reward);
    }

    public void update(Reward reward) {
        em.merge(reward);
    }

    public void remove(Reward reward) {
        em.remove(reward);
    }

    public void removeById(String id) {
        Reward reward = em.find(Reward.class, id);
        if (reward != null) {
            em.remove(reward);
        }
    }

    public boolean has(String id) {
        return em.find(Reward.class, id) != null;
    }

    public List<Reward> getAll() {
        return em.createQuery("SELECT r FROM Reward r", Reward.class).getResultList();
    }

    public List<Reward> getWhere(String field, Object value) {
        return em.createQuery("SELECT r FROM Reward r WHERE r." + field + " = :value", Reward.class)
                .setParameter("value", value)
                .getResultList();
    }
}
