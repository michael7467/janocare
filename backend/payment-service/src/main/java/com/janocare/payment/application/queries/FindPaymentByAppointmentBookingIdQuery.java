package com.janocare.payment.application.queries;

import java.util.UUID;

public class FindPaymentByAppointmentBookingIdQuery {

    public UUID appointmentBookingId;

    public FindPaymentByAppointmentBookingIdQuery(UUID appointmentBookingId) {
        this.appointmentBookingId = appointmentBookingId;
    }
}