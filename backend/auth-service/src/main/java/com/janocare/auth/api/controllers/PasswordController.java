package com.janocare.auth.api.controllers;

import com.janocare.auth.api.requests.password.ChangeMyPasswordRequest;
import com.janocare.auth.api.requests.password.ForgotPasswordRequest;
import com.janocare.auth.api.requests.password.ResetPasswordRequest;
import com.janocare.auth.api.requests.password.SetPasswordRequest;
import com.janocare.auth.api.responses.password.ChangePasswordResponse;
import com.janocare.auth.application.commands.password.ChangeMyPasswordCommand;
import com.janocare.auth.application.commands.password.ForgotPasswordCommand;
import com.janocare.auth.application.commands.password.ResetPasswordCommand;
import com.janocare.auth.application.commands.password.SetPasswordCommand;
import com.janocare.auth.application.handlers.AuthCommandHandler;
import com.janocare.auth.infrastructure.security.CookieService;

import io.vertx.ext.web.RoutingContext;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/password")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PasswordController {

    @Inject
    AuthCommandHandler handler;

    @Inject
    CookieService cookieService;

    @POST
    @Path("/forgot")
    public Response forgotPassword(
            ForgotPasswordRequest req
    ) {

        ForgotPasswordCommand command =
                new ForgotPasswordCommand();

        command.email = req.identifier;

        return Response.ok(
                handler.forgotPassword(command)
        ).build();
    }

    @POST
    @Path("/reset")
    public Response resetPassword(
            ResetPasswordRequest req
    ) {

        ResetPasswordCommand command =
                new ResetPasswordCommand();

        command.email = req.identifier;
        command.otp = req.otp;
        command.newPassword = req.newPassword;
        command.confirmPassword = req.confirmPassword;

        return Response.ok(
                handler.resetPassword(command)
        ).build();
    }

    @POST
    @Path("/set")
    public Response setPassword(
            SetPasswordRequest req,
            RoutingContext ctx
    ) {

        SetPasswordCommand command =
                new SetPasswordCommand();

        command.email = req.identifier;
        command.password = req.password;
        command.confirmPassword = req.confirmPassword;

        ChangePasswordResponse resp =
                handler.setPassword(command);

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
    @Path("/change-my-password")
    @RolesAllowed({"PATIENT", "ADMIN", "PROFESSIONAL"})
    public Response changeMyPassword(
            ChangeMyPasswordRequest req,
            RoutingContext ctx
    ) {

        ChangeMyPasswordCommand command =
                new ChangeMyPasswordCommand();

        command.email = req.identifier;
        command.previousPassword = req.previousPassword;
        command.newPassword = req.newPassword;
        command.confirmPassword = req.confirmPassword;

        ChangePasswordResponse resp =
                handler.changeMyPassword(command);

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