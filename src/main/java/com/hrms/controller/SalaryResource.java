package com.hrms.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.hrms.model.Salary;
import com.hrms.service.SalaryService;

import jakarta.ejb.EJB;
import org.bson.types.ObjectId;

import java.util.List;

@Path("/salaries")
@Produces({"application/json"})
@Consumes({"application/json"})
public class SalaryResource {

    @EJB
    private SalaryService salaryService;

    @GET
    public Response getAllSalaries() {
        return Response.ok(salaryService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getSalaryById(@PathParam("id") ObjectId id) {
        Salary salary = salaryService.findById(id);
        if (salary == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(salary).build();
    }

    @HEAD
    @Path("/{id}")
    public Response checkSalaryExists(@PathParam("id") ObjectId id) {
        Salary salary = salaryService.findById(id);
        if (salary == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }

    @POST
    public Response createSalary(Salary salary) {
        salaryService.create(salary);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateSalary(@PathParam("id") ObjectId id, Salary salary) {
        salaryService.update(id, salary);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteSalary(@PathParam("id") ObjectId id) {
        salaryService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/month/{month}/year/{year}/companyId/{companyId}")
    public Response getSalaryByMonth(@PathParam("month") int month, @PathParam("year") int year, @PathParam("companyId") ObjectId companyId) {
         List<Salary> salaries = salaryService.getByMonth(month, year, companyId);
         return Response.ok(salaries).build();
    }
}