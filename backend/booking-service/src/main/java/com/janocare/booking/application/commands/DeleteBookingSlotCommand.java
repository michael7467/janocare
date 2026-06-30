package com.janocare.booking.application.commands;

import java.util.List;
import java.util.UUID;

public class DeleteBookingSlotCommand {

    public UUID professionalId;

    public List<UUID> ids;
}