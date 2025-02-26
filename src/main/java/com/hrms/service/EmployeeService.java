package com.hrms.service;

import com.hrms.model.Employee;
import com.hrms.model.Account;
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

    List<Employee> findByCompanyId(ObjectId companyId);

    List<Employee> findManagersByCompanyId(ObjectId companyId);

    Employee createWithAccount(Employee employee, Account account);
}