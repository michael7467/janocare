package com.janocare.booking.api.requests;

import com.janocare.booking.domain.enums.AppointmentBookingType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public class CreateAppointmentBookingRequest {

    @NotNull(message = "Professional ID is required")
    public UUID professionalId;

    @NotNull(message = "Booking slot ID is required")
    public UUID bookingSlotId;

    @NotNull(message = "Booking date is required")
    public LocalDate bookingDate;

    @NotNull(message = "Booking type is required")
    public AppointmentBookingType type;

    public String bookingReason;
    public String timezone;
}