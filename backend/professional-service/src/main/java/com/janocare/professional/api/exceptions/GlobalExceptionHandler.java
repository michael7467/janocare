package com.janocare.professional.api.exceptions;

import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ProfessionalNotPendingException;
import com.janocare.professional.domain.exceptions.ProfessionTypeNotFoundException;
import com.janocare.professional.domain.exceptions.UnauthorizedException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.stream.Collectors;

@Provider
public class GlobalExceptionHandler
        implements ExceptionMapper<Exception> {

    private static final Logger LOG =
            Logger.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {

        // 404 — domain not found exceptions
        if (exception instanceof ProfessionalNotFoundException ||
            exception instanceof ProfessionTypeNotFoundException ||
            exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.error(exception.getMessage()))
                    .build();
        }

        // 400 — state transition violation
        // e.g. approving a professional that is not PENDING
        if (exception instanceof ProfessionalNotPendingException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(exception.getMessage()))
                    .build();
        }

        // 400 — domain validation errors
        if (exception instanceof ValidationException ||
            exception instanceof IllegalArgumentException ||
            exception instanceof BadRequestException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(exception.getMessage()))
                    .build();
        }

        // 401 — unauthorized
        if (exception instanceof UnauthorizedException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(ApiResponse.error(exception.getMessage()))
                    .build();
        }

        // 400 — Bean Validation (@Valid, @NotNull, @NotBlank etc)
        // extract field-level messages instead of raw exception text
        if (exception instanceof ConstraintViolationException cve) {
            String errors = cve.getConstraintViolations()
                    .stream()
                    .map(v -> v.getPropertyPath()
                            + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(errors))
                    .build();
        }

        // 500 — unexpected error — log it
        LOG.errorf(exception,
                "Unexpected error: %s", exception.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error(
                        "An unexpected error occurred"))
                .build();
    }
}