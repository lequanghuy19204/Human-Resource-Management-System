package com.hrms.impl;

import com.hrms.model.TrainingProgram;
import com.hrms.repository.TrainingProgramRepository;
import com.hrms.service.TrainingProgramService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.List;

@Stateless
public class TrainingProgramServiceImpl implements TrainingProgramService {

    @Inject
    private TrainingProgramRepository repository;

    @Override
    public List<TrainingProgram> findAll() {
        return repository.findAll();
    }

    @Override
    public List<TrainingProgram> findByCompanyId(ObjectId companyId) {
        return repository.findByCompanyId(companyId);
    }

    @Override
    public TrainingProgram findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public TrainingProgram create(TrainingProgram program) {
        return repository.create(program);
    }

    @Override
    public boolean update(ObjectId id, TrainingProgram program) {
        return repository.update(id, program);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}