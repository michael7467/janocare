package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalservice.CreateProfessionalServiceRequest;
import com.janocare.professional.api.requests.professionalservice.UpdateProfessionalServiceRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalservice.CreateProfessionalServiceCommand;
import com.janocare.professional.application.commands.professionalservice.DeleteProfessionalServiceCommand;
import com.janocare.professional.application.commands.professionalservice.UpdateProfessionalServiceCommand;
import com.janocare.professional.application.handlers.ProfessionalServiceHandler;
import com.janocare.professional.application.queries.professionalservice.FindAllProfessionalServicesQuery;
import com.janocare.professional.application.queries.professionalservice.FindProfessionalServiceByIdQuery;
import com.janocare.professional.application.queries.professionalservice.FindProfessionalServicesByProfessionalIdQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalServiceController {

    @Inject
    ProfessionalServiceHandler handler;

    @POST
    public Response create(CreateProfessionalServiceRequest req) {
        CreateProfessionalServiceCommand command =
                new CreateProfessionalServiceCommand();

        command.professionalId = req.professionalId;
        command.serviceName = req.serviceName;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Professional service created"
                ))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalServiceRequest req
    ) {
        UpdateProfessionalServiceCommand command =
                new UpdateProfessionalServiceCommand();

        command.serviceId = id;
        command.serviceName = req.serviceName;

        return Response.ok(ApiResponse.success(
                handler.update(command),
                "Professional service updated"
        )).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(ApiResponse.success(
                handler.findById(new FindProfessionalServiceByIdQuery(id))
        )).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    public Response findByProfessionalId(
            @PathParam("professionalId") UUID professionalId
    ) {
        return Response.ok(ApiResponse.success(
                handler.findByProfessionalId(
                        new FindProfessionalServicesByProfessionalIdQuery(professionalId)
                )
        )).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {
        FindAllProfessionalServicesQuery query =
                new FindAllProfessionalServicesQuery();

        query.page = page;
        query.size = size;
        query.search = search;

        return Response.ok(ApiResponse.success(
                handler.findAll(query)
        )).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        handler.delete(new DeleteProfessionalServiceCommand(id));

        return Response.ok(
                ApiResponse.success(null, "Professional service deleted")
        ).build();
    }
}