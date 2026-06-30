package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.SubSpecialization;
import com.janocare.professional.infrastructure.persistence.entities.SpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.SubSpecializationJpaEntity;

public class SubSpecializationPersistenceMapper {

    private SubSpecializationPersistenceMapper() {}

    public static SubSpecializationJpaEntity toJpaEntity(
            SubSpecialization domain,
            SpecializationJpaEntity specialization
    ) {

        SubSpecializationJpaEntity entity =
                new SubSpecializationJpaEntity();

        entity.id = domain.getId();

        entity.specialization = specialization;

        entity.name = domain.getName();

        entity.description = domain.getDescription();

        return entity;
    }

    public static SubSpecialization toDomain(
            SubSpecializationJpaEntity entity
    ) {

        return SubSpecialization.restore(
                entity.id,
                entity.specialization.id,
                entity.name,
                entity.description
        );
    }
}