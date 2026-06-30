package com.janocare.professional.api.controllers;

import com.janocare.professional.api.requests.professionalreview.CreateProfessionalReviewRequest;
import com.janocare.professional.api.requests.professionalreview.UpdateProfessionalReviewRequest;
import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.application.commands.professionalreview.CreateProfessionalReviewCommand;
import com.janocare.professional.application.commands.professionalreview.DeleteProfessionalReviewCommand;
import com.janocare.professional.application.commands.professionalreview.UpdateProfessionalReviewCommand;
import com.janocare.professional.application.handlers.ProfessionalReviewHandler;
import com.janocare.professional.application.queries.professionalreview.FindAllProfessionalReviewsQuery;
import com.janocare.professional.application.queries.professionalreview.FindProfessionalReviewByIdQuery;
import com.janocare.professional.application.queries.professionalreview.FindProfessionalReviewsByProfessionalIdQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/professional-reviews")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessionalReviewController {

    @Inject
    ProfessionalReviewHandler handler;

    @POST
    public Response create(CreateProfessionalReviewRequest req) {
        CreateProfessionalReviewCommand command =
                new CreateProfessionalReviewCommand();

        command.professionalId = req.professionalId;
        command.userId = req.userId;
        command.appointmentBookingId = req.appointmentBookingId;
        command.reviewAnonymous = req.reviewAnonymous;
        command.waitTimeRating = req.waitTimeRating;
        command.mannerRating = req.mannerRating;
        command.review = req.review;
        command.overallRating = req.overallRating;
        command.doctorRecommended = req.doctorRecommended;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        handler.create(command),
                        "Professional review created"
                ))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateProfessionalReviewRequest req
    ) {
        UpdateProfessionalReviewCommand command =
                new UpdateProfessionalReviewCommand();

        command.reviewId = id;
        command.reviewAnonymous = req.reviewAnonymous;
        command.waitTimeRating = req.waitTimeRating;
        command.mannerRating = req.mannerRating;
        command.review = req.review;
        command.overallRating = req.overallRating;
        command.doctorRecommended = req.doctorRecommended;

        return Response.ok(ApiResponse.success(
                handler.update(command),
                "Professional review updated"
        )).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(ApiResponse.success(
                handler.findById(new FindProfessionalReviewByIdQuery(id))
        )).build();
    }

    @GET
    @Path("/professional/{professionalId}")
    public Response findByProfessionalId(
            @PathParam("professionalId") UUID professionalId
    ) {
        return Response.ok(ApiResponse.success(
                handler.findByProfessionalId(
                        new FindProfessionalReviewsByProfessionalIdQuery(professionalId)
                )
        )).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") Integer page,
            @QueryParam("size") Integer size,
            @QueryParam("search") String search
    ) {
        FindAllProfessionalReviewsQuery query =
                new FindAllProfessionalReviewsQuery();

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
        handler.delete(new DeleteProfessionalReviewCommand(id));

        return Response.ok(ApiResponse.success(
                null,
                "Professional review deleted"
        )).build();
    }
}