package com.janocare.booking.api.controllers;

import com.janocare.booking.api.mappers.CancellationReasonApiMapper;
import com.janocare.booking.api.requests.CreateCancellationReasonRequest;
import com.janocare.booking.api.responses.ApiResponse;
import com.janocare.booking.application.commands.CreateCancellationReasonCommand;
import com.janocare.booking.application.handlers.BookingHandler;
import com.janocare.booking.application.queries.FindAllCancellationReasonsQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cancellation-reasons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CancellationReasonController {

    @Inject
    BookingHandler handler;

    @POST
    public Response create(CreateCancellationReasonRequest req) {
        CreateCancellationReasonCommand command =
                new CreateCancellationReasonCommand();

        command.reason = req.reason;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        CancellationReasonApiMapper.toResponse(
                                handler.createCancellationReason(command)
                        ),
                        "Cancellation reason created successfully"
                ))
                .build();
    }

    @GET
    public Response findAll() {
        return Response.ok(
                ApiResponse.success(
                        handler.findAllCancellationReasons(
                                        new FindAllCancellationReasonsQuery()
                                )
                                .stream()
                                .map(CancellationReasonApiMapper::toResponse)
                                .toList()
                )
        ).build();
    }
}