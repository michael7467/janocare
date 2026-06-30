package com.janocare.booking.api.mappers;

import com.janocare.booking.api.responses.AppointmentBookingResponse;
import com.janocare.booking.domain.entities.AppointmentBooking;

public class AppointmentBookingApiMapper {

    private AppointmentBookingApiMapper() {}

    public static AppointmentBookingResponse toResponse(AppointmentBooking booking) {
        AppointmentBookingResponse response = new AppointmentBookingResponse();

        response.id = booking.getId();
        response.patientUserId = booking.getPatientUserId();
        response.professionalId = booking.getProfessionalId();
        response.bookingSlotId = booking.getBookingSlotId();
        response.bookingDate = booking.getBookingDate();
        response.status = booking.getStatus();
        response.type = booking.getType();
        response.bookingReason = booking.getBookingReason();
        response.timezone = booking.getTimezone();

        return response;
    }
}