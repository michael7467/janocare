package com.janocare.booking.api.controllers;

import com.janocare.booking.api.mappers.BookingSlotApiMapper;
import com.janocare.booking.api.requests.*;
import com.janocare.booking.api.responses.ApiResponse;
import com.janocare.booking.application.commands.*;
import com.janocare.booking.application.handlers.BookingHandler;
import com.janocare.booking.application.queries.*;
import jakarta.annotation.security.RolesAllowed;
import com.janocare.booking.infrastructure.clients.ProfessionalResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.Context;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.janocare.booking.domain.entities.BookingSlot;
import java.time.LocalTime;
import com.janocare.booking.infrastructure.clients.ProfessionalServiceClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
@Path("/professionals/booking-slots")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PROFESSIONAL")
public class ProfessionalBookingSlotController {

    @Inject
    BookingHandler handler;

    @Context
    SecurityContext securityContext;
@Inject
@RestClient
ProfessionalServiceClient professionalClient; 
  private UUID getUserId() {
    return UUID.fromString(securityContext.getUserPrincipal().getName());
}

// Add this to resolve professionalId:
private UUID resolveProfessionalId() {
    UUID userId = getUserId();
    System.out.println("=== resolveProfessionalId ===");
    System.out.println("userId: " + userId);
    
    ProfessionalResponse response = professionalClient.getProfessionalByUserId(userId);
    System.out.println("response: " + response);
    System.out.println("response.data: " + (response != null ? response.data : "NULL"));
    System.out.println("response.data.id: " + (response != null && response.data != null ? response.data.id : "NULL"));
    
    return response.data.id;
}
@GET
public Response findAll(
        @QueryParam("slotDate") String slotDate
) {
    FindAllBookingSlotsQuery query = new FindAllBookingSlotsQuery();
    query.professionalId = resolveProfessionalId();

    if (slotDate != null && !slotDate.isBlank()) {
        try {
            // Try standard ISO format first
            query.slotDate = LocalDate.parse(slotDate);
        } catch (Exception e) {
            try {
                // Handle JavaScript date string format
                java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern(
                        "EEE MMM dd yyyy HH:mm:ss 'GMT'Z '('zzz')'",
                        java.util.Locale.ENGLISH
                    );
                query.slotDate = java.time.ZonedDateTime
                    .parse(slotDate, formatter)
                    .toLocalDate();
            } catch (Exception e2) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("Invalid date format: " + slotDate))
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
    @GET
    @Path("/me")
    public Response findMy() {
        FindAllBookingSlotsQuery query = new FindAllBookingSlotsQuery();
        query.professionalId = resolveProfessionalId();

        return Response.ok(
                ApiResponse.success(
                        handler.findAllSlots(query)
                                .stream()
                                .map(BookingSlotApiMapper::toResponse)
                                .toList()
                )
        ).build();
    }

  @POST
public Response create(CreateBookingSlotRequest req) {

    UUID userId = getUserId();
    System.out.println("=== CREATE SLOT DEBUG ===");
    System.out.println("userId from JWT: " + userId);

    var professional = professionalClient.getProfessionalByUserId(userId);
    System.out.println("professional object: " + professional);
    System.out.println("professional.id: " + (professional != null ? professional.data.id : "NULL"));

    UUID professionalId = professional != null ? professional.data.id : null;
    System.out.println("professionalId resolved: " + professionalId);

    // Parse date from ISO string
    LocalDate slotDate = LocalDate.parse(req.slotDate.substring(0, 10));

    List<BookingSlot> createdSlots = new ArrayList<>();

    for (String startTimeStr : req.startTimes) {
        LocalTime startTime = LocalTime.parse(
            startTimeStr,
            java.time.format.DateTimeFormatter.ofPattern("hh:mm a", java.util.Locale.ENGLISH)
        );
        LocalTime endTime = startTime.plusMinutes(45);

        CreateBookingSlotCommand command = new CreateBookingSlotCommand();
        command.professionalId = professionalId;
        command.slotDate = slotDate;
        command.startTime = startTime;
        command.endTime = endTime;
        command.slotInterval = 45;

        createdSlots.add(handler.createSlot(command));
    }

    return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.success(
                    createdSlots.stream()
                            .map(BookingSlotApiMapper::toResponse)
                            .toList(),
                    "Booking slots created successfully"
            ))
            .build();
}
    @POST
    @Path("/single")
    public Response createSingle(CreateSingleBookingSlotRequest req) {
        CreateSingleBookingSlotCommand command = new CreateSingleBookingSlotCommand();

        command.professionalId = resolveProfessionalId();
        command.slotDate = req.slotDate;
        command.startTime = req.startTime;
        command.endTime = req.endTime;

        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.success(
                        BookingSlotApiMapper.toResponse(handler.createSingleSlot(command)),
                        "Single booking slot created successfully"
                ))
                .build();
    }

  @GET
@Path("/organized")
public Response findGroupedByMonth() {
    FindGroupedBookingSlotsQuery query = new FindGroupedBookingSlotsQuery();
    query.professionalId = resolveProfessionalId(); // ← from JWT
    return Response.ok(
            ApiResponse.success(handler.findGroupedByMonth(query))
    ).build();
}

    @GET
@Path("/analyze")
public Response findAnalyzed() {
    FindAnalyzedBookingSlotsQuery query = new FindAnalyzedBookingSlotsQuery();
    query.professionalId = resolveProfessionalId();
    return Response.ok(
            ApiResponse.success(handler.findAnalyzedSlots(query))
    ).build();
}
    @PUT
    @Path("/delete")
    public Response delete(DeleteBookingSlotRequest req) {
        DeleteBookingSlotCommand command = new DeleteBookingSlotCommand();
        command.ids = req.ids;
        command.professionalId = resolveProfessionalId();

        return Response.ok(
                ApiResponse.success(
                        handler.deleteSlots(command),
                        "Booking slots deleted successfully"
                )
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id) {
        FindBookingSlotByIdQuery query = new FindBookingSlotByIdQuery(id);
        query.professionalId = resolveProfessionalId();

        return Response.ok(
                ApiResponse.success(
                        BookingSlotApiMapper.toResponse(handler.findSlotById(query))
                )
        ).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, UpdateBookingSlotRequest req) {
        UpdateBookingSlotCommand command = new UpdateBookingSlotCommand();

        command.id = id;
        command.professionalId = resolveProfessionalId();
        command.slotDate = req.slotDate;
        command.startTime = req.startTime;
        command.endTime = req.endTime;
        command.status = req.status;

        return Response.ok(
                ApiResponse.success(
                        BookingSlotApiMapper.toResponse(handler.updateSlot(command)),
                        "Booking slot updated successfully"
                )
        ).build();
    }
}