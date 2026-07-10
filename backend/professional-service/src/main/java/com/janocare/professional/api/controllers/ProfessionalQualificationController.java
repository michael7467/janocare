package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalqualification.CreateProfessionalQualificationRequest;
import com.janocare.professional.api.requests.professionalqualification.UpdateProfessionalQualificationRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalqualification.CreateProfessionalQualificationCommand;
import com.janocare.professional.application.commands.professionalqualification.DeleteProfessionalQualificationCommand;
import com.janocare.professional.application.commands.professionalqualification.UpdateProfessionalQualificationCommand;
import com.janocare.professional.application.handlers.ProfessionalQualificationHandler;
import com.janocare.professional.application.queries.professionalqualification.FindAllProfessionalQualificationsQuery;
import com.janocare.professional.application.queries.professionalqualification.FindProfessionalQualificationByIdQuery;
import com.janocare.professional.application.queries.professionalqualification.FindProfessionalQualificationsByProfessionalIdQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-qualifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PROFESSIONAL")
public class ProfessionalQualificationController {

    @Inject
    ProfessionalQualificationHandler handler;

    @POST
    public Response create(CreateProfessionalQualificationRequest req) {
        CreateProfessionalQualificationCommand command =
                new CreateProfessionalQualificationCommand();

        command.professionalId = req.professionalId;
        command.qualificationName = req.qualificationName;
        command.institutionName = req.institutionName;
        command.procurementYear = req.procurementYear;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Professional qualification created"
                ))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalQualificationRequest req
    ) {
        UpdateProfessionalQualificationCommand command =
                new UpdateProfessionalQualificationCommand();

        command.qualificationId = id;
        command.qualificationName = req.qualificationName;
        command.institutionName = req.institutionName;
        command.procurementYear = req.procurementYear;

        return Response.ok(ApiResponse.success(
                handler.update(command),
                "Professional qualification updated"
        )).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        handler.delete(new DeleteProfessionalQualificationCommand(id));

        return Response.ok(ApiResponse.success(
                null,
                "Professional qualification deleted"
        )).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(ApiResponse.success(
                handler.findById(new FindProfessionalQualificationByIdQuery(id))
        )).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    public Response findByProfessionalId(
            @PathParam("professionalId") UUID professionalId
    ) {
        return Response.ok(ApiResponse.success(
                handler.findByProfessionalId(
                        new FindProfessionalQualificationsByProfessionalIdQuery(professionalId)
                )
        )).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {
        FindAllProfessionalQualificationsQuery query =
                new FindAllProfessionalQualificationsQuery();

        query.page = page;
        query.size = size;
        query.search = search;

        return Response.ok(ApiResponse.success(
                handler.findAll(query)
        )).build();
    }
}