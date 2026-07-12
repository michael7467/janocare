package com.janocare.booking.api.requests;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CancelAppointmentBookingRequest {

    @NotNull(message = "Cancellation reason is required")
    public UUID cancellationReasonId;

    public String comment;
    public String timezone;
}