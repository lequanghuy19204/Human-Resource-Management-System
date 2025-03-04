package com.hrms.service;

import jakarta.ejb.Local;
import com.hrms.model.Salary;
import org.bson.types.ObjectId;
import java.util.List;

@Local
public interface SalaryService {
    List<Salary> findAll();

    Salary findById(ObjectId objectId);

    Salary create(Salary salary);

    boolean update(ObjectId objectId, Salary salary);

    boolean delete(ObjectId objectId);
}