package com.janocare.booking.infrastructure.persistence.mappers;

import com.janocare.booking.domain.entities.BookingCancellationReason;
import com.janocare.booking.infrastructure.persistence.entities.BookingCancellationReasonJpaEntity;

public class BookingCancellationReasonPersistenceMapper {

    private BookingCancellationReasonPersistenceMapper() {}

    public static BookingCancellationReasonJpaEntity toJpaEntity(
            BookingCancellationReason domain
    ) {
        BookingCancellationReasonJpaEntity entity =
                new BookingCancellationReasonJpaEntity();

        entity.id = domain.getId();
        entity.appointmentBookingId = domain.getAppointmentBookingId();
        entity.userId = domain.getUserId();
        entity.cancellationReasonId = domain.getCancellationReasonId();
        entity.comment = domain.getComment();
        entity.timezone = domain.getTimezone();
        entity.createdAt = domain.getCreatedAt();
        entity.updatedAt = domain.getUpdatedAt();

        return entity;
    }

    public static BookingCancellationReason toDomain(
            BookingCancellationReasonJpaEntity entity
    ) {
        return BookingCancellationReason.restore(
                entity.id,
                entity.appointmentBookingId,
                entity.userId,
                entity.cancellationReasonId,
                entity.comment,
                entity.timezone,
                entity.createdAt,
                entity.updatedAt
        );
    }
}