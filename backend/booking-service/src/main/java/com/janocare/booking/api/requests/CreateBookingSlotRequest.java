package com.janocare.booking.api.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public class CreateBookingSlotRequest {
    public String slotDate;          // accept as String, parse manually
    public Integer slotInterval;
    public List<String> startTimes;  // array of time strings
}