package com.janocare.auth.api.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/uploads")
public class UploadFileController {

    @GET
    @Path("/profile-pictures/{fileName}")
    public Response getProfilePicture(
            @PathParam("fileName") String fileName
    ) {
        try {
            java.nio.file.Path filePath = Paths.get(
                    "uploads",
                    "profile-pictures",
                    fileName
            );

            if (!Files.exists(filePath)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            String contentType = Files.probeContentType(filePath);

            return Response.ok(filePath.toFile())
                    .type(contentType != null ? contentType : "image/jpeg")
                    .build();

        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}