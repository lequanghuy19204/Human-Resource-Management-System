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
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.hrms.model.Organization;
import com.hrms.repository.OrganizationRepository;

@Stateless
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private OrganizationRepository organizationRepository;

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

    @Override
    public List<Map<String, Object>> findByCompanyIdWithDetails(ObjectId companyId) {
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Employee employee : employees) {
            Map<String, Object> details = new HashMap<>();
            details.put("_id", employee.get_id());
            details.put("name", employee.getName());
            details.put("position_name", employee.getPosition_name());
            details.put("phone", employee.getPhone());
            details.put("base_salary", employee.getBase_salary());
            details.put("working_days", employee.getWorking_days());
            details.put("approved_leave_days", employee.getApproved_leave_days());

            // Lấy tên tổ chức
            if (employee.getOrganization_id() != null) {
                Organization org = organizationRepository.findById(new ObjectId(employee.getOrganization_id()));
                details.put("organization_name", org != null ? org.getName() : "Không có");
            } else {
                details.put("organization_name", "Không có");
            }

            result.add(details);
        }

        return result;
    }
}