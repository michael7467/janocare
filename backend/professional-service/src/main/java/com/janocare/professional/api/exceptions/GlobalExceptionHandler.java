package com.janocare.professional.api.exceptions;

import com.janocare.professional.api.responses.ApiResponse;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ProfessionTypeNotFoundException;
import com.janocare.professional.domain.exceptions.UnauthorizedException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler
        implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof ProfessionalNotFoundException) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(
                            ApiResponse.error(exception.getMessage())
                    )
                    .build();
        }

        if (exception instanceof ProfessionTypeNotFoundException) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(
                            ApiResponse.error(exception.getMessage())
                    )
                    .build();
        }

        if (exception instanceof ValidationException) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(
                            ApiResponse.error(exception.getMessage())
                    )
                    .build();
        }

        if (exception instanceof UnauthorizedException) {

            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(
                            ApiResponse.error(exception.getMessage())
                    )
                    .build();
        }

        if (exception instanceof ConstraintViolationException) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(
                            ApiResponse.error(exception.getMessage())
                    )
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(
                        ApiResponse.error("Something went wrong")
                )
                .build();
    }
}