package com.example.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.ejb.TaskServiceBean;
import com.example.entity.Task;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskController {

    @EJB
    private TaskServiceBean taskService;

    @POST
    public Response addTask(Task task) {
        taskService.addTask(task);
        return Response.ok(task).build();
    }

    @PUT
    public Response updateTask(Task task) {
        taskService.updateTask(task);
        return Response.ok(task).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeTask(@PathParam("id") String id) {
        taskService.removeTask(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/exists")
    public Response hasTask(@PathParam("id") String id) {
        return Response.ok(taskService.hasTask(id)).build();
    }

    @GET
    public Response getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return Response.ok(tasks).build();
    }

    @GET
    @Path("/status/{status}")
    public Response getTasksByStatus(@PathParam("status") String status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        return Response.ok(tasks).build();
    }
}
