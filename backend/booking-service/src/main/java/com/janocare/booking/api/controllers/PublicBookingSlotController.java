package com.janocare.booking.api.controllers;

import com.janocare.booking.api.mappers.BookingSlotApiMapper;
import com.janocare.booking.api.responses.ApiResponse;
import com.janocare.booking.application.handlers.BookingHandler;
import com.janocare.booking.application.queries.FindAllBookingSlotsQuery;
import com.janocare.booking.domain.entities.BookingSlot;
import com.janocare.booking.domain.enums.BookingSlotStatus;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Path("/professionals/public/booking-slots")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class PublicBookingSlotController {

    @Inject
    BookingHandler handler;

    @GET
    public Response findAvailableSlots(
            @QueryParam("professionalId") UUID professionalId,
            @QueryParam("slotDate") String slotDate
    ) {
        FindAllBookingSlotsQuery query = new FindAllBookingSlotsQuery();
        query.professionalId = professionalId;

        if (slotDate != null && !slotDate.isBlank()) {
            try {
                query.slotDate = LocalDate.parse(slotDate.substring(0, 10));
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(ApiResponse.error("Invalid date format"))
                        .build();
            }
        }

        // Use findExistingSlots — never auto-generate for public
        List<BookingSlot> slots = handler.findExistingSlots(query);

        return Response.ok(
                ApiResponse.success(
                        slots.stream()
                                .filter(s -> s.getStatus() == BookingSlotStatus.AVAILABLE)
                                .map(BookingSlotApiMapper::toResponse)
                                .toList()
                )
        ).build();
    }
}