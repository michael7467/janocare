package com.janocare.payment.infrastructure.persistence.mappers;

import com.janocare.payment.domain.entities.PaymentTransaction;
import com.janocare.payment.infrastructure.persistence.entities.PaymentTransactionJpaEntity;

public class PaymentTransactionPersistenceMapper {

    private PaymentTransactionPersistenceMapper() {}

    public static PaymentTransactionJpaEntity toJpaEntity(
            PaymentTransaction domain
    ) {
        PaymentTransactionJpaEntity entity =
                new PaymentTransactionJpaEntity();

        entity.id = domain.getId();
        entity.appointmentBookingId = domain.getAppointmentBookingId();
        entity.patientUserId = domain.getPatientUserId();
        entity.professionalId = domain.getProfessionalId();
        entity.amount = domain.getAmount();
        entity.currency = domain.getCurrency();
        entity.referenceNumber = domain.getReferenceNumber();
        entity.paymentType = domain.getPaymentType();
        entity.settlementStatus = domain.getSettlementStatus();
        entity.paymentStatus = domain.getPaymentStatus();
        entity.transactionDate = domain.getTransactionDate();
        entity.transactionNote = domain.getTransactionNote();
        entity.createdAt = domain.getCreatedAt();
        entity.updatedAt = domain.getUpdatedAt();

        return entity;
    }

    public static PaymentTransaction toDomain(
            PaymentTransactionJpaEntity entity
    ) {
        return PaymentTransaction.restore(
                entity.id,
                entity.appointmentBookingId,
                entity.patientUserId,
                entity.professionalId,
                entity.amount,
                entity.currency,
                entity.referenceNumber,
                entity.paymentType,
                entity.settlementStatus,
                entity.paymentStatus,
                entity.transactionDate,
                entity.transactionNote,
                entity.createdAt,
                entity.updatedAt
        );
    }
}