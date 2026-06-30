package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalinfo.CreateProfessionalInfoRequest;
import com.janocare.professional.api.requests.professionalinfo.UpdateProfessionalInfoRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalinfo.CreateProfessionalInfoCommand;
import com.janocare.professional.application.commands.professionalinfo.DeleteProfessionalInfoCommand;
import com.janocare.professional.application.commands.professionalinfo.UpdateProfessionalInfoCommand;
import com.janocare.professional.application.handlers.ProfessionalInfoHandler;
import com.janocare.professional.application.queries.professionalinfo.FindAllProfessionalInfosQuery;
import com.janocare.professional.application.queries.professionalinfo.FindProfessionalInfoByIdQuery;
import com.janocare.professional.application.queries.professionalinfo.FindProfessionalInfosByProfessionalIdQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-infos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalInfoController {

    @Inject
    ProfessionalInfoHandler handler;

    @POST
    public Response create(CreateProfessionalInfoRequest req) {
        CreateProfessionalInfoCommand command = new CreateProfessionalInfoCommand();

        command.professionalId = req.professionalId;
        command.institutionName = req.institutionName;
        command.officeNumber = req.officeNumber;
        command.daysOfWeek = req.daysOfWeek;
        command.startTime = req.startTime;
        command.endTime = req.endTime;
        command.available = req.available;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Professional info created"
                ))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, UpdateProfessionalInfoRequest req) {
        UpdateProfessionalInfoCommand command = new UpdateProfessionalInfoCommand();

        command.infoId = id;
        command.institutionName = req.institutionName;
        command.officeNumber = req.officeNumber;
        command.daysOfWeek = req.daysOfWeek;
        command.startTime = req.startTime;
        command.endTime = req.endTime;
        command.available = req.available;

        return Response.ok(ApiResponse.success(
                handler.update(command),
                "Professional info updated"
        )).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        handler.delete(new DeleteProfessionalInfoCommand(id));

        return Response.ok(ApiResponse.success(
                null,
                "Professional info deleted"
        )).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(ApiResponse.success(
                handler.findById(new FindProfessionalInfoByIdQuery(id))
        )).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    public Response findByProfessionalId(@PathParam("professionalId") UUID professionalId) {
        return Response.ok(ApiResponse.success(
                handler.findByProfessionalId(
                        new FindProfessionalInfosByProfessionalIdQuery(professionalId)
                )
        )).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {
        FindAllProfessionalInfosQuery query = new FindAllProfessionalInfosQuery();

        query.page = page;
        query.size = size;
        query.search = search;

        return Response.ok(ApiResponse.success(
                handler.findAll(query)
        )).build();
    }
}