package com.hrms.controller;

import com.hrms.model.Company;
import com.hrms.repository.CompanyRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyResource {
    private final CompanyRepository repository = new CompanyRepository();

    @GET
    public Response getAllCompanies() {
        try {
            return Response.ok(repository.findAll()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách công ty: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCompanyById(@PathParam("id") String id) {
        try {
            Company company = repository.findById(new ObjectId(id));
            if (company != null) {
                return Response.ok(company).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy công ty với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tìm công ty: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response createCompany(Company company) {
        try {
            Company created = repository.create(company);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tạo công ty: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateCompany(@PathParam("id") String id, Company company) {
        try {
            boolean updated = repository.update(new ObjectId(id), company);
            if (updated) {
                return Response.ok(company).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy công ty để cập nhật với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi cập nhật công ty: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCompany(@PathParam("id") String id) {
        try {
            boolean deleted = repository.delete(new ObjectId(id));
            if (deleted) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy công ty để xóa với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi xóa công ty: " + e.getMessage())
                    .build();
        }
    }
}