package com.janocare.booking.api.requests;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateSingleBookingSlotRequest {

    public LocalDate slotDate;

    public LocalTime startTime;

    public LocalTime endTime;
    public Integer slotInterval;
}