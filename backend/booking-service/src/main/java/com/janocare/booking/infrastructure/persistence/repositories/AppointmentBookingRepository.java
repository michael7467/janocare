package com.janocare.booking.infrastructure.persistence.repositories;

import com.janocare.booking.application.ports.AppointmentBookingRepositoryPort;
import com.janocare.booking.domain.entities.AppointmentBooking;
import com.janocare.booking.infrastructure.persistence.entities.AppointmentBookingJpaEntity;
import com.janocare.booking.infrastructure.persistence.mappers.AppointmentBookingPersistenceMapper;
import java.util.Map;
import com.janocare.booking.domain.enums.AppointmentBookingStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import com.janocare.booking.domain.enums.AppointmentBookingStatus;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class AppointmentBookingRepository
        implements PanacheRepositoryBase<AppointmentBookingJpaEntity, UUID>,
        AppointmentBookingRepositoryPort {

    @Override
    @Transactional
    public AppointmentBooking save(AppointmentBooking booking) {
        AppointmentBookingJpaEntity entity =
                AppointmentBookingPersistenceMapper.toJpaEntity(booking);

        AppointmentBookingJpaEntity saved =
                getEntityManager().merge(entity);

        return AppointmentBookingPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<AppointmentBooking> findDomainById(UUID id) {
        return findByIdOptional(id)
                .map(AppointmentBookingPersistenceMapper::toDomain);
    }

    @Override
    public List<AppointmentBooking> findByPatientUserId(UUID patientUserId) {
        return find("patientUserId", patientUserId)
                .list()
                .stream()
                .map(AppointmentBookingPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<AppointmentBooking> findByProfessionalId(UUID professionalId) {
        return find("professionalId", professionalId)
                .list()
                .stream()
                .map(AppointmentBookingPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<AppointmentBooking> findAllBookings() {
        return listAll()
                .stream()
                .map(AppointmentBookingPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteBookingById(UUID id) {
        deleteById(id);
    }
    @Override
public Map<String, Object> findBookingAnalytics(UUID professionalId) {

   List<AppointmentBooking> bookings =
        findByProfessionalId(professionalId);

long total = bookings.size();

long confirmed = bookings.stream()
        .filter(b -> b.getStatus() == AppointmentBookingStatus.CONFIRMED)
        .count();

long cancelled = bookings.stream()
        .filter(b -> b.getStatus() == AppointmentBookingStatus.CANCELLED)
        .count();

long pendingPayment = bookings.stream()
        .filter(b -> b.getStatus() == AppointmentBookingStatus.PENDING_PAYMENT)
        .count();

return Map.of(
        "total", total,
        "confirmed", confirmed,
        "cancelled", cancelled,
        "pendingPayment", pendingPayment
);
}
}