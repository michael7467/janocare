package com.janocare.payment.api.requests;

import com.janocare.payment.domain.enums.PaymentType;

import java.math.BigDecimal;
import java.util.UUID;

public class CreatePaymentTransactionRequest {

    public UUID appointmentBookingId;

    public UUID patientUserId;

    public UUID professionalId;

    public BigDecimal amount;

    public String currency;

    public PaymentType paymentType;

    public String transactionNote;
}