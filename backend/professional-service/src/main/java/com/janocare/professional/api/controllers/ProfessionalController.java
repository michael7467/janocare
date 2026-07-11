package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professional.CreateProfessionalRequest;
import com.janocare.professional.api.requests.professional.UpdateProfessionalRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professional.ApproveProfessionalCommand;
import com.janocare.professional.application.commands.professional.RejectProfessionalCommand;
import com.janocare.professional.application.commands.professional.CreateProfessionalCommand;
import com.janocare.professional.application.commands.professional.UpdateProfessionalCommand;
import com.janocare.professional.application.handlers.ProfessionalHandler;
import com.janocare.professional.application.queries.professional.FindAllProfessionalsQuery;
import com.janocare.professional.application.queries.professional.FindProfessionalByIdQuery;
import com.janocare.professional.application.queries.professional.FindProfessionalByUserIdQuery;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.UUID;

@Path("/professionals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalController {

    @Inject
    ProfessionalHandler handler;

    // =====================================================
    // INTERNAL — called by auth-service only
    // =====================================================

    @POST
    @Path("/internal")
    @PermitAll
    public Response createFromAuthService(
            @Valid CreateProfessionalRequest req) {

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.createFromAuthService(
                                new CreateProfessionalCommand(
                                        req.userId,
                                        req.professionTypeId
                                )
                        ),
                        "Professional profile created"
                ))
                .build();
    }

    // =====================================================
    // PUBLIC — browse professionals
    // =====================================================

    @GET
    @PermitAll
    public Response findAll(
            @QueryParam("page")     Integer page,
            @QueryParam("size")     Integer size,
            @QueryParam("search")   String search,
            @QueryParam("status")   String status,
            @QueryParam("verified") Boolean verified
    ) {
        FindAllProfessionalsQuery query =
                new FindAllProfessionalsQuery();
        query.page     = page;
        query.size     = size;
        query.search   = search;
        query.status   = status;
        query.verified = verified;

        return Response.ok(
                ApiResponse.success(handler.findAll(query))
        ).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response findById(
            @PathParam("id") UUID id) {

        return Response.ok(
                ApiResponse.success(
                        handler.findById(
                                new FindProfessionalByIdQuery(id))
                )
        ).build();
    }

    @GET
    @Path("/by-user/{userId}")
    @PermitAll
    public Response findByUserId(
            @PathParam("userId") UUID userId) {

        return Response.ok(
                ApiResponse.success(
                        handler.findByUserId(
                                new FindProfessionalByUserIdQuery(userId))
                )
        ).build();
    }

    // =====================================================
    // PROFESSIONAL — update own profile
    // =====================================================

    @PUT
    @Path("/{id}")
    @RolesAllowed("PROFESSIONAL")
    public Response update(
            @PathParam("id") UUID id,
            @Valid UpdateProfessionalRequest req,
            @Context SecurityContext securityContext) {

        UUID currentUserId = UUID.fromString(
                securityContext.getUserPrincipal().getName()
        );

        UpdateProfessionalCommand command =
                new UpdateProfessionalCommand();
        command.professionalId          = id;
        command.requestingUserId        = currentUserId;
        command.bio                     = req.bio;
        command.practicingFrom          = req.practicingFrom;
        command.consultationFee         = req.consultationFee;
        command.bookingFee              = req.bookingFee;
        command.instantConsultationFee  = req.instantConsultationFee;
        command.inpersonEnabled         = req.inpersonEnabled;
        command.onlineConsultationEnabled = req.onlineConsultationEnabled;
        command.instantCallEnabled      = req.instantCallEnabled;

        return Response.ok(
                ApiResponse.success(
                        handler.update(command),
                        "Profile updated successfully"
                )
        ).build();
    }

    // =====================================================
    // ADMIN — approve / reject professional
    // Accountability pattern: admin manages knowledge level
    // =====================================================

    @PUT
    @Path("/{id}/approve")
    @RolesAllowed("ADMIN")
    public Response approveProfessional(
            @PathParam("id") UUID id,
            @Context SecurityContext securityContext) {

        UUID adminUserId = UUID.fromString(
                securityContext.getUserPrincipal().getName()
        );

        return Response.ok(
                ApiResponse.success(
                        handler.approve(
                                new ApproveProfessionalCommand(
                                        id, adminUserId)
                        ),
                        "Professional approved successfully"
                )
        ).build();
    }

    @PUT
    @Path("/{id}/reject")
    @RolesAllowed("ADMIN")
    public Response rejectProfessional(
            @PathParam("id") UUID id) {

        return Response.ok(
                ApiResponse.success(
                        handler.reject(
                                new RejectProfessionalCommand(id)
                        ),
                        "Professional rejected"
                )
        ).build();
    }
}