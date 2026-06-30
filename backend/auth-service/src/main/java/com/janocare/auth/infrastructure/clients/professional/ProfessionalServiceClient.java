package com.janocare.auth.infrastructure.clients.professional;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import com.janocare.auth.infrastructure.config.RestClientLoggingFilter;
import java.util.UUID;

@Path("/professionals")
@RegisterRestClient(configKey = "professional-service")
@RegisterProvider(RestClientLoggingFilter.class)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ProfessionalServiceClient {

    @POST
    @Path("/internal")
    void createProfessional(CreateProfessionalRequest request);

    default void createProfessional(UUID userId, UUID professionTypeId) {
        CreateProfessionalRequest request = new CreateProfessionalRequest();
        request.userId = userId;
        request.professionTypeId = professionTypeId;
        createProfessional(request);
    }
}