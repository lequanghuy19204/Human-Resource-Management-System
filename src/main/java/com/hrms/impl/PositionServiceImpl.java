package com.hrms.impl;

import com.hrms.model.Position;
import com.hrms.repository.PositionRepository;
import com.hrms.service.PositionService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.List;

@Stateless
public class PositionServiceImpl implements PositionService {

    @Inject
    private PositionRepository repository;

    @Override
    public List<Position> findAll() {
        return repository.findAll();
    }

    @Override
    public Position findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Position create(Position position) {
        return repository.create(position);
    }

    @Override
    public boolean update(ObjectId id, Position position) {
        return repository.update(id, position);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}