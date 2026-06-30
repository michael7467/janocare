package com.janocare.payment.api.mappers;

import com.janocare.payment.api.responses.PaymentTransactionResponse;
import com.janocare.payment.domain.entities.PaymentTransaction;

public class PaymentTransactionApiMapper {

    private PaymentTransactionApiMapper() {}

    public static PaymentTransactionResponse toResponse(
            PaymentTransaction transaction
    ) {
        PaymentTransactionResponse response =
                new PaymentTransactionResponse();

        response.id = transaction.getId();
        response.appointmentBookingId =
                transaction.getAppointmentBookingId();
        response.patientUserId =
                transaction.getPatientUserId();
        response.professionalId =
                transaction.getProfessionalId();
        response.amount =
                transaction.getAmount();
        response.currency =
                transaction.getCurrency();
        response.referenceNumber =
                transaction.getReferenceNumber();
        response.paymentType =
                transaction.getPaymentType();
        response.settlementStatus =
                transaction.getSettlementStatus();
        response.paymentStatus =
                transaction.getPaymentStatus();
        response.transactionDate =
                transaction.getTransactionDate();
        response.transactionNote =
                transaction.getTransactionNote();

        return response;
    }
}