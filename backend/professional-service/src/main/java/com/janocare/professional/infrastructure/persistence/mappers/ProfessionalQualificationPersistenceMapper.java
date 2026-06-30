package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalQualification;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalQualificationJpaEntity;

public class ProfessionalQualificationPersistenceMapper {

    private ProfessionalQualificationPersistenceMapper() {}

    public static ProfessionalQualificationJpaEntity toJpaEntity(
            ProfessionalQualification domain,
            ProfessionalJpaEntity professional
    ) {

        ProfessionalQualificationJpaEntity entity =
                new ProfessionalQualificationJpaEntity();

        entity.id = domain.getId();

        entity.professional = professional;

        entity.qualificationName =
                domain.getQualificationName();

        entity.institutionName =
                domain.getInstitutionName();

        entity.procurementYear =
                domain.getProcurementYear();

        return entity;
    }

    public static ProfessionalQualification toDomain(
            ProfessionalQualificationJpaEntity entity
    ) {

        return ProfessionalQualification.restore(
                entity.id,
                entity.professional.id,
                entity.qualificationName,
                entity.institutionName,
                entity.procurementYear
        );
    }
}