package com.janocare.auth.api.controllers;

import com.janocare.auth.api.requests.location.CreateStateRequest;
import com.janocare.auth.api.requests.location.UpdateStateRequest;
import com.janocare.auth.api.responses.ApiResponse;

import com.janocare.auth.application.commands.location.CreateStateCommand;
import com.janocare.auth.application.commands.location.DeleteStateCommand;
import com.janocare.auth.application.commands.location.UpdateStateCommand;

import com.janocare.auth.application.handlers.LocationHandler;

import com.janocare.auth.application.queries.location.FindAllStatesQuery;
import com.janocare.auth.application.queries.location.FindStateByIdQuery;
import jakarta.validation.Valid;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/states")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StateController {

    @Inject
    LocationHandler handler;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid CreateStateRequest req) {

        CreateStateCommand command =
                new CreateStateCommand();

        command.countryId = req.countryId;
        command.stateName = req.stateName;
        command.active = req.active;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                handler.createState(command),
                                "State created successfully"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(
            @PathParam("id") UUID id,
           @Valid UpdateStateRequest req
    ) {

        UpdateStateCommand command =
                new UpdateStateCommand();

        command.stateId = id;
        command.countryId = req.countryId;
        command.stateName = req.stateName;
        command.active = req.active;

        return Response.ok(
                ApiResponse.success(
                        handler.updateState(command),
                        "State updated successfully"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(
            @PathParam("id") UUID id
    ) {

        DeleteStateCommand command =
                new DeleteStateCommand(id);

        handler.deleteState(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "State deleted successfully"
                )
        ).build();
    }

    @GET
    public Response findAll(
            @QueryParam("countryId") UUID countryId,
            @QueryParam("search") String search,
            @QueryParam("active") Boolean active
    ) {

        FindAllStatesQuery query =
                new FindAllStatesQuery();

        query.countryId = countryId;
        query.search = search;
        query.active = active;

        return Response.ok(
                ApiResponse.success(
                        handler.findAllStates(query)
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(
            @PathParam("id") UUID id
    ) {

        FindStateByIdQuery query =
                new FindStateByIdQuery(id);

        return Response.ok(
                ApiResponse.success(
                        handler.findStateById(query)
                )
        ).build();
    }
}