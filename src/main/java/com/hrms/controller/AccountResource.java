package com.hrms.controller;

import com.hrms.model.Account;
import com.hrms.service.AccountService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @EJB
    private AccountService accountService;

    @GET
    public Response getAllAccounts() {
        try {
            return Response.ok(accountService.findAll()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi lấy danh sách tài khoản: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getAccountById(@PathParam("id") String id) {
        try {
            Account account = accountService.findById(new ObjectId(id));
            if (account != null) {
                return Response.ok(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy tài khoản với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tìm tài khoản: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/username/{username}")
    public Response getAccountByUsername(@PathParam("username") String username) {
        try {
            Account account = accountService.findByUsername(username);
            if (account != null) {
                return Response.ok(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy tài khoản với username: " + username)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tìm tài khoản: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response createAccount(Account account) {
        try {
            Account created = accountService.create(account);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi tạo tài khoản: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateAccount(@PathParam("id") String id, Account account) {
        try {
            boolean updated = accountService.update(new ObjectId(id), account);
            if (updated) {
                return Response.ok(account).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy tài khoản để cập nhật với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi cập nhật tài khoản: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("id") String id) {
        try {
            boolean deleted = accountService.delete(new ObjectId(id));
            if (deleted) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy tài khoản để xóa với ID: " + id)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Lỗi khi xóa tài khoản: " + e.getMessage())
                    .build();
        }
    }
}