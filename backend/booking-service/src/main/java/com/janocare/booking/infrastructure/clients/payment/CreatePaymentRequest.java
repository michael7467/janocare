package com.janocare.booking.infrastructure.clients.payment;

import com.janocare.booking.domain.enums.AppointmentBookingType;

import java.math.BigDecimal;
import java.util.UUID;

public class CreatePaymentRequest {

    public UUID appointmentBookingId;

    public UUID patientUserId;

    public UUID professionalId;

    public BigDecimal amount;

    public String currency;

    public String paymentType;

    public String transactionNote;
}