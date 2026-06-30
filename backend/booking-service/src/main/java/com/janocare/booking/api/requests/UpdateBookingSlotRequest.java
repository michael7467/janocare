package com.janocare.booking.api.requests;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateBookingSlotRequest {

    public LocalDate slotDate;

    public LocalTime startTime;

    public LocalTime endTime;

    public String status;
}