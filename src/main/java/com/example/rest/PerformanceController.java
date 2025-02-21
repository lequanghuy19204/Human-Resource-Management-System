package com.example.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.ejb.PerformanceServiceBean;
import com.example.entity.Performance;
import java.util.List;

@Path("/performances")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerformanceController {

    @EJB
    private PerformanceServiceBean performanceService;

    @POST
    public Response addPerformance(Performance performance) {
        performanceService.addPerformance(performance);
        return Response.ok(performance).build();
    }

    @PUT
    public Response updatePerformance(Performance performance) {
        performanceService.updatePerformance(performance);
        return Response.ok(performance).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removePerformance(@PathParam("id") String id) {
        performanceService.removePerformance(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/exists")
    public Response hasPerformance(@PathParam("id") String id) {
        return Response.ok(performanceService.hasPerformance(id)).build();
    }

    @GET
    public Response getAllPerformances() {
        List<Performance> performances = performanceService.getAllPerformances();
        return Response.ok(performances).build();
    }

    @GET
    @Path("/employee/{employeeId}")
    public Response getPerformancesByEmployee(@PathParam("employeeId") String employeeId) {
        List<Performance> performances = performanceService.getPerformancesByEmployee(employeeId);
        return Response.ok(performances).build();
    }
}
