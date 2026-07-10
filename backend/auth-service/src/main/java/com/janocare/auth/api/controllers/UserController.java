package com.janocare.auth.api.controllers;

import com.janocare.auth.api.responses.user.UserResponse;
import com.janocare.auth.application.handlers.UserHandler;
import com.janocare.auth.application.queries.user.FindUserByIdQuery;
import com.janocare.auth.api.responses.ApiResponse;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserHandler userHandler;

    @GET
    @Path("/{id}")
    @PermitAll
    public Response getUserById(@PathParam("id") UUID id) {
        UserResponse response = userHandler.findById(id);
        return Response.ok(response).build();
    }

    @POST
    @Path("/batch")
    @PermitAll
    public Response findByIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return Response.ok(ApiResponse.success(List.of())).build();
        }

        List<UserResponse> users = ids.stream()
                .map(id -> {
                    try {
                        return userHandler.findById(id);
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        return Response.ok(ApiResponse.success(users)).build();
    }
}