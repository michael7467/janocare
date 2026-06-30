package com.janocare.booking.api.responses;

import java.time.LocalDate;

public class BookingSlotAnalysisResponse {
    public String slotDate;
    public Integer slotInterval;
    public Long Total;
    public Long PENDING;
    public Long ACCEPTED;
    public Long COMPLETED;
    public Long CANCELLED;
    public Long REJECTED;
}