package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalspecialization.CreateProfessionalSpecializationRequest;
import com.janocare.professional.api.requests.professionalspecialization.UpdateProfessionalSpecializationRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalspecialization.CreateProfessionalSpecializationCommand;
import com.janocare.professional.application.commands.professionalspecialization.DeleteProfessionalSpecializationCommand;
import com.janocare.professional.application.commands.professionalspecialization.UpdateProfessionalSpecializationCommand;
import com.janocare.professional.application.handlers.ProfessionalSpecializationHandler;
import com.janocare.professional.application.queries.professionalspecialization.FindAllProfessionalSpecializationsQuery;
import com.janocare.professional.application.queries.professionalspecialization.FindProfessionalSpecializationByIdQuery;
import com.janocare.professional.application.queries.professionalspecialization.FindProfessionalSpecializationsByProfessionalIdQuery;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-specializations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalSpecializationController {

    @Inject
    ProfessionalSpecializationHandler handler;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(
            CreateProfessionalSpecializationRequest req
    ) {

        CreateProfessionalSpecializationCommand command =
                new CreateProfessionalSpecializationCommand();

        command.professionalId = req.professionalId;

        command.specializationId = req.specializationId;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                handler.create(command),
                                "Professional specialization created"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalSpecializationRequest req
    ) {

        UpdateProfessionalSpecializationCommand command =
                new UpdateProfessionalSpecializationCommand();

        command.professionalSpecializationId = id;

        command.specializationId =
                req.specializationId;

        return Response.ok(
                ApiResponse.success(
                        handler.update(command),
                        "Professional specialization updated"
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response findById(
            @PathParam("id") UUID id
    ) {

        FindProfessionalSpecializationByIdQuery query =
                new FindProfessionalSpecializationByIdQuery(id);

        return Response.ok(
                ApiResponse.success(
                        handler.findById(query)
                )
        ).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    @PermitAll
    public Response findByProfessionalId(
            @PathParam("professionalId") UUID professionalId
    ) {

        FindProfessionalSpecializationsByProfessionalIdQuery query =
                new FindProfessionalSpecializationsByProfessionalIdQuery(
                        professionalId
                );

        return Response.ok(
                ApiResponse.success(
                        handler.findByProfessionalId(query)
                )
        ).build();
    }

    @GET
    @PermitAll
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {

        FindAllProfessionalSpecializationsQuery query =
                new FindAllProfessionalSpecializationsQuery();

        query.page = page;

        query.size = size;

        query.search = search;

        return Response.ok(
                ApiResponse.success(
                        handler.findAll(query)
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(
            @PathParam("id") UUID id
    ) {

        DeleteProfessionalSpecializationCommand command =
                new DeleteProfessionalSpecializationCommand(id);

        handler.delete(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "Professional specialization deleted"
                )
        ).build();
    }
}