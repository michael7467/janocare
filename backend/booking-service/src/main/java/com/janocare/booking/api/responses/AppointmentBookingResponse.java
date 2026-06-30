package com.janocare.booking.api.responses;

import com.janocare.booking.domain.enums.AppointmentBookingStatus;
import com.janocare.booking.domain.enums.AppointmentBookingType;

import java.time.LocalDate;
import java.util.UUID;

public class AppointmentBookingResponse {
    public UUID id;
    public UUID patientUserId;
    public UUID professionalId;
    public UUID bookingSlotId;
    public LocalDate bookingDate;
    public AppointmentBookingStatus status;
    public AppointmentBookingType type;
    public String bookingReason;
    public String timezone;
}