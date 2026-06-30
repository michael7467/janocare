package com.janocare.booking.api.requests;

import com.janocare.booking.domain.enums.AppointmentBookingType;

import java.time.LocalDate;
import java.util.UUID;

public class CreateAppointmentBookingRequest {
    public UUID patientUserId;
    public UUID professionalId;
    public UUID bookingSlotId;
    public LocalDate bookingDate;
    public AppointmentBookingType type;
    public String bookingReason;
    public String timezone;
}