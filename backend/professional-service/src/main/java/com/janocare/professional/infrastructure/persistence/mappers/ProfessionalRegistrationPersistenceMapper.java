package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalRegistration;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalRegistrationJpaEntity;

public class ProfessionalRegistrationPersistenceMapper {

    private ProfessionalRegistrationPersistenceMapper() {}

    public static ProfessionalRegistrationJpaEntity toJpaEntity(
            ProfessionalRegistration domain,
            ProfessionalJpaEntity professional
    ) {

        ProfessionalRegistrationJpaEntity entity =
                new ProfessionalRegistrationJpaEntity();

        entity.id = domain.getId();

        entity.professional = professional;

        entity.registrationName =
                domain.getRegistrationName();

        entity.registrationDate =
                domain.getRegistrationDate();

        entity.certificatePhoto =
                domain.getCertificatePhoto();

        return entity;
    }

    public static ProfessionalRegistration toDomain(
            ProfessionalRegistrationJpaEntity entity
    ) {

        return ProfessionalRegistration.restore(
                entity.id,
                entity.professional.id,
                entity.registrationName,
                entity.registrationDate,
                entity.certificatePhoto
        );
    }
}