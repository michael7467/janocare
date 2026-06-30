package com.janocare.payment.domain.entities;

import com.janocare.payment.domain.enums.PaymentStatus;
import com.janocare.payment.domain.enums.PaymentType;
import com.janocare.payment.domain.enums.SettlementStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PaymentTransaction {

    private UUID id;
    private UUID appointmentBookingId;
    private UUID patientUserId;
    private UUID professionalId;

    private BigDecimal amount;
    private String currency;
    private String referenceNumber;
    private PaymentType paymentType;
    private SettlementStatus settlementStatus;
    private PaymentStatus paymentStatus;
    private Instant transactionDate;
    private String transactionNote;

    private Instant createdAt;
    private Instant updatedAt;

    private PaymentTransaction() {}

    public static PaymentTransaction create(
            UUID appointmentBookingId,
            UUID patientUserId,
            UUID professionalId,
            BigDecimal amount,
            String currency,
            PaymentType paymentType,
            String transactionNote
    ) {
        PaymentTransaction tx = new PaymentTransaction();

        tx.id = UUID.randomUUID();
        tx.appointmentBookingId = appointmentBookingId;
        tx.patientUserId = patientUserId;
        tx.professionalId = professionalId;
        tx.amount = amount;
        tx.currency = currency != null ? currency : "EUR";
        tx.referenceNumber = "PAY-" + UUID.randomUUID();
        tx.paymentType = paymentType != null ? paymentType : PaymentType.FAKE_PAYMENT;
        tx.settlementStatus = SettlementStatus.NOT_SETTLED;
        tx.paymentStatus = PaymentStatus.PENDING;
        tx.transactionDate = Instant.now();
        tx.transactionNote = transactionNote;
        tx.createdAt = Instant.now();
        tx.updatedAt = Instant.now();

        return tx;
    }

    public static PaymentTransaction restore(
            UUID id,
            UUID appointmentBookingId,
            UUID patientUserId,
            UUID professionalId,
            BigDecimal amount,
            String currency,
            String referenceNumber,
            PaymentType paymentType,
            SettlementStatus settlementStatus,
            PaymentStatus paymentStatus,
            Instant transactionDate,
            String transactionNote,
            Instant createdAt,
            Instant updatedAt
    ) {
        PaymentTransaction tx = new PaymentTransaction();

        tx.id = id;
        tx.appointmentBookingId = appointmentBookingId;
        tx.patientUserId = patientUserId;
        tx.professionalId = professionalId;
        tx.amount = amount;
        tx.currency = currency;
        tx.referenceNumber = referenceNumber;
        tx.paymentType = paymentType;
        tx.settlementStatus = settlementStatus;
        tx.paymentStatus = paymentStatus;
        tx.transactionDate = transactionDate;
        tx.transactionNote = transactionNote;
        tx.createdAt = createdAt;
        tx.updatedAt = updatedAt;

        return tx;
    }

    public void markPaid() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new RuntimeException("Only pending payments can be confirmed");
        }

        paymentStatus = PaymentStatus.PAID;
        updatedAt = Instant.now();
    }

    public void markFailed() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new RuntimeException("Only pending payments can be failed");
        }

        paymentStatus = PaymentStatus.FAILED;
        updatedAt = Instant.now();
    }

    public void refund() {
        if (paymentStatus != PaymentStatus.PAID) {
            throw new RuntimeException("Only paid payments can be refunded");
        }

        paymentStatus = PaymentStatus.REFUNDED;
        updatedAt = Instant.now();
    }

    public void settle() {
        if (paymentStatus != PaymentStatus.PAID) {
            throw new RuntimeException("Only paid payments can be settled");
        }

        settlementStatus = SettlementStatus.SETTLED;
        updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getAppointmentBookingId() { return appointmentBookingId; }
    public UUID getPatientUserId() { return patientUserId; }
    public UUID getProfessionalId() { return professionalId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getReferenceNumber() { return referenceNumber; }
    public PaymentType getPaymentType() { return paymentType; }
    public SettlementStatus getSettlementStatus() { return settlementStatus; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public Instant getTransactionDate() { return transactionDate; }
    public String getTransactionNote() { return transactionNote; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}