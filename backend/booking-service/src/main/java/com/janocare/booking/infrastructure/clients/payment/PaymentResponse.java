package com.janocare.booking.infrastructure.clients.payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PaymentResponse {

    public UUID id;

    public UUID appointmentBookingId;

    public UUID patientUserId;

    public UUID professionalId;

    public BigDecimal amount;

    public String currency;

    public String referenceNumber;

    public String paymentType;

    public String settlementStatus;

    public String paymentStatus;

    public Instant transactionDate;

    public String transactionNote;
}