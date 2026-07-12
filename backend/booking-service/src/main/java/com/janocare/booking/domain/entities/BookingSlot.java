package com.janocare.booking.domain.entities;

import com.janocare.booking.domain.enums.BookingSlotStatus;
import com.janocare.booking.domain.exceptions.InvalidSlotOperationException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class BookingSlot {

    private UUID id;

    // Reference by Identity — professional service
    // No FK constraint — resolved via REST call when needed
    private UUID professionalId;

    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;

    // slotInterval comes from ProfessionType.slotInterval
    // Accountability pattern: knowledge level configures
    // the operational level slot duration
    private Integer slotInterval;

    // State pattern: AVAILABLE → RESERVED → BOOKED → CANCELLED
    private BookingSlotStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    private BookingSlot() {}

    // =====================================================
    // FACTORY — create new slot
    // =====================================================

    public static BookingSlot create(
            UUID professionalId,
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime,
            Integer slotInterval
    ) {
        if (professionalId == null)
            throw new IllegalArgumentException(
                    "Professional ID is required");
        if (slotDate == null)
            throw new IllegalArgumentException(
                    "Slot date is required");
        if (startTime == null || endTime == null)
            throw new IllegalArgumentException(
                    "Start time and end time are required");
        if (!endTime.isAfter(startTime))
            throw new IllegalArgumentException(
                    "End time must be after start time");
        if (slotInterval == null || slotInterval <= 0)
            throw new IllegalArgumentException(
                    "Slot interval must be a positive number of minutes");

        BookingSlot slot = new BookingSlot();
        slot.id             = UUID.randomUUID();
        slot.professionalId = professionalId;
        slot.slotDate       = slotDate;
        slot.startTime      = startTime;
        slot.endTime        = endTime;
        slot.slotInterval   = slotInterval;
        slot.status         = BookingSlotStatus.AVAILABLE;
        slot.createdAt      = Instant.now();
        slot.updatedAt      = Instant.now();
        return slot;
    }

    // =====================================================
    // FACTORY — restore from persistence
    // =====================================================

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
        slot.id             = id;
        slot.professionalId = professionalId;
        slot.slotDate       = slotDate;
        slot.startTime      = startTime;
        slot.endTime        = endTime;
        slot.slotInterval   = slotInterval;
        slot.status         = status;
        slot.createdAt      = createdAt;
        slot.updatedAt      = updatedAt;
        return slot;
    }

    // =====================================================
    // BUSINESS METHODS — State pattern
    // All transitions are guarded — invalid state throws exception
    // =====================================================

    /**
     * AVAILABLE → RESERVED
     * Used when patient browses and temporarily holds a slot.
     */
    public void reserve() {
        if (status != BookingSlotStatus.AVAILABLE) {
            throw new InvalidSlotOperationException(
                    "Slot cannot be reserved. " +
                    "Current status: " + status +
                    ". Required: AVAILABLE");
        }
        status    = BookingSlotStatus.RESERVED;
        updatedAt = Instant.now();
    }

    /**
     * AVAILABLE | RESERVED → BOOKED
     * Accepts both AVAILABLE and RESERVED — patient can book
     * directly without prior reservation in some flows.
     */
    public void book() {
        if (status != BookingSlotStatus.AVAILABLE &&
                status != BookingSlotStatus.RESERVED) {
            throw new InvalidSlotOperationException(
                    "Slot cannot be booked. " +
                    "Current status: " + status +
                    ". Required: AVAILABLE or RESERVED");
        }
        status    = BookingSlotStatus.BOOKED;
        updatedAt = Instant.now();
    }

    /**
     * BOOKED | RESERVED | AVAILABLE → CANCELLED
     * Guard: cannot cancel an already cancelled slot.
     */
    public void cancel() {
        if (status == BookingSlotStatus.CANCELLED) {
            throw new InvalidSlotOperationException(
                    "Slot is already cancelled");
        }
        status    = BookingSlotStatus.CANCELLED;
        updatedAt = Instant.now();
    }

    /**
     * CANCELLED | BOOKED → AVAILABLE
     * Used when a booking is cancelled — slot becomes available again.
     */
    public void makeAvailable() {
        if (status == BookingSlotStatus.AVAILABLE) {
            throw new InvalidSlotOperationException(
                    "Slot is already available");
        }
        status    = BookingSlotStatus.AVAILABLE;
        updatedAt = Instant.now();
    }

    /**
     * Partial update — only updates non-null fields.
     * Status update uses enum directly — no raw string parsing.
     */
    public void update(
            LocalDate slotDate,
            LocalTime startTime,
            LocalTime endTime,
            BookingSlotStatus status  // enum — not String
    ) {
        if (slotDate != null)  this.slotDate   = slotDate;
        if (startTime != null) this.startTime  = startTime;
        if (endTime != null)   this.endTime    = endTime;
        if (status != null)    this.status     = status;
        this.updatedAt = Instant.now();
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public UUID getId()                  { return id; }
    public UUID getProfessionalId()      { return professionalId; }
    public LocalDate getSlotDate()       { return slotDate; }
    public LocalTime getStartTime()      { return startTime; }
    public LocalTime getEndTime()        { return endTime; }
    public Integer getSlotInterval()     { return slotInterval; }
    public BookingSlotStatus getStatus() { return status; }
    public Instant getCreatedAt()        { return createdAt; }
    public Instant getUpdatedAt()        { return updatedAt; }
}