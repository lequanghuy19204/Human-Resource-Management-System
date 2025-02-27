package com.hrms.impl;

import com.hrms.model.Employee;
import com.hrms.model.Account;
import com.hrms.repository.EmployeeRepository;
import com.hrms.repository.AccountRepository;
import com.hrms.service.EmployeeService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import java.util.List;

@Stateless
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private AccountRepository accountRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(ObjectId id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.create(employee);
    }

    @Override
    public boolean update(ObjectId id, Employee employee) {
        return employeeRepository.update(id, employee);
    }

    @Override
    public boolean delete(ObjectId id) {
        return employeeRepository.delete(id);
    }

    @Override
    public List<Employee> findByCompanyId(ObjectId companyId) {
        return employeeRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Employee> findManagersByCompanyId(ObjectId companyId) {
        return employeeRepository.findManagersByCompanyId(companyId);
    }

    @Override
    public Employee createWithAccount(Employee employee, Account account) {
        // Tạo nhân viên trước
        Employee savedEmployee = employeeRepository.create(employee);

        // Thiết lập employee_id cho account và lưu account
        account.setEmployee_id(savedEmployee.get_id());
        Account savedAccount = accountRepository.create(account);

        // Cập nhật account_id cho employee
        savedEmployee.setAccount_id(savedAccount.get_id());
        employeeRepository.update(new ObjectId(savedEmployee.get_id()), savedEmployee);

        return savedEmployee;
    }

    @Override
    public List<Employee> findByOrganizationId(ObjectId organizationId) {
        return employeeRepository.findByOrganizationId(organizationId);
    }
}