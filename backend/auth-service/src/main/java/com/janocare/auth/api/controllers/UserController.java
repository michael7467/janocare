package com.janocare.auth.api.controllers;

import com.janocare.auth.api.responses.user.UserResponse;
import com.janocare.auth.application.handlers.UserHandler;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserHandler userHandler;

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") UUID id) {
        UserResponse response = userHandler.findById(id);
        return Response.ok(response).build();
    }
}