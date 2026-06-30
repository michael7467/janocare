package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalachievement.CreateProfessionalAchievementRequest;
import com.janocare.professional.api.requests.professionalachievement.UpdateProfessionalAchievementRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalachievement.CreateProfessionalAchievementCommand;
import com.janocare.professional.application.commands.professionalachievement.DeleteProfessionalAchievementCommand;
import com.janocare.professional.application.commands.professionalachievement.UpdateProfessionalAchievementCommand;
import com.janocare.professional.application.handlers.ProfessionalAchievementHandler;
import com.janocare.professional.application.queries.professionalachievement.FindAllProfessionalAchievementsQuery;
import com.janocare.professional.application.queries.professionalachievement.FindProfessionalAchievementByIdQuery;
import com.janocare.professional.application.queries.professionalachievement.FindProfessionalAchievementsByProfessionalIdQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import java.util.UUID;

@Path("/professional-achievements")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalAchievementController {

    @Inject
    ProfessionalAchievementHandler handler;
    @Inject
    SecurityIdentity identity;
    

    @POST
    @RolesAllowed("PROFESSIONAL")
    public Response create(CreateProfessionalAchievementRequest req) {

        UUID userId = UUID.fromString(identity.getPrincipal().getName());

        CreateProfessionalAchievementCommand command =
                new CreateProfessionalAchievementCommand();

        command.userId = userId;
        command.awardOrRecognitionName = req.awardOrRecognitionName;
        command.awardDescription = req.awardDescription;
        command.awardYear = req.awardYear;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                handler.create(command),
                                "Professional achievement created"
                        )
                )
                .build();
 }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalAchievementRequest req
    ) {
        UpdateProfessionalAchievementCommand command =
                new UpdateProfessionalAchievementCommand();

        command.achievementId = id;
        command.awardOrRecognitionName = req.awardOrRecognitionName;
        command.awardDescription = req.awardDescription;
        command.awardYear = req.awardYear;

        return Response.ok(
                ApiResponse.success(
                        handler.update(command),
                        "Professional achievement updated"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        DeleteProfessionalAchievementCommand command =
                new DeleteProfessionalAchievementCommand(id);

        handler.delete(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "Professional achievement deleted"
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        FindProfessionalAchievementByIdQuery query =
                new FindProfessionalAchievementByIdQuery(id);

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
        FindProfessionalAchievementsByProfessionalIdQuery query =
                new FindProfessionalAchievementsByProfessionalIdQuery(professionalId);

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
        FindAllProfessionalAchievementsQuery query =
                new FindAllProfessionalAchievementsQuery();

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