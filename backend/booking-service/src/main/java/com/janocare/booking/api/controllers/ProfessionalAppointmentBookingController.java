package com.janocare.booking.api.controllers;

import com.janocare.booking.api.mappers.AppointmentBookingApiMapper;
import com.janocare.booking.api.requests.CreateAppointmentBookingRequest;
import com.janocare.booking.api.requests.UpdateAppointmentBookingRequest;
import com.janocare.booking.api.responses.ApiResponse;
import com.janocare.booking.application.commands.CreateAppointmentBookingCommand;
import com.janocare.booking.application.commands.DeleteAppointmentBookingCommand;
import com.janocare.booking.application.commands.UpdateAppointmentBookingCommand;
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

@Path("/professional/bookings/appointment-bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PROFESSIONAL_USER")
public class ProfessionalAppointmentBookingController {

    @Inject
    BookingHandler handler;

    @Context
    SecurityContext securityContext;

    private UUID getProfessionalId() {
        return UUID.fromString(securityContext.getUserPrincipal().getName());
    }

    @GET
    public Response findAll() {
        FindAllAppointmentBookingsQuery query = new FindAllAppointmentBookingsQuery();
        query.professionalId = getProfessionalId();

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
    @Path("/web")
    public Response findWeb() {
        FindAllAppointmentBookingsQuery query = new FindAllAppointmentBookingsQuery();
        query.professionalId = getProfessionalId();

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

        query.professionalId = getProfessionalId();

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

        command.patientUserId = req.patientUserId;
        command.professionalId = getProfessionalId();
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

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateAppointmentBookingRequest req
    ) {
        UpdateAppointmentBookingCommand command =
                new UpdateAppointmentBookingCommand();

        command.appointmentBookingId = id;
        command.professionalId = getProfessionalId();
        command.bookingDate = req.bookingDate;
        command.type = req.type;
        command.bookingReason = req.bookingReason;
        command.timezone = req.timezone;
        command.status = req.status;

        return Response.ok(
                ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.updateBooking(command)
                        ),
                        "Appointment booking updated successfully"
                )
        ).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        DeleteAppointmentBookingCommand command =
                new DeleteAppointmentBookingCommand();

        command.appointmentBookingId = id;
        command.professionalId = getProfessionalId();

        return Response.ok(
                ApiResponse.success(
                        handler.deleteBooking(command),
                        "Appointment booking deleted successfully"
                )
        ).build();
    }
}