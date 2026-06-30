package com.janocare.booking.application.ports;

import com.janocare.booking.domain.entities.BookingSlot;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface BookingSlotRepositoryPort {

    BookingSlot save(BookingSlot slot);

    Optional<BookingSlot> findDomainById(UUID id);

    List<BookingSlot> findByProfessionalId(UUID professionalId);

    List<BookingSlot> findAllSlots();

    void deleteSlotById(UUID id);

    Map<String, List<BookingSlot>> findGroupedByMonth(UUID professionalId);

    List<Map<String, Object>> findAnalyzedSlots(UUID professionalId);
    List<BookingSlot> findByProfessionalIdAndSlotDate(UUID professionalId, LocalDate slotDate);
}