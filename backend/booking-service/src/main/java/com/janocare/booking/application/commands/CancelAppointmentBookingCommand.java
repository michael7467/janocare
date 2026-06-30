package com.janocare.booking.application.commands;

import java.util.UUID;

public class CancelAppointmentBookingCommand {
    public UUID appointmentBookingId;
    public UUID userId;
    public UUID cancellationReasonId;
    public String comment;
    public String timezone;
}