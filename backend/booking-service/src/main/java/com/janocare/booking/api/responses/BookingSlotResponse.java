package com.janocare.booking.api.responses;

import com.janocare.booking.domain.enums.BookingSlotStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class BookingSlotResponse {
    public UUID id;
    public UUID professionalId;
    public LocalDate slotDate;
    public LocalTime startTime;
    public LocalTime endTime;
    public Integer slotInterval;
    public BookingSlotStatus status;

        public String slotTime;        // = startTime as "HH:mm AM/PM"
    public String bookingSlotStatus; // = status as string
}