package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.example.dao.SalaryDao;
import com.example.entity.Salary;
import java.util.List;

@Stateless
public class SalaryServiceBean {

    @Inject
    private SalaryDao salaryDao;

    public void addSalary(Salary salary) {
        salaryDao.add(salary);
    }

    public void updateSalary(Salary salary) {
        salaryDao.update(salary);
    }

    public void removeSalary(String id) {
        salaryDao.removeById(id);
    }

    public boolean hasSalary(String id) {
        return salaryDao.has(id);
    }

    public List<Salary> getAllSalaries() {
        return salaryDao.getAll();
    }

    public List<Salary> getSalariesByEmployee(String employeeId) {
        return salaryDao.getWhere("employeeId", employeeId);
    }
}
