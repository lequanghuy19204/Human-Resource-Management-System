package com.hrms.controller;

import com.hrms.model.TrainingProgram;
import com.hrms.service.TrainingProgramService;
import com.hrms.model.Employee;
import com.hrms.service.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/training-programs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrainingProgramResource {

    @EJB
    private TrainingProgramService trainingProgramService;

    @EJB
    private EmployeeService employeeService;

    @GET
    public Response getAllTrainingPrograms() {
        try {
            return Response.ok(trainingProgramService.findAll()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách chương trình đào tạo: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getTrainingProgramById(@PathParam("id") String id) {
        try {
            TrainingProgram program = trainingProgramService.findById(new ObjectId(id));
            if (program != null) {
                return Response.ok(program).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy chương trình đào tạo với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tìm chương trình đào tạo: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response createTrainingProgram(TrainingProgram program) {
        try {
            TrainingProgram created = trainingProgramService.create(program);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tạo chương trình đào tạo: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateTrainingProgram(@PathParam("id") String id, TrainingProgram program) {
        try {
            boolean updated = trainingProgramService.update(new ObjectId(id), program);
            if (updated) {
                return Response.ok(program).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy chương trình đào tạo để cập nhật với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi cập nhật chương trình đào tạo: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTrainingProgram(@PathParam("id") String id) {
        try {
            boolean deleted = trainingProgramService.delete(new ObjectId(id));
            if (deleted) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy chương trình đào tạo để xóa với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi xóa chương trình đào tạo: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/trainer/{trainerId}")
    public Response getTrainerName(@PathParam("trainerId") String trainerId) {
        try {
            Employee trainer = employeeService.findById(new ObjectId(trainerId));
            if (trainer != null) {
                return Response.ok(trainer).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy trainer với ID: " + trainerId)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy thông tin trainer: " + e.getMessage())
                    .build();
        }
    }
}