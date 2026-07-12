package com.janocare.booking.api.controllers;

import com.janocare.booking.api.mappers.AppointmentBookingApiMapper;
import com.janocare.booking.api.requests.CancelAppointmentBookingRequest;
import com.janocare.booking.api.requests.CreateAppointmentBookingRequest;
import com.janocare.booking.api.responses.ApiResponse;
import com.janocare.booking.application.commands.CancelAppointmentBookingCommand;
import com.janocare.booking.application.commands.CreateAppointmentBookingCommand;
import com.janocare.booking.application.handlers.BookingHandler;
import com.janocare.booking.application.queries.FindAllAppointmentBookingsQuery;
import com.janocare.booking.application.queries.FindAppointmentBookingByIdQuery;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;

import java.util.UUID;

@Path("/patient/bookings/appointment-bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PATIENT")
public class PatientAppointmentBookingController {

    private static final Logger LOG =
            Logger.getLogger(PatientAppointmentBookingController.class);

    @Inject
    BookingHandler handler;

    @Context
    SecurityContext securityContext;

    // ── extracts patient UUID from verified JWT ───────────────
    // SecurityContext.getUserPrincipal().getName() = sub claim = userId
    private UUID getPatientUserId() {
        return UUID.fromString(
                securityContext.getUserPrincipal().getName());
    }

    // =====================================================
    // GET ALL — patient sees their own bookings only
    // =====================================================

    @GET
    public Response findAll() {

        FindAllAppointmentBookingsQuery query =
                new FindAllAppointmentBookingsQuery();
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

    // =====================================================
    // GET BY ID — patient can only see their own booking
    // =====================================================

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {

        FindAppointmentBookingByIdQuery query =
                new FindAppointmentBookingByIdQuery(id);
        // set patientUserId so handler can verify ownership
        query.patientUserId = getPatientUserId();

        return Response.ok(
                ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.findBookingById(query))
                )
        ).build();
    }

    // =====================================================
    // CREATE — patient books an appointment
    // patientUserId always from JWT — never trusted from body
    // =====================================================

    @POST
    public Response create(@Valid CreateAppointmentBookingRequest req) {

        UUID patientUserId = getPatientUserId();

        CreateAppointmentBookingCommand command =
                new CreateAppointmentBookingCommand();
        command.patientUserId  = patientUserId; // from JWT — not from body
        command.professionalId = req.professionalId;
        command.bookingSlotId  = req.bookingSlotId;
        command.bookingDate    = req.bookingDate;
        command.type           = req.type;
        command.bookingReason  = req.bookingReason;
        command.timezone       = req.timezone;

        LOG.infof("Patient %s creating booking for slot %s",
                patientUserId, req.bookingSlotId);

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.createBooking(command)),
                        "Appointment booking created successfully"
                ))
                .build();
    }

    // =====================================================
    // CANCEL — patient cancels their own booking
    // =====================================================

    @DELETE
    @Path("/{id}")
    public Response cancel(
            @PathParam("id") UUID id,
            @Valid CancelAppointmentBookingRequest req) {

        UUID patientUserId = getPatientUserId();

        CancelAppointmentBookingCommand command =
                new CancelAppointmentBookingCommand();
        command.appointmentBookingId  = id;
        command.userId                = patientUserId; // from JWT
        command.cancellationReasonId  = req.cancellationReasonId;
        command.comment               = req.comment;
        command.timezone              = req.timezone;

        LOG.infof("Patient %s cancelling booking %s", patientUserId, id);

        return Response.ok(
                ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.cancelBooking(command)),
                        "Appointment booking cancelled"
                )
        ).build();
    }
}