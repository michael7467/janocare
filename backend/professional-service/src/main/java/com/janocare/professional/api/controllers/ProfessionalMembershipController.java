package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalmembership.CreateProfessionalMembershipRequest;
import com.janocare.professional.api.requests.professionalmembership.UpdateProfessionalMembershipRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalmembership.CreateProfessionalMembershipCommand;
import com.janocare.professional.application.commands.professionalmembership.DeleteProfessionalMembershipCommand;
import com.janocare.professional.application.commands.professionalmembership.UpdateProfessionalMembershipCommand;
import com.janocare.professional.application.handlers.ProfessionalMembershipHandler;
import com.janocare.professional.application.queries.professionalmembership.FindAllProfessionalMembershipsQuery;
import com.janocare.professional.application.queries.professionalmembership.FindProfessionalMembershipByIdQuery;
import com.janocare.professional.application.queries.professionalmembership.FindProfessionalMembershipsByProfessionalIdQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-memberships")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PROFESSIONAL")
public class ProfessionalMembershipController {

    @Inject
    ProfessionalMembershipHandler handler;

    @POST
    public Response create(CreateProfessionalMembershipRequest req) {
        CreateProfessionalMembershipCommand command =
                new CreateProfessionalMembershipCommand();

        command.professionalId = req.professionalId;
        command.membershipName = req.membershipName;
        command.membershipDescription = req.membershipDescription;
        command.membershipYear = req.membershipYear;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Professional membership created"
                ))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalMembershipRequest req
    ) {
        UpdateProfessionalMembershipCommand command =
                new UpdateProfessionalMembershipCommand();

        command.membershipId = id;
        command.membershipName = req.membershipName;
        command.membershipDescription = req.membershipDescription;
        command.membershipYear = req.membershipYear;

        return Response.ok(ApiResponse.success(
                handler.update(command),
                "Professional membership updated"
        )).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        handler.delete(new DeleteProfessionalMembershipCommand(id));

        return Response.ok(ApiResponse.success(
                null,
                "Professional membership deleted"
        )).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(ApiResponse.success(
                handler.findById(new FindProfessionalMembershipByIdQuery(id))
        )).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    public Response findByProfessionalId(
            @PathParam("professionalId") UUID professionalId
    ) {
        return Response.ok(ApiResponse.success(
                handler.findByProfessionalId(
                        new FindProfessionalMembershipsByProfessionalIdQuery(professionalId)
                )
        )).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {
        FindAllProfessionalMembershipsQuery query =
                new FindAllProfessionalMembershipsQuery();

        query.page = page;
        query.size = size;
        query.search = search;

        return Response.ok(ApiResponse.success(
                handler.findAll(query)
        )).build();
    }
}