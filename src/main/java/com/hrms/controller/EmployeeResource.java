package com.hrms.controller;

import com.hrms.model.Employee;
import com.hrms.service.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @EJB
    private EmployeeService employeeService;

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

    @POST
    public Response createEmployee(Employee employee) {
        try {
            Employee created = employeeService.create(employee);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
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
                    .entity("Không tìm thấy nhân viên để cập nhật với ID: " + id)
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
}