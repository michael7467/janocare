package com.janocare.payment.application.handlers;

import com.janocare.payment.application.commands.*;
import com.janocare.payment.application.ports.PaymentTransactionRepositoryPort;
import com.janocare.payment.application.queries.*;

import com.janocare.payment.domain.entities.PaymentTransaction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class PaymentHandler {

    @Inject
    PaymentTransactionRepositoryPort paymentRepository;

    @Transactional
    public PaymentTransaction createPayment(
            CreatePaymentTransactionCommand command
    ) {

        PaymentTransaction transaction =
                PaymentTransaction.create(
                        command.appointmentBookingId,
                        command.patientUserId,
                        command.professionalId,
                        command.amount,
                        command.currency,
                        command.paymentType,
                        command.transactionNote
                );

        return paymentRepository.save(transaction);
    }

    @Transactional
    public PaymentTransaction confirmPayment(
            ConfirmPaymentCommand command
    ) {

        PaymentTransaction transaction =
                paymentRepository
                        .findDomainById(command.paymentTransactionId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Payment transaction not found"
                                )
                        );

        transaction.markPaid();

        return paymentRepository.save(transaction);
    }

    @Transactional
    public PaymentTransaction failPayment(
            FailPaymentCommand command
    ) {

        PaymentTransaction transaction =
                paymentRepository
                        .findDomainById(command.paymentTransactionId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Payment transaction not found"
                                )
                        );

        transaction.markFailed();

        return paymentRepository.save(transaction);
    }

    @Transactional
    public PaymentTransaction refundPayment(
            RefundPaymentCommand command
    ) {

        PaymentTransaction transaction =
                paymentRepository
                        .findDomainById(command.paymentTransactionId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Payment transaction not found"
                                )
                        );

        transaction.refund();

        return paymentRepository.save(transaction);
    }

    @Transactional
    public PaymentTransaction settlePayment(
            SettlePaymentCommand command
    ) {

        PaymentTransaction transaction =
                paymentRepository
                        .findDomainById(command.paymentTransactionId)
                        .orElseThrow(() ->
                                new NotFoundException(
                                        "Payment transaction not found"
                                )
                        );

        transaction.settle();

        return paymentRepository.save(transaction);
    }

    public PaymentTransaction findById(
            FindPaymentByIdQuery query
    ) {

        return paymentRepository
                .findDomainById(query.paymentTransactionId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Payment transaction not found"
                        )
                );
    }

    public PaymentTransaction findByAppointmentBookingId(
            FindPaymentByAppointmentBookingIdQuery query
    ) {

        return paymentRepository
                .findByAppointmentBookingId(query.appointmentBookingId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Payment transaction not found"
                        )
                );
    }

    public List<PaymentTransaction> findAll(
            FindAllPaymentsQuery query
    ) {

        if (query.patientUserId != null) {
            return paymentRepository.findByPatientUserId(
                    query.patientUserId
            );
        }

        if (query.professionalId != null) {
            return paymentRepository.findByProfessionalId(
                    query.professionalId
            );
        }

        return paymentRepository.findAllTransactions();
    }
}