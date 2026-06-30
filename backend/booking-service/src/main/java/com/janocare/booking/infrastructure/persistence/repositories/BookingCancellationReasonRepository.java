package com.janocare.booking.infrastructure.persistence.repositories;

import com.janocare.booking.application.ports.BookingCancellationReasonRepositoryPort;
import com.janocare.booking.domain.entities.BookingCancellationReason;
import com.janocare.booking.infrastructure.persistence.entities.BookingCancellationReasonJpaEntity;
import com.janocare.booking.infrastructure.persistence.mappers.BookingCancellationReasonPersistenceMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class BookingCancellationReasonRepository
        implements PanacheRepositoryBase<BookingCancellationReasonJpaEntity, UUID>,
        BookingCancellationReasonRepositoryPort {

    @Override
    @Transactional
    public BookingCancellationReason save(BookingCancellationReason reason) {
        BookingCancellationReasonJpaEntity entity =
                BookingCancellationReasonPersistenceMapper.toJpaEntity(reason);

        BookingCancellationReasonJpaEntity saved =
                getEntityManager().merge(entity);

        return BookingCancellationReasonPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<BookingCancellationReason> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(BookingCancellationReasonPersistenceMapper::toDomain);
    }

    @Override
    public List<BookingCancellationReason> findByAppointmentBookingId(UUID appointmentBookingId) {
        return find("appointmentBookingId", appointmentBookingId)
                .list()
                .stream()
                .map(BookingCancellationReasonPersistenceMapper::toDomain)
                .toList();
    }
}