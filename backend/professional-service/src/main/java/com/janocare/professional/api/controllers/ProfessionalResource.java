package api.controllers;

import api.requests.*;
import api.responses.ProfessionalResponse;
import application.commands.*;
import application.dto.ProfessionalDTO;
import application.handlers.*;
import application.queries.GetProfessionalByIdQuery;
import application.queries.SearchProfessionalsQuery;
import jakarta.validation.Valid;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/professionals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessionalResource {

    @Inject
    RegisterProfessionalHandler registerProfessionalHandler;

    @Inject
    ApproveProfessionalHandler approveProfessionalHandler;

    @Inject
    AddSpecializationHandler addSpecializationHandler;

    @Inject
    AddReviewHandler addReviewHandler;

    @Inject
    GetProfessionalByIdHandler getProfessionalByIdHandler;

    @Inject
    SearchProfessionalsHandler searchProfessionalsHandler;



    @POST
    public Response registerProfessional(@Valid RegisterProfessionalRequest request) {
        RegisterProfessionalCommand command = new RegisterProfessionalCommand();
        command.firstName = request.firstName;
        command.lastName = request.lastName;

        UUID id = registerProfessionalHandler.handle(command);
        return Response.created(URI.create("/professionals/" + id)).build();
    }

    @PUT
    @Path("/{id}/approve")
    public Response approveProfessional(@PathParam("id") UUID id) {
        ApproveProfessionalCommand command = new ApproveProfessionalCommand();
        command.professionalId = id;

        approveProfessionalHandler.handle(command);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/specializations")
    public Response addSpecialization(@PathParam("id") UUID id, AddSpecializationRequest request) {
        AddSpecializationCommand command = new AddSpecializationCommand();
        command.professionalId = id;
        command.specializationName = request.specializationName;

        addSpecializationHandler.handle(command);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/reviews")
    public Response addReview(@PathParam("id") UUID id, AddReviewRequest request) {
        AddReviewCommand command = new AddReviewCommand();
        command.professionalId = id;
        command.rating = request.rating;
        command.comment = request.comment;

        addReviewHandler.handle(command);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    public Response getProfessionalById(@PathParam("id") UUID id) {
        GetProfessionalByIdQuery query = new GetProfessionalByIdQuery();
        query.id = id;

        ProfessionalDTO dto = getProfessionalByIdHandler.handle(query);
        return Response.ok(toResponse(dto)).build();
    }

    @GET
    public Response searchProfessionals(@QueryParam("specialty") String specialty) {
        SearchProfessionalsQuery query = new SearchProfessionalsQuery();
        query.specialty = specialty;

        List<ProfessionalDTO> dtos = searchProfessionalsHandler.handle(query);
        List<ProfessionalResponse> responses = dtos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return Response.ok(responses).build();
    }

    private ProfessionalResponse toResponse(ProfessionalDTO dto) {
        ProfessionalResponse response = new ProfessionalResponse();
        response.id = dto.id;
        response.fullName = dto.fullName;
        response.approvalStatus = dto.approvalStatus;
        response.specializations = dto.specializations;
        response.reviews = dto.reviews;
        return response;
    }
}
