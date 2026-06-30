package com.janocare.payment.application.ports;

import com.janocare.payment.domain.entities.PaymentTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentTransactionRepositoryPort {

    PaymentTransaction save(PaymentTransaction transaction);

    Optional<PaymentTransaction> findDomainById(UUID id);

    Optional<PaymentTransaction> findByAppointmentBookingId(UUID appointmentBookingId);

    List<PaymentTransaction> findByPatientUserId(UUID patientUserId);

    List<PaymentTransaction> findByProfessionalId(UUID professionalId);

    List<PaymentTransaction> findAllTransactions();
}