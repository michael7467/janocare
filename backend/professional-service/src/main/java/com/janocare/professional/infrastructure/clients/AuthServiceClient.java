package com.janocare.professional.infrastructure.clients;

import com.janocare.professional.api.responses.professional.UserProfileResponse;
import com.janocare.professional.api.responses.professional.UserResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@Path("/users")
@RegisterRestClient(configKey = "auth-service")
public interface AuthServiceClient {

    @GET
    @Path("/{id}")
    UserResponse getUserById(@PathParam("id") UUID id);
}