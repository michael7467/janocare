package com.janocare.booking.domain.entities;

import com.janocare.booking.domain.enums.BookingSlotStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class BookingSlot {

    private UUID id;
    private UUID professionalId;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer slotInterval;
    private BookingSlotStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    private BookingSlot() {}

    public static BookingSlot create(
            UUID professionalId,
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime,
            Integer slotInterval
    ) {
        BookingSlot slot = new BookingSlot();

        slot.id = UUID.randomUUID();
        slot.professionalId = professionalId;
        slot.slotDate = slotDate;
        slot.startTime = startTime;
        slot.endTime = endTime;
        slot.slotInterval = slotInterval;
        slot.status = BookingSlotStatus.AVAILABLE;
        slot.createdAt = Instant.now();
        slot.updatedAt = Instant.now();

        return slot;
    }
    public void update(
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime,
            String status
    ) {
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = BookingSlotStatus.valueOf(status.toUpperCase());
    }
    public static BookingSlot restore(
            UUID id,
            UUID professionalId,
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime,
            Integer slotInterval,
            BookingSlotStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        BookingSlot slot = new BookingSlot();

        slot.id = id;
        slot.professionalId = professionalId;
        slot.slotDate = slotDate;
        slot.startTime = startTime;
        slot.endTime = endTime;
        slot.slotInterval = slotInterval;
        slot.status = status;
        slot.createdAt = createdAt;
        slot.updatedAt = updatedAt;

        return slot;
    }

    public void reserve() {
        if (status != BookingSlotStatus.AVAILABLE) {
            throw new RuntimeException("Slot is not available");
        }

        status = BookingSlotStatus.RESERVED;
        updatedAt = Instant.now();
    }

    public void book() {
        if (status != BookingSlotStatus.RESERVED &&
                status != BookingSlotStatus.AVAILABLE) {
            throw new RuntimeException("Slot cannot be booked");
        }

        status = BookingSlotStatus.BOOKED;
        updatedAt = Instant.now();
    }

    public void cancel() {
        status = BookingSlotStatus.CANCELLED;
        updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getProfessionalId() { return professionalId; }
    public LocalDate getSlotDate() { return slotDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public Integer getSlotInterval() { return slotInterval; }
    public BookingSlotStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}