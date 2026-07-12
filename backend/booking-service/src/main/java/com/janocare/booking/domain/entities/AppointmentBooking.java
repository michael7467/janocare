package com.janocare.booking.domain.entities;

import com.janocare.booking.domain.enums.AppointmentBookingStatus;
import com.janocare.booking.domain.enums.AppointmentBookingType;
import com.janocare.booking.domain.exceptions.InvalidBookingOperationException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class AppointmentBooking {

    // Reference by Identity — auth service (cross-context, no FK)
    private UUID patientUserId;

    // Reference by Identity — professional service (cross-context, no FK)
    private UUID professionalId;

    private UUID id;
    private UUID bookingSlotId;
    private LocalDate bookingDate;

    // State pattern:
    // PENDING_PAYMENT → CONFIRMED → COMPLETED
    // PENDING_PAYMENT → CANCELLED
    // CONFIRMED       → CANCELLED | REJECTED
    private AppointmentBookingStatus status;

    private AppointmentBookingType type;
    private String bookingReason;
    private String timezone;
    private Instant createdAt;
    private Instant updatedAt;

    private AppointmentBooking() {}

    // =====================================================
    // FACTORY — create new booking
    // =====================================================

    public static AppointmentBooking create(
            UUID patientUserId,
            UUID professionalId,
            UUID bookingSlotId,
            LocalDate bookingDate,
            AppointmentBookingType type,
            String bookingReason,
            String timezone
    ) {
        if (patientUserId == null)
            throw new IllegalArgumentException(
                    "Patient user ID is required");
        if (professionalId == null)
            throw new IllegalArgumentException(
                    "Professional ID is required");
        if (bookingSlotId == null)
            throw new IllegalArgumentException(
                    "Booking slot ID is required");
        if (bookingDate == null)
            throw new IllegalArgumentException(
                    "Booking date is required");
        if (type == null)
            throw new IllegalArgumentException(
                    "Booking type is required");

        AppointmentBooking booking = new AppointmentBooking();
        booking.id              = UUID.randomUUID();
        booking.patientUserId   = patientUserId;
        booking.professionalId  = professionalId;
        booking.bookingSlotId   = bookingSlotId;
        booking.bookingDate     = bookingDate;
        booking.status          = AppointmentBookingStatus.PENDING_PAYMENT;
        booking.type            = type;
        booking.bookingReason   = bookingReason;
        booking.timezone        = timezone;
        booking.createdAt       = Instant.now();
        booking.updatedAt       = Instant.now();
        return booking;
    }

    // =====================================================
    // FACTORY — restore from persistence
    // =====================================================

    public static AppointmentBooking restore(
            UUID id,
            UUID patientUserId,
            UUID professionalId,
            UUID bookingSlotId,
            LocalDate bookingDate,
            AppointmentBookingStatus status,
            AppointmentBookingType type,
            String bookingReason,
            String timezone,
            Instant createdAt,
            Instant updatedAt
    ) {
        AppointmentBooking booking = new AppointmentBooking();
        booking.id              = id;
        booking.patientUserId   = patientUserId;
        booking.professionalId  = professionalId;
        booking.bookingSlotId   = bookingSlotId;
        booking.bookingDate     = bookingDate;
        booking.status          = status;
        booking.type            = type;
        booking.bookingReason   = bookingReason;
        booking.timezone        = timezone;
        booking.createdAt       = createdAt;
        booking.updatedAt       = updatedAt;
        return booking;
    }

    // =====================================================
    // BUSINESS METHODS — State pattern
    // Guard conditions on every transition
    // =====================================================

    /**
     * PENDING_PAYMENT → CONFIRMED
     * Called after payment is registered successfully.
     */
    public void confirm() {
        if (status != AppointmentBookingStatus.PENDING_PAYMENT) {
            throw new InvalidBookingOperationException(
                    "Only PENDING_PAYMENT bookings can be confirmed. " +
                    "Current status: " + status);
        }
        status    = AppointmentBookingStatus.CONFIRMED;
        updatedAt = Instant.now();
    }

    /**
     * CONFIRMED → COMPLETED
     * Called when appointment has been attended.
     */
    public void complete() {
        if (status != AppointmentBookingStatus.CONFIRMED) {
            throw new InvalidBookingOperationException(
                    "Only CONFIRMED bookings can be completed. " +
                    "Current status: " + status);
        }
        status    = AppointmentBookingStatus.COMPLETED;
        updatedAt = Instant.now();
    }

    /**
     * PENDING_PAYMENT | CONFIRMED → CANCELLED
     * Guard: cannot cancel a completed booking.
     */
    public void cancel() {
        if (status == AppointmentBookingStatus.COMPLETED) {
            throw new InvalidBookingOperationException(
                    "Completed bookings cannot be cancelled");
        }
        if (status == AppointmentBookingStatus.CANCELLED) {
            throw new InvalidBookingOperationException(
                    "Booking is already cancelled");
        }
        status    = AppointmentBookingStatus.CANCELLED;
        updatedAt = Instant.now();
    }

    /**
     * PENDING_PAYMENT | CONFIRMED → REJECTED
     * Guard: cannot reject a completed or already rejected booking.
     */
    public void reject() {
        if (status == AppointmentBookingStatus.COMPLETED) {
            throw new InvalidBookingOperationException(
                    "Completed bookings cannot be rejected");
        }
        if (status == AppointmentBookingStatus.REJECTED) {
            throw new InvalidBookingOperationException(
                    "Booking is already rejected");
        }
        if (status == AppointmentBookingStatus.CANCELLED) {
            throw new InvalidBookingOperationException(
                    "Cancelled bookings cannot be rejected");
        }
        status    = AppointmentBookingStatus.REJECTED;
        updatedAt = Instant.now();
    }

    /**
     * Partial update — only updates non-null fields.
     * Uses enum types directly — no raw String parsing.
     */
    public void update(
            LocalDate bookingDate,
            AppointmentBookingType type,       // enum — not String
            String bookingReason,
            String timezone
    ) {
        if (bookingDate != null)    this.bookingDate    = bookingDate;
        if (type != null)           this.type           = type;
        if (bookingReason != null)  this.bookingReason  = bookingReason;
        if (timezone != null)       this.timezone       = timezone;
        this.updatedAt = Instant.now();
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public UUID getId()                         { return id; }
    public UUID getPatientUserId()              { return patientUserId; }
    public UUID getProfessionalId()             { return professionalId; }
    public UUID getBookingSlotId()              { return bookingSlotId; }
    public LocalDate getBookingDate()           { return bookingDate; }
    public AppointmentBookingStatus getStatus() { return status; }
    public AppointmentBookingType getType()     { return type; }
    public String getBookingReason()            { return bookingReason; }
    public String getTimezone()                 { return timezone; }
    public Instant getCreatedAt()               { return createdAt; }
    public Instant getUpdatedAt()               { return updatedAt; }
}