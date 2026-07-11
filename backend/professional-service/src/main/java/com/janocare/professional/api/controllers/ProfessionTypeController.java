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

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
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

    // =====================================================
    // CREATE — admin only
    // Accountability pattern: admin adds knowledge-level object
    // =====================================================

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid CreateProfessionalTypeRequest req) {

        CreateProfessionalTypeCommand command =
                new CreateProfessionalTypeCommand();
        command.name         = req.name;
        command.description  = req.description;
        command.slotInterval = req.slotInterval; // ADD

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Profession type created"
                ))
                .build();
    }

    // =====================================================
    // UPDATE — admin only
    // =====================================================

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(
            @PathParam("id") UUID id,
            @Valid UpdateProfessionalTypeRequest req) {

        UpdateProfessionalTypeCommand command =
                new UpdateProfessionalTypeCommand();
        command.professionTypeId = id;
        command.name             = req.name;
        command.description      = req.description;
        command.slotInterval     = req.slotInterval; // ADD
        command.active           = req.active;        // ADD

        return Response.ok(
                ApiResponse.success(
                        handler.update(command),
                        "Profession type updated"
                )
        ).build();
    }

    // =====================================================
    // DELETE — admin only
    // =====================================================

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") UUID id) {

        handler.delete(new DeleteProfessionalTypeCommand(id));

        return Response.ok(
                ApiResponse.success(null, "Profession type deleted")
        ).build();
    }

    // =====================================================
    // READ — public (patients browse profession types)
    // =====================================================

    @GET
    @Path("/{id}")
    @PermitAll
    public Response findById(@PathParam("id") UUID id) {

        return Response.ok(
                ApiResponse.success(
                        handler.findById(
                                new FindProfessionalTypeByIdQuery(id))
                )
        ).build();
    }

    @GET
    @PermitAll
    public Response findAll(
            @QueryParam("page")   Integer page,
            @QueryParam("size")   Integer size,
            @QueryParam("search") String search,
            @QueryParam("active") Boolean active  // ADD
    ) {
        FindAllProfessionalTypesQuery query =
                new FindAllProfessionalTypesQuery();
        query.page   = page;
        query.size   = size;
        query.search = search;
        query.active = active;                    // ADD

        return Response.ok(
                ApiResponse.success(handler.findAll(query))
        ).build();
    }
}