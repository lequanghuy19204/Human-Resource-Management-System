package com.hrms.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.hrms.model.Performance;
import com.hrms.service.PerformanceService;

import jakarta.ejb.EJB;
import org.bson.types.ObjectId;

@Path("/performances")
@Produces({"application/json"})
@Consumes({"application/json"})
public class PerformanceResource {

    @EJB
    private PerformanceService performanceService;

    @GET
    public Response getAllPerformances() {
        return Response.ok(performanceService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getPerformanceById(@PathParam("id") ObjectId id) {
        Performance performance = performanceService.findById(id);
        if (performance == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(performance).build();
    }

    @POST
    public Response createPerformance(Performance performance) {
        performanceService.create(performance);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updatePerformance(@PathParam("id") ObjectId id, Performance performance) {
        performanceService.update(id, performance);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerformance(@PathParam("id") ObjectId id) {
        performanceService.delete(id);
        return Response.noContent().build();
    }
}