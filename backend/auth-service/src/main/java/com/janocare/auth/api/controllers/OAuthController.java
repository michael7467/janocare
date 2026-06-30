package com.janocare.auth.api.controllers;

import com.janocare.auth.api.requests.oauth.GoogleSignInRequest;
import com.janocare.auth.api.responses.oauth.GoogleSignInResponse;
import com.janocare.auth.application.commands.oauth.GoogleSignInCommand;
import com.janocare.auth.application.handlers.AuthCommandHandler;
import com.janocare.auth.infrastructure.security.CookieService;

import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/oauth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OAuthController {

    @Inject
    AuthCommandHandler handler;

    @Inject
    CookieService cookieService;

    @GET
    @Path("/google/uri")
    public Response googleUri() {

        return Response.ok(
                handler.getGoogleRedirectUri()
        ).build();
    }

    @POST
    @Path("/google/signin")
    public Response googleSignIn(
            GoogleSignInRequest req,
            RoutingContext ctx
    ) {

        GoogleSignInCommand command =
                new GoogleSignInCommand();

        command.token = req.idToken;

        GoogleSignInResponse resp =
                handler.googleSignIn(command);

        if (resp.success && resp.refresh_token != null) {
            ctx.response().addCookie(
                    cookieService.createRefreshTokenCookie(
                            resp.refresh_token
                    )
            );

            resp.refresh_token = null;
        }

        return Response.ok(resp).build();
    }
}