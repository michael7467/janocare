package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.Specialization;
import com.janocare.professional.infrastructure.persistence.entities.SpecializationJpaEntity;

public class SpecializationPersistenceMapper {

    private SpecializationPersistenceMapper() {}

    public static SpecializationJpaEntity toJpaEntity(
            Specialization domain
    ) {

        SpecializationJpaEntity entity =
                new SpecializationJpaEntity();

        entity.id = domain.getId();

        entity.name = domain.getName();

        entity.description = domain.getDescription();

        return entity;
    }

    public static Specialization toDomain(
            SpecializationJpaEntity entity
    ) {

        return Specialization.restore(
                entity.id,
                entity.name,
                entity.description
        );
    }
}