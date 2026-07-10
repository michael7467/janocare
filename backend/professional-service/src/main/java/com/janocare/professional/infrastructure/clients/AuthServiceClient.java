package com.janocare.professional.infrastructure.clients;

import com.janocare.professional.api.responses.professional.UserResponse;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@Path("/users")
@RegisterRestClient(configKey = "auth-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthServiceClient {

    @GET
    @Path("/{id}")
    UserResponse getUserById(@PathParam("id") UUID id);

    @POST
    @Path("/batch")
    List<UserResponse> getUsersByIds(List<UUID> ids);
}