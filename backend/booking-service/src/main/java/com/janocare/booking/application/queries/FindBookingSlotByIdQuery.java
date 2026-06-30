package com.janocare.booking.application.queries;

import java.util.UUID;

public class FindBookingSlotByIdQuery {

    public UUID slotId;

    public UUID professionalId;

    public FindBookingSlotByIdQuery(UUID slotId) {
        this.slotId = slotId;
    }
}