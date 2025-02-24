package com.hrms.impl;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.hrms.service.PerformanceService;
import com.hrms.repository.PerformanceRepository;
import com.hrms.model.Performance;
import org.bson.types.ObjectId;

import java.util.List;

@Stateless
public class PerformanceServiceImpl implements PerformanceService {
    @Inject
    private PerformanceRepository repository;

    public PerformanceServiceImpl() { }

    @Override
    public List<Performance> findAll() {
        return repository.findAll();
    }

    @Override
    public Performance findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Performance create(Performance performance) {
        return repository.create(performance);
    }

    @Override
    public boolean update(ObjectId id, Performance performance) {
        return repository.update(id, performance);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}