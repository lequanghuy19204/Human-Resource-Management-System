package com.hrms.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.hrms.model.Reward;
import com.hrms.service.RewardService;

import jakarta.ejb.EJB;
import org.bson.types.ObjectId;

@Path("/rewards")
@Produces({"application/json"})
@Consumes({"application/json"})
public class RewardResource {

    @EJB
    private RewardService rewardService;

    @GET
    public Response getAllRewards() {
        return Response.ok(rewardService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getRewardById(@PathParam("id") ObjectId id) {
        Reward reward = rewardService.findById(id);
        if (reward == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reward).build();
    }

    @POST
    public Response createReward(Reward reward) {
        rewardService.create(reward);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateReward(@PathParam("id") ObjectId id, Reward reward) {
        rewardService.update(id, reward);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteReward(@PathParam("id") ObjectId id) {
        rewardService.delete(id);
        return Response.noContent().build();
    }
}