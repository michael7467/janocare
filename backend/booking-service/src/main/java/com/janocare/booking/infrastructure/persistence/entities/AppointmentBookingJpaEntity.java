package com.janocare.booking.infrastructure.persistence.entities;

import com.janocare.booking.domain.enums.AppointmentBookingStatus;
import com.janocare.booking.domain.enums.AppointmentBookingType;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "appointment_bookings")
public class AppointmentBookingJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "patient_user_id", nullable = false)
    public UUID patientUserId;

    @Column(name = "professional_id", nullable = false)
    public UUID professionalId;

    @Column(name = "booking_slot_id", nullable = false)
    public UUID bookingSlotId;

    @Column(name = "booking_date", nullable = false)
    public LocalDate bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    public AppointmentBookingStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_type", nullable = false)
    public AppointmentBookingType type;

    @Column(name = "booking_reason", length = 500)
    public String bookingReason;

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