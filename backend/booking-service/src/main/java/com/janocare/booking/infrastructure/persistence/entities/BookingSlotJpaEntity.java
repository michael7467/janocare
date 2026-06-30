package com.janocare.booking.infrastructure.persistence.entities;

import com.janocare.booking.domain.enums.BookingSlotStatus;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "booking_slots")
public class BookingSlotJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "professional_id", nullable = false)
    public UUID professionalId;

    @Column(name = "slot_date", nullable = false)
    public LocalDate slotDate;

    @Column(name = "start_time", nullable = false)
    public LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    public LocalTime endTime;

    @Column(name = "slot_interval", nullable = false)
    public Integer slotInterval;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public BookingSlotStatus status;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at")
    public Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }

        if (updatedAt == null) {
            updatedAt = Instant.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}