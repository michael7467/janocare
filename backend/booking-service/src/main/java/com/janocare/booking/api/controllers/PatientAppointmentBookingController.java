package com.janocare.booking.api.controllers;

import com.janocare.booking.api.mappers.AppointmentBookingApiMapper;
import com.janocare.booking.api.requests.CreateAppointmentBookingRequest;
import com.janocare.booking.api.responses.ApiResponse;
import com.janocare.booking.application.commands.CreateAppointmentBookingCommand;
import com.janocare.booking.application.handlers.BookingHandler;
import com.janocare.booking.application.queries.FindAllAppointmentBookingsQuery;
import com.janocare.booking.application.queries.FindAppointmentBookingByIdQuery;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.UUID;

@Path("/patient/bookings/appointment-bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PATIENT")
public class PatientAppointmentBookingController {

    @Inject
    BookingHandler handler;

    @Context
    SecurityContext securityContext;

    private UUID getPatientUserId() {
        return UUID.fromString(securityContext.getUserPrincipal().getName());
    }

    @GET
    public Response findAll() {
        FindAllAppointmentBookingsQuery query = new FindAllAppointmentBookingsQuery();
        query.patientUserId = getPatientUserId();

        return Response.ok(
                ApiResponse.success(
                        handler.findAllBookings(query)
                                .stream()
                                .map(AppointmentBookingApiMapper::toResponse)
                                .toList()
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        FindAppointmentBookingByIdQuery query =
                new FindAppointmentBookingByIdQuery(id);

        return Response.ok(
                ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.findBookingById(query)
                        )
                )
        ).build();
    }

    @POST
    public Response create(CreateAppointmentBookingRequest req) {
        CreateAppointmentBookingCommand command =
                new CreateAppointmentBookingCommand();

        command.patientUserId = getPatientUserId(); // from JWT
        command.professionalId = req.professionalId;
        command.bookingSlotId = req.bookingSlotId;
        command.bookingDate = req.bookingDate;
        command.type = req.type;
        command.bookingReason = req.bookingReason;
        command.timezone = req.timezone;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.createBooking(command)
                        ),
                        "Appointment booking created successfully"
                ))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response cancel(@PathParam("id") UUID id) {
        // TODO: implement cancel booking
        return Response.ok(
                ApiResponse.success(null, "Appointment booking cancelled")
        ).build();
    }
}