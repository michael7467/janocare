package com.janocare.booking.application.commands;

import com.janocare.booking.domain.enums.AppointmentBookingType;

import java.time.LocalDate;
import java.util.UUID;

public class CreateAppointmentBookingCommand {
    public UUID patientUserId;
    public UUID professionalId;
    public UUID bookingSlotId;
    public LocalDate bookingDate;
    public AppointmentBookingType type;
    public String bookingReason;
    public String timezone;
}