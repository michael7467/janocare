package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalSubSpecialization;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalSubSpecializationJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.SubSpecializationJpaEntity;

public class ProfessionalSubSpecializationPersistenceMapper {

    private ProfessionalSubSpecializationPersistenceMapper() {}

    public static ProfessionalSubSpecializationJpaEntity toJpaEntity(
            ProfessionalSubSpecialization domain,
            ProfessionalJpaEntity professional,
            SubSpecializationJpaEntity subSpecialization
    ) {

        ProfessionalSubSpecializationJpaEntity entity =
                new ProfessionalSubSpecializationJpaEntity();

        entity.id = domain.getId();

        entity.professional = professional;

        entity.subSpecialization = subSpecialization;

        return entity;
    }

    public static ProfessionalSubSpecialization toDomain(
            ProfessionalSubSpecializationJpaEntity entity
    ) {

        return ProfessionalSubSpecialization.restore(
                entity.id,
                entity.professional.id,
                entity.subSpecialization.id
        );
    }
}