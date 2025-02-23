package com.hrms.controller;

import com.hrms.model.Position;
import com.hrms.service.PositionService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/positions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PositionResource {

    @EJB
    private PositionService positionService;

    @GET
    public Response getAllPositions() {
        try {
            return Response.ok(positionService.findAll()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách vị trí: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getPositionById(@PathParam("id") String id) {
        try {
            Position position = positionService.findById(new ObjectId(id));
            if (position != null) {
                return Response.ok(position).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy vị trí với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tìm vị trí: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response createPosition(Position position) {
        try {
            Position created = positionService.create(position);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tạo vị trí: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updatePosition(@PathParam("id") String id, Position position) {
        try {
            boolean updated = positionService.update(new ObjectId(id), position);
            if (updated) {
                return Response.ok(position).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy vị trí để cập nhật với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi cập nhật vị trí: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePosition(@PathParam("id") String id) {
        try {
            boolean deleted = positionService.delete(new ObjectId(id));
            if (deleted) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy vị trí để xóa với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi xóa vị trí: " + e.getMessage())
                    .build();
        }
    }
}