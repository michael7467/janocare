package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalsubspecialization.CreateProfessionalSubSpecializationRequest;
import com.janocare.professional.api.requests.professionalsubspecialization.UpdateProfessionalSubSpecializationRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalsubspecialization.CreateProfessionalSubSpecializationCommand;
import com.janocare.professional.application.commands.professionalsubspecialization.DeleteProfessionalSubSpecializationCommand;
import com.janocare.professional.application.commands.professionalsubspecialization.UpdateProfessionalSubSpecializationCommand;
import com.janocare.professional.application.handlers.ProfessionalSubSpecializationHandler;
import com.janocare.professional.application.queries.professionalsubspecialization.FindAllProfessionalSubSpecializationsQuery;
import com.janocare.professional.application.queries.professionalsubspecialization.FindProfessionalSubSpecializationByIdQuery;
import com.janocare.professional.application.queries.professionalsubspecialization.FindProfessionalSubSpecializationsByProfessionalIdQuery;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-sub-specializations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalSubSpecializationController {

    @Inject
    ProfessionalSubSpecializationHandler handler;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(CreateProfessionalSubSpecializationRequest req) {
        CreateProfessionalSubSpecializationCommand command =
                new CreateProfessionalSubSpecializationCommand();

        command.professionalId = req.professionalId;
        command.subSpecializationId = req.subSpecializationId;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Professional sub specialization created"
                ))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalSubSpecializationRequest req
    ) {
        UpdateProfessionalSubSpecializationCommand command =
                new UpdateProfessionalSubSpecializationCommand();

        command.professionalSubSpecializationId = id;
        command.subSpecializationId = req.subSpecializationId;

        return Response.ok(ApiResponse.success(
                handler.update(command),
                "Professional sub specialization updated"
        )).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(ApiResponse.success(
                handler.findById(
                        new FindProfessionalSubSpecializationByIdQuery(id)
                )
        )).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    @PermitAll
    public Response findByProfessionalId(
            @PathParam("professionalId") UUID professionalId
    ) {
        return Response.ok(ApiResponse.success(
                handler.findByProfessionalId(
                        new FindProfessionalSubSpecializationsByProfessionalIdQuery(professionalId)
                )
        )).build();
    }

    @GET
    @PermitAll
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {
        FindAllProfessionalSubSpecializationsQuery query =
                new FindAllProfessionalSubSpecializationsQuery();

        query.page = page;
        query.size = size;
        query.search = search;

        return Response.ok(ApiResponse.success(
                handler.findAll(query)
        )).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") UUID id) {
        handler.delete(new DeleteProfessionalSubSpecializationCommand(id));

        return Response.ok(ApiResponse.success(
                null,
                "Professional sub specialization deleted"
        )).build();
    }
}