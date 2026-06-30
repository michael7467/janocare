package com.janocare.booking.application.ports;

import com.janocare.booking.domain.entities.BookingCancellationReason;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingCancellationReasonRepositoryPort {

    BookingCancellationReason save(BookingCancellationReason reason);

    Optional<BookingCancellationReason> findDomainById(UUID id);

    List<BookingCancellationReason> findByAppointmentBookingId(UUID appointmentBookingId);
}