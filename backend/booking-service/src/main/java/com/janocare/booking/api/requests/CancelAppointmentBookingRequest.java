package com.janocare.booking.api.requests;

import java.util.UUID;

public class CancelAppointmentBookingRequest {
    public UUID userId;
    public UUID cancellationReasonId;
    public String comment;
    public String timezone;
}