package com.janocare.auth.api.controllers;

import com.janocare.auth.api.requests.otp.ResendOtpRequest;
import com.janocare.auth.api.requests.otp.VerifyOtpRequest;
import com.janocare.auth.application.commands.otp.ResendOtpCommand;
import com.janocare.auth.application.commands.otp.VerifyOtpCommand;
import com.janocare.auth.application.handlers.AuthCommandHandler;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/otp")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OtpController {

    @Inject
    AuthCommandHandler handler;

    @POST
    @Path("/verify")
    public Response verifyOtp(
            VerifyOtpRequest req
    ) {

        VerifyOtpCommand command =
                new VerifyOtpCommand();

        command.email = req.identifier;
        command.otp = req.otp;

        return Response.ok(
                handler.verifyOtp(command)
        ).build();
    }

    @POST
    @Path("/resend")
    public Response resendOtp(
            ResendOtpRequest req
    ) {

        ResendOtpCommand command =
                new ResendOtpCommand();

        command.email = req.identifier;

        return Response.ok(
                handler.resendOtp(command)
        ).build();
    }
}