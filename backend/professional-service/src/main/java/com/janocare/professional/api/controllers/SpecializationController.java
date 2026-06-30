package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.specialization.CreateSpecializationRequest;
import com.janocare.professional.api.requests.specialization.UpdateSpecializationRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.specialization.CreateSpecializationCommand;
import com.janocare.professional.application.commands.specialization.DeleteSpecializationCommand;
import com.janocare.professional.application.commands.specialization.UpdateSpecializationCommand;
import com.janocare.professional.application.handlers.SpecializationHandler;
import com.janocare.professional.application.queries.specialization.FindAllSpecializationsQuery;
import com.janocare.professional.application.queries.specialization.FindSpecializationByIdQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/specializations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpecializationController {

    @Inject
    SpecializationHandler handler;

    @POST
    public Response create(
            CreateSpecializationRequest req
    ) {

        CreateSpecializationCommand command =
                new CreateSpecializationCommand();

        command.name = req.name;

        command.description = req.description;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                handler.create(command),
                                "Specialization created"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateSpecializationRequest req
    ) {

        UpdateSpecializationCommand command =
                new UpdateSpecializationCommand();

        command.specializationId = id;

        command.name = req.name;

        command.description = req.description;

        return Response.ok(
                ApiResponse.success(
                        handler.update(command),
                        "Specialization updated"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(
            @PathParam("id") UUID id
    ) {

        DeleteSpecializationCommand command =
                new DeleteSpecializationCommand(id);

        handler.delete(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "Specialization deleted"
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(
            @PathParam("id") UUID id
    ) {

        FindSpecializationByIdQuery query =
                new FindSpecializationByIdQuery(id);

        return Response.ok(
                ApiResponse.success(
                        handler.findById(query)
                )
        ).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {

        FindAllSpecializationsQuery query =
                new FindAllSpecializationsQuery();

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