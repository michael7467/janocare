package com.janocare.auth.api.controllers;

import com.janocare.auth.api.requests.location.CreateCountryRequest;
import com.janocare.auth.api.requests.location.UpdateCountryRequest;
import com.janocare.auth.api.responses.ApiResponse;

import com.janocare.auth.application.commands.location.CreateCountryCommand;
import com.janocare.auth.application.commands.location.DeleteCountryCommand;
import com.janocare.auth.application.commands.location.UpdateCountryCommand;

import com.janocare.auth.application.handlers.LocationHandler;

import com.janocare.auth.application.queries.location.FindAllCountriesQuery;
import com.janocare.auth.application.queries.location.FindCountryByIdQuery;
import jakarta.validation.Valid;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/countries")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CountryController {

    @Inject
    LocationHandler handler;

    @POST
    @RolesAllowed("ADMIN")
    public Response create(@Valid CreateCountryRequest req) {

        CreateCountryCommand command =
                new CreateCountryCommand();

        command.countryName = req.countryName;
        command.phonePrefix = req.phonePrefix;
        command.active = req.active;

        return Response.status(Response.Status.CREATED)
                .entity(
                        ApiResponse.success(
                                handler.createCountry(command),
                                "Country created successfully"
                        )
                )
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response update(
            @PathParam("id") UUID id,
           @Valid UpdateCountryRequest req
    ) {

        UpdateCountryCommand command =
                new UpdateCountryCommand();

        command.countryId = id;
        command.countryName = req.countryName;
        command.phonePrefix = req.phonePrefix;
        command.active = req.active;

        return Response.ok(
                ApiResponse.success(
                        handler.updateCountry(command),
                        "Country updated successfully"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(
            @PathParam("id") UUID id
    ) {

        DeleteCountryCommand command =
                new DeleteCountryCommand(id);

        handler.deleteCountry(command);

        return Response.ok(
                ApiResponse.success(
                        null,
                        "Country deleted successfully"
                )
        ).build();
    }

    @GET
    public Response findAll(
            @QueryParam("search") String search,
            @QueryParam("active") Boolean active
    ) {

        FindAllCountriesQuery query =
                new FindAllCountriesQuery();

        query.search = search;
        query.active = active;

        return Response.ok(
                ApiResponse.success(
                        handler.findAllCountries(query)
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(
            @PathParam("id") UUID id
    ) {

        FindCountryByIdQuery query =
                new FindCountryByIdQuery(id);

        return Response.ok(
                ApiResponse.success(
                        handler.findCountryById(query)
                )
        ).build();
    }
}