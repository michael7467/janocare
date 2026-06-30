package com.janocare.auth.api.controllers;

import com.janocare.auth.api.requests.profile.ProfilePictureUploadRequest;
import com.janocare.auth.api.requests.profile.UpdateProfileRequest;
import com.janocare.auth.api.responses.ApiResponse;
import com.janocare.auth.api.responses.user.UserResponse;

import com.janocare.auth.application.commands.profile.UpdateProfileCommand;
import com.janocare.auth.application.commands.profile.UpdateProfilePictureCommand;

import com.janocare.auth.application.handlers.AuthCommandHandler;

import com.janocare.auth.application.queries.profile.GetMyProfileQuery;

import io.quarkus.security.identity.SecurityIdentity;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileController {

    @Inject
    AuthCommandHandler handler;

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/me")
    @RolesAllowed({
            "PATIENT",
            "ADMIN",
            "PROFESSIONAL"
    })
    public Response myProfile() {

        UUID userId =
                UUID.fromString(
                        identity.getPrincipal().getName()
                );

        UserResponse resp =
                handler.getProfile(
                        new GetMyProfileQuery(userId)
                );

        return Response.ok(
                ApiResponse.success(resp)
        ).build();
    }

    @PUT
    @Path("/me")
    @RolesAllowed({
            "PATIENT",
            "ADMIN",
            "PROFESSIONAL"
    })
    public Response updateMyProfile(
            UpdateProfileRequest req
    ) {

        UUID userId =
                UUID.fromString(
                        identity.getPrincipal().getName()
                );

        UpdateProfileCommand command =
                new UpdateProfileCommand();

        command.userId = userId;
        command.firstName = req.firstName;
        command.lastName = req.lastName;
        command.profilePic = req.profilePic;
        command.gender = req.gender;
        command.countryId = req.countryId;
        command.stateId = req.stateId;
        command.cityId = req.cityId;

        UserResponse resp =
                handler.updateProfile(command);

        return Response.ok(
                ApiResponse.success(
                        resp,
                        "Profile updated successfully"
                )
        ).build();
    }

    @PUT
    @Path("/me/profile-picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({
            "PATIENT",
            "ADMIN",
            "PROFESSIONAL"
    })
    public Response updateProfilePicture(
            @BeanParam ProfilePictureUploadRequest req
    ) {

        UUID userId =
                UUID.fromString(
                        identity.getPrincipal().getName()
                );

        UpdateProfilePictureCommand command =
                new UpdateProfilePictureCommand();

        command.userId = userId;
        command.file = req.file;

        UserResponse resp =
                handler.updateProfilePicture(command);

        return Response.ok(
                ApiResponse.success(
                        resp,
                        "Profile picture updated successfully"
                )
        ).build();
    }
}