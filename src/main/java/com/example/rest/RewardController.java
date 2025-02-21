package com.example.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.example.ejb.RewardServiceBean;
import com.example.entity.Reward;
import java.util.List;

@Path("/rewards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RewardController {

    @EJB
    private RewardServiceBean rewardService;

    @POST
    public Response addReward(Reward reward) {
        rewardService.addReward(reward);
        return Response.ok(reward).build();
    }

    @PUT
    public Response updateReward(Reward reward) {
        rewardService.updateReward(reward);
        return Response.ok(reward).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeReward(@PathParam("id") String id) {
        rewardService.removeReward(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/exists")
    public Response hasReward(@PathParam("id") String id) {
        return Response.ok(rewardService.hasReward(id)).build();
    }

    @GET
    public Response getAllRewards() {
        List<Reward> rewards = rewardService.getAllRewards();
        return Response.ok(rewards).build();
    }

    @GET
    @Path("/employee/{employeeId}")
    public Response getRewardsByEmployee(@PathParam("employeeId") String employeeId) {
        List<Reward> rewards = rewardService.getRewardsByEmployee(employeeId);
        return Response.ok(rewards).build();
    }
}
