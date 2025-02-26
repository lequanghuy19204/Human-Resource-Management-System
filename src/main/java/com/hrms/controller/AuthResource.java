package com.hrms.controller;

import com.hrms.model.Account;
import com.hrms.model.Company;
import com.hrms.model.Employee;
import com.hrms.model.Organization;
import com.hrms.service.AccountService;
import com.hrms.service.CompanyService;
import com.hrms.service.EmployeeService;
import com.hrms.service.OrganizationService;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @EJB
    private AccountService accountService;

    @EJB
    private EmployeeService employeeService;

    @EJB
    private CompanyService companyService;

    @EJB
    private OrganizationService organizationService;

    @Context
    private HttpServletRequest request;

    @POST
    @Path("/login")
    public Response login(Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(createErrorResponse("Vui lòng nhập đầy đủ thông tin đăng nhập"))
                    .build();
        }

        Account account = accountService.findByUsername(username);
        if (account == null || !password.equals(account.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(createErrorResponse("Tên đăng nhập hoặc mật khẩu không chính xác"))
                    .build();
        }

        // Lấy thông tin nhân viên
        Employee employee = employeeService.findById(new ObjectId(account.getEmployee_id()));
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(createErrorResponse("Không tìm thấy thông tin nhân viên"))
                    .build();
        }

        // Lưu thông tin vào session
        HttpSession session = request.getSession(true);
        session.setAttribute("employeeId", employee.get_id());
        session.setAttribute("employeeName", employee.getName());
        session.setAttribute("companyId", employee.getCompany_id());
        session.setAttribute("permissions", employee.getPermissions());

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("employee", employee);

        return Response.ok(result).build();
    }

    @POST
    @Path("/register")
    public Response register(Map<String, String> registrationData) {
        String username = registrationData.get("username");
        String password = registrationData.get("password");
        String companyName = registrationData.get("companyName");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                companyName == null || companyName.trim().isEmpty()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(createErrorResponse("Vui lòng nhập đầy đủ thông tin đăng ký"))
                    .build();
        }

        // Kiểm tra username đã tồn tại chưa
        Account existingAccount = accountService.findByUsername(username);
        if (existingAccount != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(createErrorResponse("Tên đăng nhập đã tồn tại"))
                    .build();
        }

        try {
            // 1. Tạo công ty mới
            Company company = new Company();
            company.setName(companyName);
            company.setDescription("Công ty mẹ được tạo bởi Owner " + username);
            company = companyService.create(company);

            // 2. Tạo tổ chức mặc định
            Organization organization = new Organization();
            organization.setName("HR Department");
            organization.setCompanyId(company.get_id());
            organization = organizationService.create(organization);

            // 3. Tạo nhân viên (Owner)
            Employee employee = new Employee();
            employee.setName(username);
            employee.setPosition_name("Manager");

            List<String> permissions = Arrays.asList(
                    "MANAGE_ORGANIZATION", "MANAGE_EMPLOYEES", "MANAGE_TASKS",
                    "MANAGE_SALARY", "MANAGE_TRAINING", "MANAGE_PERFORMANCE");
            employee.setPermissions(permissions);

            employee.setOrganization_id(organization.getId());
            employee.setCompany_id(company.get_id());
            employee.setPhone(""); // Để trống hoặc giá trị mặc định
            employee.setOvertime_hours(0);
            employee.setLate_hours(0);
            employee.setAbsent_days(0);

            employee = employeeService.create(employee);

            // 4. Tạo tài khoản
            Account account = new Account();
            account.setEmployee_id(employee.get_id());
            account.setUsername(username);
            account.setPassword(password); // Trong thực tế nên mã hóa password
            account = accountService.create(account);

            // Cập nhật account_id cho employee
            employee.setAccount_id(account.get_id());
            employeeService.update(new ObjectId(employee.get_id()), employee);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Đăng ký thành công");

            return Response.status(Response.Status.CREATED).entity(result).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(createErrorResponse("Đăng ký thất bại: " + e.getMessage()))
                    .build();
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        return error;
    }
}