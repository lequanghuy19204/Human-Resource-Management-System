package com.hrms.impl;

import com.hrms.model.Employee;
import com.hrms.repository.EmployeeRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.hrms.service.SalaryService;
import com.hrms.repository.SalaryRepository;
import com.hrms.repository.EmployeeRepository;
import com.hrms.model.Salary;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class SalaryServiceImpl implements SalaryService {
    @Inject
    private SalaryRepository repository;
    private EmployeeRepository employeeRepository;

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

    @Override
    public List<Salary> getByMonth(int month, int year) {
        List<Salary> salaries = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            Salary salary = repository.getByMonth(month, year, employee.get_id());
            if (salary != null) {
                salary.setBase_salary(employee.getBase_salary());
                salary.setSumSalary(salary.getBase_salary()
                        + salary.getOvertime_hours()*50000
                        - salary.getLate_hours()*20000
                        - (salary.getAbsent_days() - salary.getApproved_leave_days())*100000);
            }
            else {
                salary = null;
            }
            salaries.add(salary);
        }
        return salaries;
    }
}