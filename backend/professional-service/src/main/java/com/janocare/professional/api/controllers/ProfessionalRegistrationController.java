package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalregistration.CreateProfessionalRegistrationRequest;
import com.janocare.professional.api.requests.professionalregistration.UpdateProfessionalRegistrationRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalregistration.CreateProfessionalRegistrationCommand;
import com.janocare.professional.application.commands.professionalregistration.DeleteProfessionalRegistrationCommand;
import com.janocare.professional.application.commands.professionalregistration.UpdateProfessionalRegistrationCommand;
import com.janocare.professional.application.handlers.ProfessionalRegistrationHandler;
import com.janocare.professional.application.queries.professionalregistration.FindAllProfessionalRegistrationsQuery;
import com.janocare.professional.application.queries.professionalregistration.FindProfessionalRegistrationByIdQuery;
import com.janocare.professional.application.queries.professionalregistration.FindProfessionalRegistrationsByProfessionalIdQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-registrations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalRegistrationController {

    @Inject
    ProfessionalRegistrationHandler handler;

    @POST
    public Response create(CreateProfessionalRegistrationRequest req) {
        CreateProfessionalRegistrationCommand command =
                new CreateProfessionalRegistrationCommand();

        command.professionalId = req.professionalId;
        command.registrationName = req.registrationName;
        command.registrationDate = req.registrationDate;
        command.certificatePhoto = req.certificatePhoto;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Professional registration created"
                ))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalRegistrationRequest req
    ) {
        UpdateProfessionalRegistrationCommand command =
                new UpdateProfessionalRegistrationCommand();

        command.registrationId = id;
        command.registrationName = req.registrationName;
        command.registrationDate = req.registrationDate;
        command.certificatePhoto = req.certificatePhoto;

        return Response.ok(ApiResponse.success(
                handler.update(command),
                "Professional registration updated"
        )).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        handler.delete(new DeleteProfessionalRegistrationCommand(id));

        return Response.ok(ApiResponse.success(
                null,
                "Professional registration deleted"
        )).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(ApiResponse.success(
                handler.findById(new FindProfessionalRegistrationByIdQuery(id))
        )).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    public Response findByProfessionalId(
            @PathParam("professionalId") UUID professionalId
    ) {
        return Response.ok(ApiResponse.success(
                handler.findByProfessionalId(
                        new FindProfessionalRegistrationsByProfessionalIdQuery(professionalId)
                )
        )).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {
        FindAllProfessionalRegistrationsQuery query =
                new FindAllProfessionalRegistrationsQuery();

        query.page = page;
        query.size = size;
        query.search = search;

        return Response.ok(ApiResponse.success(
                handler.findAll(query)
        )).build();
    }
}