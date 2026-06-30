package com.janocare.payment.infrastructure.persistence.repositories;

import com.janocare.payment.application.ports.PaymentTransactionRepositoryPort;
import com.janocare.payment.domain.entities.PaymentTransaction;
import com.janocare.payment.infrastructure.persistence.entities.PaymentTransactionJpaEntity;
import com.janocare.payment.infrastructure.persistence.mappers.PaymentTransactionPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PaymentTransactionRepository
        implements PanacheRepositoryBase<PaymentTransactionJpaEntity, UUID>,
        PaymentTransactionRepositoryPort {

    @Override
    @Transactional
    public PaymentTransaction save(
            PaymentTransaction transaction
    ) {
        PaymentTransactionJpaEntity entity =
                PaymentTransactionPersistenceMapper.toJpaEntity(
                        transaction
                );

        PaymentTransactionJpaEntity saved =
                getEntityManager().merge(entity);

        return PaymentTransactionPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<PaymentTransaction> findDomainById(
            UUID id
    ) {
        return findByIdOptional(id)
                .map(PaymentTransactionPersistenceMapper::toDomain);
    }

    @Override
    public Optional<PaymentTransaction> findByAppointmentBookingId(
            UUID appointmentBookingId
    ) {
        return find("appointmentBookingId", appointmentBookingId)
                .firstResultOptional()
                .map(PaymentTransactionPersistenceMapper::toDomain);
    }

    @Override
    public List<PaymentTransaction> findByPatientUserId(
            UUID patientUserId
    ) {
        return find("patientUserId", patientUserId)
                .list()
                .stream()
                .map(PaymentTransactionPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<PaymentTransaction> findByProfessionalId(
            UUID professionalId
    ) {
        return find("professionalId", professionalId)
                .list()
                .stream()
                .map(PaymentTransactionPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<PaymentTransaction> findAllTransactions() {
        return listAll()
                .stream()
                .map(PaymentTransactionPersistenceMapper::toDomain)
                .toList();
    }
}