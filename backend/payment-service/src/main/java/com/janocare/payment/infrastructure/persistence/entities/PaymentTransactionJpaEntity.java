package com.janocare.payment.infrastructure.persistence.entities;

import com.janocare.payment.domain.enums.PaymentStatus;
import com.janocare.payment.domain.enums.PaymentType;
import com.janocare.payment.domain.enums.SettlementStatus;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_transactions")
public class PaymentTransactionJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(name = "appointment_booking_id", nullable = false)
    public UUID appointmentBookingId;

    @Column(name = "patient_user_id", nullable = false)
    public UUID patientUserId;

    @Column(name = "professional_id", nullable = false)
    public UUID professionalId;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    public BigDecimal amount;

    @Column(name = "currency", length = 10, nullable = false)
    public String currency;

    @Column(name = "reference_number", length = 250, nullable = false)
    public String referenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    public PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "settlement_status", nullable = false)
    public SettlementStatus settlementStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    public PaymentStatus paymentStatus;

    @Column(name = "transaction_date", nullable = false)
    public Instant transactionDate;

    @Column(name = "transaction_note", length = 500)
    public String transactionNote;

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