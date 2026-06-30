package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalExperience;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalExperienceJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;

public class ProfessionalExperiencePersistenceMapper {

    private ProfessionalExperiencePersistenceMapper() {}

    public static ProfessionalExperienceJpaEntity toJpaEntity(
            ProfessionalExperience domain,
            ProfessionalJpaEntity professional
    ) {
        ProfessionalExperienceJpaEntity entity = new ProfessionalExperienceJpaEntity();

        entity.id = domain.getId();
        entity.professional = professional;
        entity.experience = domain.getExperience();
        entity.specialization = domain.getSpecialization();
        entity.place = domain.getPlace();
        entity.startYear = domain.getStartYear();
        entity.endYear = domain.getEndYear();

        return entity;
    }

    public static ProfessionalExperience toDomain(ProfessionalExperienceJpaEntity entity) {
        return ProfessionalExperience.restore(
                entity.id,
                entity.professional.id,
                entity.experience,
                entity.specialization,
                entity.place,
                entity.startYear,
                entity.endYear
        );
    }
}