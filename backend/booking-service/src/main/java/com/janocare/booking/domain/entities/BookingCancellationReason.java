package com.janocare.booking.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class BookingCancellationReason {

    // Reference by Identity — booking aggregate (same service, real FK)
    private UUID appointmentBookingId;

    // Reference by Identity — auth service (cross-context, no FK)
    private UUID userId;

    // Reference by Identity — cancellation reason catalogue (same service, real FK)
    private UUID cancellationReasonId;

    private UUID id;
    private String comment;
    private String timezone;
    private Instant createdAt;
    private Instant updatedAt;

    private BookingCancellationReason() {}

    // =====================================================
    // FACTORY — create new
    // =====================================================

    public static BookingCancellationReason create(
            UUID appointmentBookingId,
            UUID userId,
            UUID cancellationReasonId,
            String comment,
            String timezone
    ) {
        if (appointmentBookingId == null)
            throw new IllegalArgumentException(
                    "Appointment booking ID is required");
        if (userId == null)
            throw new IllegalArgumentException(
                    "User ID is required");
        if (cancellationReasonId == null)
            throw new IllegalArgumentException(
                    "Cancellation reason ID is required");

        BookingCancellationReason item =
                new BookingCancellationReason();
        item.id                    = UUID.randomUUID();
        item.appointmentBookingId  = appointmentBookingId;
        item.userId                = userId;
        item.cancellationReasonId  = cancellationReasonId;
        item.comment               = comment;
        item.timezone              = timezone;
        item.createdAt             = Instant.now();
        item.updatedAt             = Instant.now();
        return item;
    }

    // =====================================================
    // FACTORY — restore from persistence
    // =====================================================

    public static BookingCancellationReason restore(
            UUID id,
            UUID appointmentBookingId,
            UUID userId,
            UUID cancellationReasonId,
            String comment,
            String timezone,
            Instant createdAt,
            Instant updatedAt
    ) {
        BookingCancellationReason item =
                new BookingCancellationReason();
        item.id                    = id;
        item.appointmentBookingId  = appointmentBookingId;
        item.userId                = userId;
        item.cancellationReasonId  = cancellationReasonId;
        item.comment               = comment;
        item.timezone              = timezone;
        item.createdAt             = createdAt;
        item.updatedAt             = updatedAt;
        return item;
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public UUID getId()                       { return id; }
    public UUID getAppointmentBookingId()     { return appointmentBookingId; }
    public UUID getUserId()                   { return userId; }
    public UUID getCancellationReasonId()     { return cancellationReasonId; }
    public String getComment()                { return comment; }
    public String getTimezone()               { return timezone; }
    public Instant getCreatedAt()             { return createdAt; }
    public Instant getUpdatedAt()             { return updatedAt; }
}