package com.hrms.impl;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.hrms.service.RewardService;
import com.hrms.repository.RewardRepository;
import com.hrms.model.Reward;
import org.bson.types.ObjectId;

import java.util.List;

@Stateless
public class RewardServiceImpl implements RewardService {
    @Inject
    private RewardRepository repository;

    public RewardServiceImpl() { }

    @Override
    public List<Reward> findAll() {
        return repository.findAll();
    }

    @Override
    public Reward findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Reward create(Reward reward) {
        return repository.create(reward);
    }

    @Override
    public boolean update(ObjectId id, Reward reward) {
        return repository.update(id, reward);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}