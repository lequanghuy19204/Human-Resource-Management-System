package com.hrms.service;

import jakarta.ejb.Local;
import com.hrms.model.Salary;
import org.bson.types.ObjectId;

import java.util.List;

@Local
public interface SalaryService {
    List<Salary> findAll();

    Salary findById(ObjectId objectId); // Thay đổi từ ObjectId thành String

    Salary create(Salary salary);

    boolean update(ObjectId objectId, Salary salary); // Thay đổi từ ObjectId thành String

    boolean delete(ObjectId objectId); // Thay đổi từ ObjectId thành String

    List<Salary> getByMonth(int month, int year);
}