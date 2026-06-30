package com.janocare.auth.infrastructure.clients.notification;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/notifications")
@RegisterRestClient(configKey = "notification-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface NotificationClient {

    @POST
    NotificationApiResponse<NotificationResponse> createNotification(
            CreateNotificationRequest request
    );
}