package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professional.CreateProfessionalRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professional.CreateProfessionalCommand;
import com.janocare.professional.application.handlers.ProfessionalHandler;
import com.janocare.professional.application.queries.professional.FindAllProfessionalsQuery;
import com.janocare.professional.application.queries.professional.FindProfessionalByIdQuery;
import com.janocare.professional.application.queries.professional.FindProfessionalByUserIdQuery;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import com.janocare.professional.application.commands.professional.ApproveProfessionalCommand;
import java.util.UUID;

@Path("/professionals")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalController {

    @Inject
    ProfessionalHandler handler;

    /**
     * Called by auth-service after a PROFESSIONAL user registers.
     */
    @POST
    @Path("/internal")
    public Response createFromAuthService(CreateProfessionalRequest req) {

        CreateProfessionalCommand command =
                new CreateProfessionalCommand(
                        req.userId,
                        req.professionTypeId
                );

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.createFromAuthService(command),
                        "Professional profile created"
                ))
                .build();
    }

    @PermitAll
    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search,
            @QueryParam("status") String status,
            @QueryParam("verified") Boolean verified
    ) {

        FindAllProfessionalsQuery query =
                new FindAllProfessionalsQuery();

        query.page = page;
        query.size = size;
        query.search = search;
        query.status = status;
        query.verified = verified;

        return Response.ok(
                ApiResponse.success(handler.findAll(query))
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {

        FindProfessionalByIdQuery query =
                new FindProfessionalByIdQuery(id);

        return Response.ok(
                ApiResponse.success(handler.findById(query))
        ).build();
    }

   @PUT
@Path("/{id}/approve")
@RolesAllowed("ADMIN")
public Response approveProfessional(
        @PathParam("id") UUID id,
        @Context SecurityContext securityContext
) {
    UUID adminUserId = UUID.fromString(
            securityContext.getUserPrincipal().getName()
    );

    handler.approveProfessional(
            new ApproveProfessionalCommand(id, adminUserId)
    );

    return Response.ok(
            ApiResponse.success(null, "Professional approved successfully")
    ).build();
}

@PUT
@Path("/{id}/reject")
@RolesAllowed("ADMIN")
public Response rejectProfessional(
        @PathParam("id") UUID id
) {
    handler.rejectProfessional(
            new RejectProfessionalCommand(id)
    );

    return Response.ok(
            ApiResponse.success(null, "Professional rejected")
    ).build();
}

    @GET
    @Path("/by-user/{userId}")
    public Response findByUserId(@PathParam("userId") UUID userId) {

        FindProfessionalByUserIdQuery query =
                new FindProfessionalByUserIdQuery(userId);

        return Response.ok(
                ApiResponse.success(handler.findByUserId(query))
        ).build();
    }
}