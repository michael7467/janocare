package com.janocare.booking.application.commands;

import java.time.LocalDate;
import java.util.UUID;

public class UpdateAppointmentBookingCommand {

    public UUID appointmentBookingId;

    public UUID professionalId;

    public LocalDate bookingDate;

    public String type;

    public String bookingReason;

    public String timezone;

    public String status;
}