package com.hrms.controller;

import com.hrms.model.Organization;
import com.hrms.service.OrganizationService;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.List;

@Path("/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationResource {

    @EJB
    private OrganizationService organizationService;

    @Context
    private HttpServletRequest request;

    @GET
    public Response getAllOrganizations() {
        try {
            HttpSession session = request.getSession(false);
            String companyId = (String) session.getAttribute("companyId");
            return Response.ok(organizationService.findByCompanyId(new ObjectId(companyId))).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách tổ chức: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getOrganizationById(@PathParam("id") String id) {
        try {
            Organization organization = organizationService.findById(new ObjectId(id));
            if (organization != null) {
                return Response.ok(organization).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy tổ chức với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tìm tổ chức: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response createOrganization(Organization organization) {
        try {
            HttpSession session = request.getSession(false);
            String companyId = (String) session.getAttribute("companyId");
            organization.setCompanyId(companyId);

            Organization created = organizationService.create(organization);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tạo tổ chức: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateOrganization(@PathParam("id") String id, Organization organization) {
        try {
            HttpSession session = request.getSession(false);
            String companyId = (String) session.getAttribute("companyId");
            organization.setCompanyId(companyId);

            boolean updated = organizationService.update(new ObjectId(id), organization);
            if (updated) {
                return Response.ok(organization).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy tổ chức để cập nhật")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi cập nhật tổ chức: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrganization(@PathParam("id") String id) {
        try {
            boolean deleted = organizationService.delete(new ObjectId(id));
            if (deleted) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy tổ chức để xóa")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi xóa tổ chức: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/company/{companyId}")
    public Response getOrganizationsByCompanyId(@PathParam("companyId") String companyId) {
        try {
            List<Organization> organizations = organizationService.findByCompanyId(new ObjectId(companyId));
            return Response.ok(organizations).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách tổ chức: " + e.getMessage())
                    .build();
        }
    }
}