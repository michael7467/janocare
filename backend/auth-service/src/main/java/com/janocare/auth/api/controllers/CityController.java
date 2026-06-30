package com.janocare.auth.api.controllers;

import com.janocare.auth.api.requests.location.CreateCityRequest;
import com.janocare.auth.api.requests.location.UpdateCityRequest;
import com.janocare.auth.api.responses.ApiResponse;

import com.janocare.auth.application.commands.location.CreateCityCommand;
import com.janocare.auth.application.commands.location.DeleteCityCommand;
import com.janocare.auth.application.commands.location.UpdateCityCommand;

import com.janocare.auth.application.handlers.LocationHandler;

import com.janocare.auth.application.queries.location.FindAllCitiesQuery;
import com.janocare.auth.application.queries.location.FindCityByIdQuery;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/cities")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CityController {

    @Inject
    LocationHandler handler;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(CreateCityRequest req) {

        CreateCityCommand command =
                new CreateCityCommand();

        command.countryId = req.countryId;
        command.stateId = req.stateId;
        command.cityName = req.cityName;
        command.active = req.active;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                handler.createCity(command),
                                "City created successfully"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(
            @PathParam("id") UUID id,
            UpdateCityRequest req
    ) {

        UpdateCityCommand command =
                new UpdateCityCommand();

        command.cityId = id;
        command.countryId = req.countryId;
        command.stateId = req.stateId;
        command.cityName = req.cityName;
        command.active = req.active;

        return Response.ok(
                ApiResponse.success(
                        handler.updateCity(command),
                        "City updated successfully"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(
            @PathParam("id") UUID id
    ) {

        DeleteCityCommand command =
                new DeleteCityCommand(id);

        handler.deleteCity(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "City deleted successfully"
                )
        ).build();
    }

    @GET
    public Response findAll(
            @QueryParam("countryId") UUID countryId,
            @QueryParam("stateId") UUID stateId,
            @QueryParam("search") String search,
            @QueryParam("active") Boolean active
    ) {

        FindAllCitiesQuery query =
                new FindAllCitiesQuery();

        query.countryId = countryId;
        query.stateId = stateId;
        query.search = search;
        query.active = active;

        return Response.ok(
                ApiResponse.success(
                        handler.findAllCities(query)
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(
            @PathParam("id") UUID id
    ) {

        FindCityByIdQuery query =
                new FindCityByIdQuery(id);

        return Response.ok(
                ApiResponse.success(
                        handler.findCityById(query)
                )
        ).build();
    }
}