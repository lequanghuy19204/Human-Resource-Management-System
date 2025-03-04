package com.hrms.impl;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.hrms.service.SalaryService;
import com.hrms.repository.SalaryRepository;
import com.hrms.model.Salary;
import org.bson.types.ObjectId;

import java.util.List;

@Stateless
public class SalaryServiceImpl implements SalaryService {
    @Inject
    private SalaryRepository repository;

    public SalaryServiceImpl() { }

    @Override
    public List<Salary> findAll() {
        return repository.findAll();
    }

    @Override
    public Salary findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Salary create(Salary salary) {
        return repository.create(salary);
    }

    @Override
    public boolean update(ObjectId id, Salary salary) {
        return repository.update(id, salary);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}