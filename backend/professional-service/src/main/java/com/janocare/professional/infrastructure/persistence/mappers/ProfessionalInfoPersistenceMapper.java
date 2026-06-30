package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalInfo;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalInfoJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;

public class ProfessionalInfoPersistenceMapper {

    private ProfessionalInfoPersistenceMapper() {}

    public static ProfessionalInfoJpaEntity toJpaEntity(
            ProfessionalInfo domain,
            ProfessionalJpaEntity professional
    ) {

        ProfessionalInfoJpaEntity entity =
                new ProfessionalInfoJpaEntity();

        entity.id = domain.getId();

        entity.professional = professional;

        entity.institutionName = domain.getInstitutionName();

        entity.officeNumber = domain.getOfficeNumber();

        entity.daysOfWeek = domain.getDaysOfWeek();

        entity.startTime = domain.getStartTime();

        entity.endTime = domain.getEndTime();

        entity.available = domain.isAvailable();

        return entity;
    }

    public static ProfessionalInfo toDomain(
            ProfessionalInfoJpaEntity entity
    ) {

        return ProfessionalInfo.restore(
                entity.id,
                entity.professional.id,
                entity.institutionName,
                entity.officeNumber,
                entity.daysOfWeek,
                entity.startTime,
                entity.endTime,
                entity.available
        );
    }
}