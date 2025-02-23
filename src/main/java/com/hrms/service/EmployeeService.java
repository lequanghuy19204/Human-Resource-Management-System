package com.hrms.service;

import com.hrms.model.Employee;
import jakarta.ejb.Local;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(ObjectId id);

    Employee create(Employee employee);

    boolean update(ObjectId id, Employee employee);

    boolean delete(ObjectId id);
}