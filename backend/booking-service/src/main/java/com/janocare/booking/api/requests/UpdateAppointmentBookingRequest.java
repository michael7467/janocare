package com.janocare.booking.api.requests;

import java.time.LocalDate;

public class UpdateAppointmentBookingRequest {

    public LocalDate bookingDate;

    public String type;

    public String bookingReason;

    public String timezone;

    public String status;
}