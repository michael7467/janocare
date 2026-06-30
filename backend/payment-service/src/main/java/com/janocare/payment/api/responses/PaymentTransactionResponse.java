package com.janocare.payment.api.responses;

import com.janocare.payment.domain.enums.PaymentStatus;
import com.janocare.payment.domain.enums.PaymentType;
import com.janocare.payment.domain.enums.SettlementStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class PaymentTransactionResponse {

    public UUID id;

    public UUID appointmentBookingId;

    public UUID patientUserId;

    public UUID professionalId;

    public BigDecimal amount;

    public String currency;

    public String referenceNumber;

    public PaymentType paymentType;

    public SettlementStatus settlementStatus;

    public PaymentStatus paymentStatus;

    public Instant transactionDate;

    public String transactionNote;
}