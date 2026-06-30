package com.janocare.booking.infrastructure.clients;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.UUID;
import jakarta.ws.rs.PathParam;
@Path("/professionals")
@RegisterRestClient(configKey = "professional-service")
public interface ProfessionalServiceClient {

    @GET
    @Path("/by-user/{userId}")  // ← path param, not query param
    ProfessionalResponse getProfessionalByUserId(@PathParam("userId") UUID userId);
}