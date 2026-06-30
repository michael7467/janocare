package com.janocare.booking.application.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class CreateSingleBookingSlotCommand {

    public UUID professionalId;

    public LocalDate slotDate;

    public LocalTime startTime;

    public LocalTime endTime;
}