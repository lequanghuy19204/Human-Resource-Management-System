package com.hrms.controller;

import com.hrms.model.Employee;
import com.hrms.model.Account;
import com.hrms.service.EmployeeService;
import com.hrms.service.AccountService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @EJB
    private EmployeeService employeeService;

    @EJB
    private AccountService accountService;

    @GET
    public Response getAllEmployees() {
        try {
            return Response.ok(employeeService.findAll()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách nhân viên: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") String id) {
        try {
            Employee employee = employeeService.findById(new ObjectId(id));
            if (employee != null) {
                return Response.ok(employee).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy nhân viên với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tìm nhân viên: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/managers/{companyId}")
    public Response getManagersByCompanyId(@PathParam("companyId") String companyId) {
        try {
            List<Employee> managers = employeeService.findManagersByCompanyId(new ObjectId(companyId));
            return Response.ok(managers).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách quản lý: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response createEmployee(Map<String, Object> data) {
        try {
            // Tạo nhân viên mới
            Employee employee = new Employee();
            employee.setName((String) data.get("name"));
            employee.setPosition_name((String) data.get("position_name"));
            employee.setOrganization_id((String) data.get("organization_id"));
            employee.setCompany_id((String) data.get("company_id"));
            employee.setManager_id((String) data.get("manager_id"));
            employee.setPhone((String) data.get("phone"));
            employee.setPermissions((List<String>) data.get("permissions"));
            employee.setOvertime_hours(0);
            employee.setLate_hours(0);
            employee.setAbsent_days(0);

            // Tạo tài khoản mới
            Map<String, String> accountData = (Map<String, String>) data.get("account");
            if (accountData != null) {
                Account account = new Account();
                account.setUsername(accountData.get("username"));
                account.setPassword(accountData.get("password"));

                // Lưu nhân viên và tài khoản
                Employee savedEmployee = employeeService.createWithAccount(employee, account);
                return Response.status(Response.Status.CREATED).entity(savedEmployee).build();
            }

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Thiếu thông tin tài khoản")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tạo nhân viên: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployee(@PathParam("id") String id, Employee employee) {
        try {
            boolean updated = employeeService.update(new ObjectId(id), employee);
            if (updated) {
                return Response.ok(employee).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy nhân viên để cập nhật")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi cập nhật nhân viên: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") String id) {
        try {
            boolean deleted = employeeService.delete(new ObjectId(id));
            if (deleted) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy nhân viên để xóa với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi xóa nhân viên: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/company/{companyId}")
    public Response getEmployeesByCompanyId(@PathParam("companyId") String companyId) {
        try {
            List<Employee> employees = employeeService.findByCompanyId(new ObjectId(companyId));
            return Response.ok(employees).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách nhân viên: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/organization/{organizationId}")
    public Response getEmployeesByOrganizationId(@PathParam("organizationId") String organizationId) {
        try {
            List<Employee> employees = employeeService.findByOrganizationId(new ObjectId(organizationId));
            return Response.ok(employees).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách nhân viên: " + e.getMessage())
                    .build();
        }
    }
}