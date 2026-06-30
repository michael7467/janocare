package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalSpecialization;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalSpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.SpecializationJpaEntity;

public class ProfessionalSpecializationPersistenceMapper {

    private ProfessionalSpecializationPersistenceMapper() {}

    public static ProfessionalSpecializationJpaEntity toJpaEntity(
            ProfessionalSpecialization domain,
            ProfessionalJpaEntity professional,
            SpecializationJpaEntity specialization
    ) {

        ProfessionalSpecializationJpaEntity entity =
                new ProfessionalSpecializationJpaEntity();

        entity.id = domain.getId();

        entity.professional = professional;

        entity.specialization = specialization;

        return entity;
    }

    public static ProfessionalSpecialization toDomain(
            ProfessionalSpecializationJpaEntity entity
    ) {

        return ProfessionalSpecialization.restore(
                entity.id,
                entity.professional.id,
                entity.specialization.id
        );
    }
}