package com.janocare.booking.infrastructure.persistence.mappers;

import com.janocare.booking.domain.entities.AppointmentBooking;
import com.janocare.booking.infrastructure.persistence.entities.AppointmentBookingJpaEntity;

public class AppointmentBookingPersistenceMapper {

    private AppointmentBookingPersistenceMapper() {}

    public static AppointmentBookingJpaEntity toJpaEntity(AppointmentBooking domain) {
        AppointmentBookingJpaEntity entity = new AppointmentBookingJpaEntity();

        entity.id = domain.getId();
        entity.patientUserId = domain.getPatientUserId();
        entity.professionalId = domain.getProfessionalId();
        entity.bookingSlotId = domain.getBookingSlotId();
        entity.bookingDate = domain.getBookingDate();
        entity.status = domain.getStatus();
        entity.type = domain.getType();
        entity.bookingReason = domain.getBookingReason();
        entity.timezone = domain.getTimezone();
        entity.createdAt = domain.getCreatedAt();
        entity.updatedAt = domain.getUpdatedAt();

        return entity;
    }

    public static AppointmentBooking toDomain(AppointmentBookingJpaEntity entity) {
        return AppointmentBooking.restore(
                entity.id,
                entity.patientUserId,
                entity.professionalId,
                entity.bookingSlotId,
                entity.bookingDate,
                entity.status,
                entity.type,
                entity.bookingReason,
                entity.timezone,
                entity.createdAt,
                entity.updatedAt
        );
    }
}