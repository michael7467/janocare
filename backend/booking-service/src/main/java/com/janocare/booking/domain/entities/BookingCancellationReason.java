package com.janocare.booking.domain.entities;

import java.time.Instant;
import java.util.UUID;

public class BookingCancellationReason {

    private UUID id;
    private UUID appointmentBookingId;
    private UUID userId;
    private UUID cancellationReasonId;
    private String comment;
    private String timezone;
    private Instant createdAt;
    private Instant updatedAt;

    private BookingCancellationReason() {}

    public static BookingCancellationReason create(
            UUID appointmentBookingId,
            UUID userId,
            UUID cancellationReasonId,
            String comment,
            String timezone
    ) {
        BookingCancellationReason item =
                new BookingCancellationReason();

        item.id = UUID.randomUUID();
        item.appointmentBookingId = appointmentBookingId;
        item.userId = userId;
        item.cancellationReasonId = cancellationReasonId;
        item.comment = comment;
        item.timezone = timezone;
        item.createdAt = Instant.now();
        item.updatedAt = Instant.now();

        return item;
    }

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

        item.id = id;
        item.appointmentBookingId = appointmentBookingId;
        item.userId = userId;
        item.cancellationReasonId = cancellationReasonId;
        item.comment = comment;
        item.timezone = timezone;
        item.createdAt = createdAt;
        item.updatedAt = updatedAt;

        return item;
    }

    public UUID getId() { return id; }
    public UUID getAppointmentBookingId() { return appointmentBookingId; }
    public UUID getUserId() { return userId; }
    public UUID getCancellationReasonId() { return cancellationReasonId; }
    public String getComment() { return comment; }
    public String getTimezone() { return timezone; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}