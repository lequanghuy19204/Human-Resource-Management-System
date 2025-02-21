package com.example.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.ejb.SalaryServiceBean;
import com.example.entity.Salary;
import java.util.List;

@Path("/salaries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalaryController {

    @EJB
    private SalaryServiceBean salaryService;

    @POST
    public Response addSalary(Salary salary) {
        salaryService.addSalary(salary);
        return Response.ok(salary).build();
    }

    @PUT
    public Response updateSalary(Salary salary) {
        salaryService.updateSalary(salary);
        return Response.ok(salary).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeSalary(@PathParam("id") String id) {
        salaryService.removeSalary(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/exists")
    public Response hasSalary(@PathParam("id") String id) {
        return Response.ok(salaryService.hasSalary(id)).build();
    }

    @GET
    public Response getAllSalaries() {
        List<Salary> salaries = salaryService.getAllSalaries();
        return Response.ok(salaries).build();
    }

    @GET
    @Path("/employee/{employeeId}")
    public Response getSalariesByEmployee(@PathParam("employeeId") String employeeId) {
        List<Salary> salaries = salaryService.getSalariesByEmployee(employeeId);
        return Response.ok(salaries).build();
    }
}
