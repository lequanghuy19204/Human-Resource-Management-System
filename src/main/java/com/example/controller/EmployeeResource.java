package com.example.controller;

import com.example.config.MongoDBConnection;
import com.mongodb.client.MongoDatabase;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/employees")
public class EmployeeResource {

    @GET
    @Path("/test-mongodb")
    @Produces("application/json")
    public Response testMongoDBConnection() {
        try {
            MongoDatabase db = MongoDBConnection.getDatabase();
            String dbName = db.getName();
            MongoDBConnection.closeConnection();
            return Response.ok()
                    .entity("{\"status\": \"success\", \"message\": \"Connected to MongoDB\", \"database\": \"" + dbName
                            + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}