package com.janocare.booking.infrastructure.persistence.mappers;

import com.janocare.booking.domain.entities.BookingSlot;
import com.janocare.booking.infrastructure.persistence.entities.BookingSlotJpaEntity;

public class BookingSlotPersistenceMapper {

    private BookingSlotPersistenceMapper() {}

    public static BookingSlotJpaEntity toJpaEntity(BookingSlot domain) {
        BookingSlotJpaEntity entity = new BookingSlotJpaEntity();

        entity.id = domain.getId();
        entity.professionalId = domain.getProfessionalId();
        entity.slotDate = domain.getSlotDate();
        entity.startTime = domain.getStartTime();
        entity.endTime = domain.getEndTime();
        entity.slotInterval = domain.getSlotInterval();
        entity.status = domain.getStatus();
        entity.createdAt = domain.getCreatedAt();
        entity.updatedAt = domain.getUpdatedAt();

        return entity;
    }

    public static BookingSlot toDomain(BookingSlotJpaEntity entity) {
        return BookingSlot.restore(
                entity.id,
                entity.professionalId,
                entity.slotDate,
                entity.startTime,
                entity.endTime,
                entity.slotInterval,
                entity.status,
                entity.createdAt,
                entity.updatedAt
        );
    }
}