package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionType;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionTypeJpaEntity;

public class ProfessionTypePersistenceMapper {

    private ProfessionTypePersistenceMapper() {}

    public static ProfessionTypeJpaEntity toJpaEntity(
            ProfessionType domain
    ) {

        ProfessionTypeJpaEntity entity =
                new ProfessionTypeJpaEntity();

        entity.id = domain.getId();

        entity.name = domain.getName();

        entity.description =
                domain.getDescription();

        entity.createdAt =
                domain.getCreatedAt();

        entity.updatedAt =
                domain.getUpdatedAt();

        return entity;
    }

    public static ProfessionType toDomain(
            ProfessionTypeJpaEntity entity
    ) {

        return ProfessionType.restore(
                entity.id,
                entity.name,
                entity.description,
                entity.createdAt,
                entity.updatedAt
        );
    }
}