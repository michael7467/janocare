package com.janocare.booking.api.mappers;

import com.janocare.booking.api.responses.BookingSlotResponse;
import com.janocare.booking.domain.entities.BookingSlot;

public class BookingSlotApiMapper {

    private BookingSlotApiMapper() {}

public static BookingSlotResponse toResponse(BookingSlot domain) {
    BookingSlotResponse r = new BookingSlotResponse();
    r.id = domain.getId();
    r.professionalId = domain.getProfessionalId();
    r.slotDate = domain.getSlotDate();
    r.startTime = domain.getStartTime();
    r.endTime = domain.getEndTime();
    r.slotInterval = domain.getSlotInterval();
    r.status = domain.getStatus();

    // ← add these for frontend compatibility
    r.bookingSlotStatus = domain.getStatus() != null 
        ? domain.getStatus().name() : null;
    
    if (domain.getStartTime() != null) {
        r.slotTime = domain.getStartTime()
            .format(java.time.format.DateTimeFormatter
                .ofPattern("hh:mm a", java.util.Locale.ENGLISH));
    }

    return r;
}
}