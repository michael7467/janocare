package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalService;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalServiceJpaEntity;

public class ProfessionalServicePersistenceMapper {

    private ProfessionalServicePersistenceMapper() {}

    public static ProfessionalServiceJpaEntity toJpaEntity(
            ProfessionalService domain,
            ProfessionalJpaEntity professional
    ) {
        ProfessionalServiceJpaEntity entity = new ProfessionalServiceJpaEntity();

        entity.id = domain.getId();
        entity.professional = professional;
        entity.serviceName = domain.getServiceName();

        return entity;
    }

    public static ProfessionalService toDomain(ProfessionalServiceJpaEntity entity) {
        return ProfessionalService.restore(
                entity.id,
                entity.professional.id,
                entity.serviceName
        );
    }
}