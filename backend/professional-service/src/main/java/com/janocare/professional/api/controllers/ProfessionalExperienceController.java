package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalexperience.CreateProfessionalExperienceRequest;
import com.janocare.professional.api.requests.professionalexperience.UpdateProfessionalExperienceRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalexperience.CreateProfessionalExperienceCommand;
import com.janocare.professional.application.commands.professionalexperience.DeleteProfessionalExperienceCommand;
import com.janocare.professional.application.commands.professionalexperience.UpdateProfessionalExperienceCommand;
import com.janocare.professional.application.handlers.ProfessionalExperienceHandler;
import com.janocare.professional.application.queries.professionalexperience.FindAllProfessionalExperiencesQuery;
import com.janocare.professional.application.queries.professionalexperience.FindProfessionalExperienceByIdQuery;
import com.janocare.professional.application.queries.professionalexperience.FindProfessionalExperiencesByProfessionalIdQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-experiences")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PROFESSIONAL")
public class ProfessionalExperienceController {

    @Inject
    ProfessionalExperienceHandler handler;

    @POST
    public Response create(CreateProfessionalExperienceRequest req) {
        CreateProfessionalExperienceCommand command =
                new CreateProfessionalExperienceCommand();

        command.professionalId = req.professionalId;
        command.experience = req.experience;
        command.specialization = req.specialization;
        command.place = req.place;
        command.startYear = req.startYear;
        command.endYear = req.endYear;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                handler.create(command),
                                "Professional experience created"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalExperienceRequest req
    ) {
        UpdateProfessionalExperienceCommand command =
                new UpdateProfessionalExperienceCommand();

        command.experienceId = id;
        command.experience = req.experience;
        command.specialization = req.specialization;
        command.place = req.place;
        command.startYear = req.startYear;
        command.endYear = req.endYear;

        return Response.ok(
                ApiResponse.success(
                        handler.update(command),
                        "Professional experience updated"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        DeleteProfessionalExperienceCommand command =
                new DeleteProfessionalExperienceCommand(id);

        handler.delete(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "Professional experience deleted"
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        FindProfessionalExperienceByIdQuery query =
                new FindProfessionalExperienceByIdQuery(id);

        return Response.ok(
                ApiResponse.success(
                        handler.findById(query)
                )
        ).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    public Response findByProfessionalId(
            @PathParam("professionalId") UUID professionalId
    ) {
        FindProfessionalExperiencesByProfessionalIdQuery query =
                new FindProfessionalExperiencesByProfessionalIdQuery(professionalId);

        return Response.ok(
                ApiResponse.success(
                        handler.findByProfessionalId(query)
                )
        ).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {
        FindAllProfessionalExperiencesQuery query =
                new FindAllProfessionalExperiencesQuery();

        query.page = page;
        query.size = size;
        query.search = search;

        return Response.ok(
                ApiResponse.success(
                        handler.findAll(query)
                )
        ).build();
    }
}