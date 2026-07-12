package com.janocare.booking.api.controllers;

import com.janocare.booking.api.mappers.AppointmentBookingApiMapper;
import com.janocare.booking.api.requests.ConfirmAppointmentBookingRequest;
import com.janocare.booking.api.requests.UpdateAppointmentBookingRequest;
import com.janocare.booking.api.responses.ApiResponse;
import com.janocare.booking.application.commands.ConfirmAppointmentBookingCommand;
import com.janocare.booking.application.commands.DeleteAppointmentBookingCommand;
import com.janocare.booking.application.commands.UpdateAppointmentBookingCommand;
import com.janocare.booking.application.handlers.BookingHandler;
import com.janocare.booking.application.queries.FindAllAppointmentBookingsQuery;
import com.janocare.booking.application.queries.FindAppointmentBookingByIdQuery;
import com.janocare.booking.infrastructure.clients.ProfessionalResponse;
import com.janocare.booking.infrastructure.clients.ProfessionalServiceClient;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.UUID;

@Path("/professional/bookings/appointment-bookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PROFESSIONAL") // fixed — was "PROFESSIONAL_USER"
public class ProfessionalAppointmentBookingController {

    private static final Logger LOG =
            Logger.getLogger(ProfessionalAppointmentBookingController.class);

    @Inject
    BookingHandler handler;

    @Context
    SecurityContext securityContext;

    @Inject
    @RestClient
    ProfessionalServiceClient professionalClient;

    // ── extracts userId from JWT then resolves professionalId ──
    // getUserPrincipal().getName() = sub claim = userId (not professionalId)
    private UUID getUserId() {
        return UUID.fromString(
                securityContext.getUserPrincipal().getName());
    }

    private UUID resolveProfessionalId() {
        UUID userId = getUserId();
        ProfessionalResponse response =
                professionalClient.getProfessionalByUserId(userId);
        return response.data.id;
    }

    // =====================================================
    // GET ALL — professional sees their own bookings
    // =====================================================

    @GET
    public Response findAll() {

        FindAllAppointmentBookingsQuery query =
                new FindAllAppointmentBookingsQuery();
        query.professionalId = resolveProfessionalId();

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
    // GET BY ID — professional sees their own booking only
    // =====================================================

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {

        FindAppointmentBookingByIdQuery query =
                new FindAppointmentBookingByIdQuery(id);
        query.professionalId = resolveProfessionalId();

        return Response.ok(
                ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.findBookingById(query))
                )
        ).build();
    }

    // =====================================================
    // CONFIRM — professional confirms appointment
    // State: PENDING_PAYMENT → CONFIRMED
    // =====================================================

    @PUT
    @Path("/{id}/confirm")
    public Response confirm(@PathParam("id") UUID id) {

        UUID professionalId = resolveProfessionalId();

        ConfirmAppointmentBookingCommand command =
                new ConfirmAppointmentBookingCommand();
        command.appointmentBookingId = id;

        LOG.infof("Professional %s confirming booking %s",
                professionalId, id);

        return Response.ok(
                ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.confirmBooking(command)),
                        "Appointment booking confirmed"
                )
        ).build();
    }

    // =====================================================
    // UPDATE — professional updates booking details
    // =====================================================

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            @Valid UpdateAppointmentBookingRequest req) {

        UpdateAppointmentBookingCommand command =
                new UpdateAppointmentBookingCommand();
        command.appointmentBookingId = id;
        command.professionalId       = resolveProfessionalId();
        command.bookingDate          = req.bookingDate;
        command.type                 = req.type;
        command.bookingReason        = req.bookingReason;
        command.timezone             = req.timezone;
        // status NOT set here — use confirm/cancel/complete endpoints

        LOG.infof("Professional updating booking %s", id);

        return Response.ok(
                ApiResponse.success(
                        AppointmentBookingApiMapper.toResponse(
                                handler.updateBooking(command)),
                        "Appointment booking updated successfully"
                )
        ).build();
    }

    // =====================================================
    // DELETE — professional deletes booking
    // =====================================================

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {

        DeleteAppointmentBookingCommand command =
                new DeleteAppointmentBookingCommand();
        command.appointmentBookingId = id;
        command.professionalId       = resolveProfessionalId();

        LOG.infof("Professional deleting booking %s", id);

        return Response.ok(
                ApiResponse.success(
                        handler.deleteBooking(command),
                        "Appointment booking deleted successfully"
                )
        ).build();
    }
}