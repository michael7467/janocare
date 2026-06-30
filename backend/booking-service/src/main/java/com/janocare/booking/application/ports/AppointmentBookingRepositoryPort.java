package com.janocare.booking.application.ports;

import com.janocare.booking.domain.entities.AppointmentBooking;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentBookingRepositoryPort {

    AppointmentBooking save(AppointmentBooking booking);

    Optional<AppointmentBooking> findDomainById(UUID id);

    List<AppointmentBooking> findByPatientUserId(UUID patientUserId);

    List<AppointmentBooking> findByProfessionalId(UUID professionalId);

    List<AppointmentBooking> findAllBookings();

    void deleteBookingById(UUID id);

    // optional analytics/dashboard
    Map<String, Object> findBookingAnalytics(UUID professionalId);
}