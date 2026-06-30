package com.janocare.booking.domain.entities;
import com.janocare.booking.domain.enums.AppointmentBookingType;
import com.janocare.booking.domain.enums.AppointmentBookingStatus;
import com.janocare.booking.domain.enums.AppointmentBookingType;
import com.janocare.booking.domain.enums.AppointmentBookingStatus;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class AppointmentBooking {

    private UUID id;
    private UUID patientUserId;
    private UUID professionalId;
    private UUID bookingSlotId;
    private LocalDate bookingDate;
    private AppointmentBookingStatus status;
    private AppointmentBookingType type;
    private String bookingReason;
    private String timezone;
    private Instant createdAt;
    private Instant updatedAt;

    private AppointmentBooking() {}

    public static AppointmentBooking create(
            UUID patientUserId,
            UUID professionalId,
            UUID bookingSlotId,
            LocalDate bookingDate,
            AppointmentBookingType type,
            String bookingReason,
            String timezone
    ) {
        AppointmentBooking booking = new AppointmentBooking();

        booking.id = UUID.randomUUID();
        booking.patientUserId = patientUserId;
        booking.professionalId = professionalId;
        booking.bookingSlotId = bookingSlotId;
        booking.bookingDate = bookingDate;
        booking.status = AppointmentBookingStatus.PENDING_PAYMENT;
        booking.type = type;
        booking.bookingReason = bookingReason;
        booking.timezone = timezone;
        booking.createdAt = Instant.now();
        booking.updatedAt = Instant.now();

        return booking;
    }
public void update(
        LocalDate  bookingDate,
        String type,
        String bookingReason,
        String timezone,
        String status
) {
    this.bookingDate = bookingDate;
    this.type = AppointmentBookingType.valueOf(type.toUpperCase());
    this.bookingReason = bookingReason;
    this.timezone = timezone;
    this.status = AppointmentBookingStatus.valueOf(status.toUpperCase());
}
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

        booking.id = id;
        booking.patientUserId = patientUserId;
        booking.professionalId = professionalId;
        booking.bookingSlotId = bookingSlotId;
        booking.bookingDate = bookingDate;
        booking.status = status;
        booking.type = type;
        booking.bookingReason = bookingReason;
        booking.timezone = timezone;
        booking.createdAt = createdAt;
        booking.updatedAt = updatedAt;

        return booking;
    }

    public void confirm() {
        if (status != AppointmentBookingStatus.PENDING_PAYMENT) {
            throw new RuntimeException("Only pending payment bookings can be confirmed");
        }

        status = AppointmentBookingStatus.CONFIRMED;
        updatedAt = Instant.now();
    }

    public void complete() {
        if (status != AppointmentBookingStatus.CONFIRMED) {
            throw new RuntimeException("Only confirmed bookings can be completed");
        }

        status = AppointmentBookingStatus.COMPLETED;
        updatedAt = Instant.now();
    }

    public void cancel() {
        if (status == AppointmentBookingStatus.COMPLETED) {
            throw new RuntimeException("Completed booking cannot be cancelled");
        }

        status = AppointmentBookingStatus.CANCELLED;
        updatedAt = Instant.now();
    }

    public void reject() {
        status = AppointmentBookingStatus.REJECTED;
        updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getPatientUserId() { return patientUserId; }
    public UUID getProfessionalId() { return professionalId; }
    public UUID getBookingSlotId() { return bookingSlotId; }
    public LocalDate getBookingDate() { return bookingDate; }
    public AppointmentBookingStatus getStatus() { return status; }
    public AppointmentBookingType getType() { return type; }
    public String getBookingReason() { return bookingReason; }
    public String getTimezone() { return timezone; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}