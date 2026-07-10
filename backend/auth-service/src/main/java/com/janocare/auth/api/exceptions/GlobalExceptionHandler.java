package com.janocare.auth.api.exceptions;

import jakarta.validation.ConstraintViolationException;
import com.janocare.auth.api.responses.ApiResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG =
            Logger.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Exception e) {

        // 404 — not found
        if (e instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }

        // 400 — bad request / validation
        if (e instanceof BadRequestException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }

        // 400 — illegal argument
        if (e instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }

        // 401 — unauthorized / invalid credentials
        if (e.getMessage() != null && (
                e.getMessage().contains("Invalid credentials") ||
                e.getMessage().contains("Account not verified") ||
                e.getMessage().contains("Invalid refresh token") ||
                e.getMessage().contains("Missing refresh token")
        )) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }

        // 400 — business rule violations
        if (e.getMessage() != null && (
                e.getMessage().contains("Passwords do not match") ||
                e.getMessage().contains("Invalid OTP") ||
                e.getMessage().contains("already registered") ||
                e.getMessage().contains("cannot be self-registered") ||
                e.getMessage().contains("Password change required")
        )) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(e.getMessage()))
                    .build();
        }

        // Add inside GlobalExceptionHandler.toResponse():
        if (e instanceof ConstraintViolationException cve) {
        String errors = cve.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(java.util.stream.Collectors.joining(", "));
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ApiResponse.error(errors))
                .build();
        }
        // 500 — unexpected error
        LOG.errorf(e, "Unexpected error: %s", e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("An unexpected error occurred"))
                .build();
    }
}