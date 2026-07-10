package com.janocare.auth.api.controllers;

import com.janocare.auth.api.requests.auth.LoginRequest;
import com.janocare.auth.api.requests.auth.RefreshTokenRequest;
import com.janocare.auth.api.requests.auth.RegisterRequest;
import com.janocare.auth.api.responses.auth.LoginResponse;
import com.janocare.auth.api.responses.auth.TokenResponse;
import com.janocare.auth.application.commands.auth.LoginCommand;
import com.janocare.auth.application.commands.auth.RefreshTokenCommand;
import com.janocare.auth.application.commands.auth.RegisterCommand;
import com.janocare.auth.application.handlers.AuthCommandHandler;
import com.janocare.auth.infrastructure.security.CookieService;
import jakarta.validation.Valid;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthCommandHandler handler;

    @Inject
    CookieService cookieService;

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest req) {

        RegisterCommand command = new RegisterCommand();

        command.username = req.username;
        command.email = req.email;
        command.phone = req.phone;
        command.deviceName = req.deviceName;
        command.deviceType = req.deviceType;
        command.role = req.role != null ? req.role.name() : null;
        command.professionTypeId =
                req.professionTypeId != null
                        ? req.professionTypeId.toString()
                        : null;

        return Response.status(Response.Status.CREATED)
                .entity(handler.register(command))
                .build();
    }

    @POST
    @Path("/login")
    public Response login(
          @Valid  LoginRequest req,
            RoutingContext ctx
    ) {

        LoginCommand command = new LoginCommand();

        command.identifier = req.identifier;
        command.password = req.password;

        LoginResponse resp = handler.login(command);

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

    @POST
    @Path("/refresh")
    public Response refresh(
       @Valid  RefreshTokenRequest req, RoutingContext ctx
    ) {

        String refreshToken =
                ctx.request().getCookie("refresh_token") != null
                        ? ctx.request()
                          .getCookie("refresh_token")
                          .getValue()
                        : null;

        RefreshTokenCommand command =
                new RefreshTokenCommand(refreshToken);

        TokenResponse resp =
                handler.refreshToken(command);

        if (resp.refresh_token != null) {
            ctx.response().addCookie(
                    cookieService.createRefreshTokenCookie(
                            resp.refresh_token
                    )
            );

            resp.refresh_token = null;
        }

        return Response.ok(resp).build();
    }

    @POST
    @Path("/logout")
    public Response logout(
            RoutingContext ctx
    ) {

        ctx.response().addCookie(
                cookieService.clearRefreshTokenCookie()
        );

        handler.logout();

        return Response.ok().build();
    }
}