package com.hrms.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.hrms.model.Task;
import com.hrms.service.TaskService;

import jakarta.ejb.EJB;
import org.bson.types.ObjectId;

import java.util.List;

@Path("/tasks")
@Produces({"application/json"})
@Consumes({"application/json"})
public class TaskResource {

    @EJB
    private TaskService taskService;

    @GET
    public Response getAllTasks() {
        return Response.ok(taskService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getTaskById(@PathParam("id") ObjectId id) {
        Task task = taskService.findById(id);
        if (task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(task).build();
    }

    @GET
    @Path("/company/{companyId}")
    public Response getTaskByCompanyId(@PathParam("companyId") ObjectId id) {
        List<Task> task = taskService.findByCompanyId(id);
        if (task == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(task).build();
    }

    @POST
    public Response createTask(Task task) {
        taskService.create(task);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateTask(@PathParam("id") ObjectId id, Task task) {
        taskService.update(id, task);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTask(@PathParam("id") ObjectId id) {
        taskService.delete(id);
        return Response.noContent().build();
    }
}