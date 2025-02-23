package com.hrms.impl;

import com.hrms.model.Employee;
import com.hrms.repository.EmployeeRepository;
import com.hrms.service.EmployeeService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.List;

@Stateless
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepository repository;

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee findById(ObjectId id) {
        return repository.findById(id);
    }

    @Override
    public Employee create(Employee employee) {
        return repository.create(employee);
    }

    @Override
    public boolean update(ObjectId id, Employee employee) {
        return repository.update(id, employee);
    }

    @Override
    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }
}