package com.janocare.booking.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "booking_cancellation_reasons")
public class BookingCancellationReasonJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "appointment_booking_id", nullable = false)
    public UUID appointmentBookingId;

    @Column(name = "user_id", nullable = false)
    public UUID userId;

    @Column(name = "cancellation_reason_id")
    public UUID cancellationReasonId;

    @Column(name = "comment", length = 500)
    public String comment;

    @Column(name = "timezone", length = 64)
    public String timezone;

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