package com.janocare.booking.infrastructure.persistence.mappers;

import com.janocare.booking.domain.entities.CancellationReason;
import com.janocare.booking.infrastructure.persistence.entities.CancellationReasonJpaEntity;

public class CancellationReasonPersistenceMapper {

    private CancellationReasonPersistenceMapper() {}

    public static CancellationReasonJpaEntity toJpaEntity(CancellationReason domain) {
        CancellationReasonJpaEntity entity = new CancellationReasonJpaEntity();

        entity.id = domain.getId();
        entity.reason = domain.getReason();
        entity.createdAt = domain.getCreatedAt();
        entity.updatedAt = domain.getUpdatedAt();

        return entity;
    }

    public static CancellationReason toDomain(CancellationReasonJpaEntity entity) {
        return CancellationReason.restore(
                entity.id,
                entity.reason,
                entity.createdAt,
                entity.updatedAt
        );
    }
}