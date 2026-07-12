package com.janocare.booking.api.controllers;

import com.janocare.booking.api.mappers.BookingSlotApiMapper;
import com.janocare.booking.api.requests.*;
import com.janocare.booking.api.responses.ApiResponse;
import com.janocare.booking.application.commands.*;
import com.janocare.booking.application.handlers.BookingHandler;
import com.janocare.booking.application.queries.*;
import com.janocare.booking.domain.entities.BookingSlot;
import com.janocare.booking.infrastructure.clients.ProfessionalResponse;
import com.janocare.booking.infrastructure.clients.ProfessionalServiceClient;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Path("/professionals/booking-slots")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PROFESSIONAL")
public class ProfessionalBookingSlotController {

    private static final Logger LOG =
            Logger.getLogger(ProfessionalBookingSlotController.class);

    @Inject
    BookingHandler handler;

    @Context
    SecurityContext securityContext;

    @Inject
    @RestClient
    ProfessionalServiceClient professionalClient;

    // ── helpers ───────────────────────────────────────────────

    private UUID getUserId() {
        return UUID.fromString(
                securityContext.getUserPrincipal().getName());
    }

    private UUID resolveProfessionalId() {
        UUID userId = getUserId();
        LOG.debugf("Resolving professional for userId: %s", userId);
        ProfessionalResponse response =
                professionalClient.getProfessionalByUserId(userId);
        return response.data.id;
    }

    // =====================================================
    // GET ALL — professional dashboard
    // Returns existing slots OR auto-generates if none exist
    // =====================================================

    @GET
    public Response findAll(
            @QueryParam("slotDate") String slotDate) {

        FindAllBookingSlotsQuery query =
                new FindAllBookingSlotsQuery();
        query.professionalId = resolveProfessionalId();

        if (slotDate != null && !slotDate.isBlank()) {
            try {
                query.slotDate = LocalDate.parse(
                        slotDate.substring(0, 10));
            } catch (Exception e) {
                try {
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern(
                                    "EEE MMM dd yyyy HH:mm:ss 'GMT'Z '('zzz')'",
                                    Locale.ENGLISH);
                    query.slotDate = java.time.ZonedDateTime
                            .parse(slotDate, formatter)
                            .toLocalDate();
                } catch (Exception e2) {
                    return Response
                            .status(Response.Status.BAD_REQUEST)
                            .entity(ApiResponse.error(
                                    "Invalid date format: "
                                            + slotDate))
                            .build();
                }
            }
        }

        return Response.ok(
                ApiResponse.success(
                        handler.findOrGenerateSlots(query)
                                .stream()
                                .map(BookingSlotApiMapper::toResponse)
                                .toList()
                )
        ).build();
    }

    // =====================================================
    // GET MY SLOTS — all slots without date filter
    // =====================================================

    @GET
    @Path("/me")
    public Response findMy() {

        FindAllBookingSlotsQuery query =
                new FindAllBookingSlotsQuery();
        query.professionalId = resolveProfessionalId();

        return Response.ok(
                ApiResponse.success(
                        handler.findOrGenerateSlots(query) // fixed: was findAllSlots()
                                .stream()
                                .map(BookingSlotApiMapper::toResponse)
                                .toList()
                )
        ).build();
    }

    // =====================================================
    // CREATE — batch slot creation from selected times
    // =====================================================

    @POST
    public Response create(CreateBookingSlotRequest req) {

        UUID professionalId = resolveProfessionalId();
        LOG.debugf("Creating slots for professional: %s", professionalId);

        // parse date — handle ISO string from frontend
        LocalDate slotDate = LocalDate.parse(
                req.slotDate.substring(0, 10));

        // get slot interval from request or default to 45
        int slotInterval = req.slotInterval != null
                ? req.slotInterval
                : 45;

        List<BookingSlot> createdSlots = new ArrayList<>();

        for (String startTimeStr : req.startTimes) {
            LocalTime startTime = LocalTime.parse(
                    startTimeStr,
                    DateTimeFormatter.ofPattern(
                            "hh:mm a", Locale.ENGLISH)
            );
            LocalTime endTime = startTime.plusMinutes(slotInterval);

            CreateBookingSlotCommand command =
                    new CreateBookingSlotCommand();
            command.professionalId = professionalId;
            command.slotDate       = slotDate;
            command.startTime      = startTime;
            command.endTime        = endTime;
            command.slotInterval   = slotInterval;

            createdSlots.add(handler.createSlot(command));
        }

        LOG.infof("Created %d slots for professional %s on %s",
                createdSlots.size(), professionalId, slotDate);

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        createdSlots.stream()
                                .map(BookingSlotApiMapper::toResponse)
                                .toList(),
                        "Booking slots created successfully"
                ))
                .build();
    }

    // =====================================================
    // CREATE SINGLE
    // =====================================================

    @POST
    @Path("/single")
    public Response createSingle(
            CreateSingleBookingSlotRequest req) {

        CreateSingleBookingSlotCommand command =
                new CreateSingleBookingSlotCommand();
        command.professionalId = resolveProfessionalId();
        command.slotDate       = req.slotDate;
        command.startTime      = req.startTime;
        command.endTime        = req.endTime;
        command.slotInterval   = req.slotInterval;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        BookingSlotApiMapper.toResponse(
                                handler.createSingleSlot(command)),
                        "Single booking slot created"
                ))
                .build();
    }

    // =====================================================
    // ANALYTICS
    // =====================================================

    @GET
    @Path("/organized")
    public Response findGroupedByMonth() {
        FindGroupedBookingSlotsQuery query =
                new FindGroupedBookingSlotsQuery();
        query.professionalId = resolveProfessionalId();
        return Response.ok(
                ApiResponse.success(
                        handler.findGroupedByMonth(query))
        ).build();
    }

    @GET
    @Path("/analyze")
    public Response findAnalyzed() {
        FindAnalyzedBookingSlotsQuery query =
                new FindAnalyzedBookingSlotsQuery();
        query.professionalId = resolveProfessionalId();
        return Response.ok(
                ApiResponse.success(
                        handler.findAnalyzedSlots(query))
        ).build();
    }

    // =====================================================
    // FIND BY ID
    // =====================================================

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {

        FindBookingSlotByIdQuery query =
                new FindBookingSlotByIdQuery(id);
        query.professionalId = resolveProfessionalId();

        return Response.ok(
                ApiResponse.success(
                        BookingSlotApiMapper.toResponse(
                                handler.findSlotById(query)))
        ).build();
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PUT
    @Path("/{id}")
    public Response update(
            @PathParam("id") UUID id,
            UpdateBookingSlotRequest req) {

        UpdateBookingSlotCommand command =
                new UpdateBookingSlotCommand();
        command.id             = id;
        command.professionalId = resolveProfessionalId();
        command.slotDate       = req.slotDate;
        command.startTime      = req.startTime;
        command.endTime        = req.endTime;
        command.status         = req.status;

        return Response.ok(
                ApiResponse.success(
                        BookingSlotApiMapper.toResponse(
                                handler.updateSlot(command)),
                        "Booking slot updated successfully"
                )
        ).build();
    }

    // =====================================================
    // DELETE
    // =====================================================

    @PUT
    @Path("/delete")
    public Response delete(DeleteBookingSlotRequest req) {

        DeleteBookingSlotCommand command =
                new DeleteBookingSlotCommand();
        command.ids            = req.ids;
        command.professionalId = resolveProfessionalId();

        return Response.ok(
                ApiResponse.success(
                        handler.deleteSlots(command),
                        "Booking slots deleted successfully"
                )
        ).build();
    }
}