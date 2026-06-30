package com.janocare.notification.api.controllers;

import com.janocare.notification.api.mappers.NotificationApiMapper;
import com.janocare.notification.api.requests.CreateNotificationRequest;
import com.janocare.notification.api.responses.ApiResponse;

import com.janocare.notification.application.commands.CreateNotificationCommand;
import com.janocare.notification.application.commands.MarkNotificationFailedCommand;
import com.janocare.notification.application.commands.MarkNotificationSentCommand;

import com.janocare.notification.application.handlers.NotificationHandler;

import com.janocare.notification.application.queries.FindAllNotificationsQuery;
import com.janocare.notification.application.queries.FindNotificationByIdQuery;

import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationController {

    @Inject
    NotificationHandler handler;

    @POST
    public Response create(
            CreateNotificationRequest req
    ) {

        CreateNotificationCommand command =
                new CreateNotificationCommand();

        command.userId = req.userId;
        command.subject = req.subject;
        command.message = req.message;
        command.destination = req.destination;
        command.type = req.type;
        command.channel = req.channel;
        command.notificationCategoryId =
                req.notificationCategoryId;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                NotificationApiMapper.toResponse(
                                        handler.createNotification(command)
                                ),
                                "Notification created successfully"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}/sent")
    public Response markSent(
            @PathParam("id") UUID id
    ) {

        MarkNotificationSentCommand command =
                new MarkNotificationSentCommand();

        command.notificationId = id;

        return Response.ok(
                ApiResponse.success(
                        NotificationApiMapper.toResponse(
                                handler.markNotificationSent(command)
                        ),
                        "Notification marked as sent"
                )
        ).build();
    }

    @PUT
    @Path("/{id}/failed")
    public Response markFailed(
            @PathParam("id") UUID id
    ) {

        MarkNotificationFailedCommand command =
                new MarkNotificationFailedCommand();

        command.notificationId = id;

        return Response.ok(
                ApiResponse.success(
                        NotificationApiMapper.toResponse(
                                handler.markNotificationFailed(command)
                        ),
                        "Notification marked as failed"
                )
        ).build();
    }

    @GET
    public Response findAll(
            @QueryParam("userId") UUID userId
    ) {

        FindAllNotificationsQuery query =
                new FindAllNotificationsQuery();

        query.userId = userId;

        return Response.ok(
                ApiResponse.success(
                        handler.findAllNotifications(query)
                                .stream()
                                .map(NotificationApiMapper::toResponse)
                                .toList()
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(
            @PathParam("id") UUID id
    ) {

        return Response.ok(
                ApiResponse.success(
                        NotificationApiMapper.toResponse(
                                handler.findNotificationById(
                                        new FindNotificationByIdQuery(id)
                                )
                        )
                )
        ).build();
    }
}