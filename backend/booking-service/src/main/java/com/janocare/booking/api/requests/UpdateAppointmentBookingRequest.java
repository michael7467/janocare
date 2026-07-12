package com.janocare.booking.api.requests;
import com.janocare.booking.domain.enums.AppointmentBookingType;
import java.time.LocalDate;

public class UpdateAppointmentBookingRequest {

    public LocalDate bookingDate;

    public String type;

    public String bookingReason;
    public AppointmentBookingType type;
    public String timezone;

    public String status;
}