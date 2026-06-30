package com.janocare.booking.application.queries;

import java.util.UUID;

public class FindAppointmentBookingByIdQuery {

    public UUID appointmentBookingId;

    public UUID professionalId;

    public FindAppointmentBookingByIdQuery(UUID appointmentBookingId) {
        this.appointmentBookingId = appointmentBookingId;
    }
}