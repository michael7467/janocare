package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.subspecialization.CreateSubSpecializationRequest;
import com.janocare.professional.api.requests.subspecialization.UpdateSubSpecializationRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.subspecialization.CreateSubSpecializationCommand;
import com.janocare.professional.application.commands.subspecialization.DeleteSubSpecializationCommand;
import com.janocare.professional.application.commands.subspecialization.UpdateSubSpecializationCommand;
import com.janocare.professional.application.handlers.SubSpecializationHandler;
import com.janocare.professional.application.queries.subspecialization.FindAllSubSpecializationsQuery;
import com.janocare.professional.application.queries.subspecialization.FindSubSpecializationByIdQuery;
import com.janocare.professional.application.queries.subspecialization.FindSubSpecializationsBySpecializationIdQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/sub-specializations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubSpecializationController {

    @Inject
    SubSpecializationHandler handler;

    @POST
    public Response create(
            CreateSubSpecializationRequest req
    ) {

        CreateSubSpecializationCommand command =
                new CreateSubSpecializationCommand();

        command.specializationId =
                req.specializationId;

        command.name = req.name;

        command.description = req.description;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                handler.create(command),
                                "Sub specialization created"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateSubSpecializationRequest req
    ) {

        UpdateSubSpecializationCommand command =
                new UpdateSubSpecializationCommand();

        command.subSpecializationId = id;

        command.name = req.name;

        command.description = req.description;

        return Response.ok(
                ApiResponse.success(
                        handler.update(command),
                        "Sub specialization updated"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(
            @PathParam("id") UUID id
    ) {

        DeleteSubSpecializationCommand command =
                new DeleteSubSpecializationCommand(id);

        handler.delete(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "Sub specialization deleted"
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(
            @PathParam("id") UUID id
    ) {

        FindSubSpecializationByIdQuery query =
                new FindSubSpecializationByIdQuery(id);

        return Response.ok(
                ApiResponse.success(
                        handler.findById(query)
                )
        ).build();
    }

    @GET
    @Path("/specialization/{specializationId}")
    public Response findBySpecializationId(
            @PathParam("specializationId")
            UUID specializationId
    ) {

        FindSubSpecializationsBySpecializationIdQuery query =
                new FindSubSpecializationsBySpecializationIdQuery(
                        specializationId
                );

        return Response.ok(
                ApiResponse.success(
                        handler.findBySpecializationId(query)
                )
        ).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {

        FindAllSubSpecializationsQuery query =
                new FindAllSubSpecializationsQuery();

        query.page = page;

        query.size = size;

        query.search = search;

        return Response.ok(
                ApiResponse.success(
                        handler.findAll(query)
                )
        ).build();
    }
}