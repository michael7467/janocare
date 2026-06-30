package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professiontype.CreateProfessionalTypeRequest;
import com.janocare.professional.api.requests.professiontype.UpdateProfessionalTypeRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professiontype.CreateProfessionalTypeCommand;
import com.janocare.professional.application.commands.professiontype.DeleteProfessionalTypeCommand;
import com.janocare.professional.application.commands.professiontype.UpdateProfessionalTypeCommand;
import com.janocare.professional.application.handlers.ProfessionTypeHandler;
import com.janocare.professional.application.queries.professiontype.FindAllProfessionalTypesQuery;
import com.janocare.professional.application.queries.professiontype.FindProfessionalTypeByIdQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/profession-types")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionTypeController {

    @Inject
    ProfessionTypeHandler handler;

    @POST
    public Response create(CreateProfessionalTypeRequest req) {
        CreateProfessionalTypeCommand command = new CreateProfessionalTypeCommand();

        command.name = req.name;
        command.description = req.description;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Profession type created"
                ))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalTypeRequest req
    ) {
        UpdateProfessionalTypeCommand command = new UpdateProfessionalTypeCommand();

        command.professionTypeId = id;
        command.name = req.name;
        command.description = req.description;

        return Response.ok(
                ApiResponse.success(
                        handler.update(command),
                        "Profession type updated"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        DeleteProfessionalTypeCommand command =
                new DeleteProfessionalTypeCommand(id);

        handler.delete(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "Profession type deleted"
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        FindProfessionalTypeByIdQuery query =
                new FindProfessionalTypeByIdQuery(id);

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
        FindAllProfessionalTypesQuery query =
                new FindAllProfessionalTypesQuery();

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