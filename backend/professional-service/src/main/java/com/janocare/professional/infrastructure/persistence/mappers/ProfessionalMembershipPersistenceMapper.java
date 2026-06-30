package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalMembership;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalMembershipJpaEntity;

public class ProfessionalMembershipPersistenceMapper {

    private ProfessionalMembershipPersistenceMapper() {}

    public static ProfessionalMembershipJpaEntity toJpaEntity(
            ProfessionalMembership domain,
            ProfessionalJpaEntity professional
    ) {
        ProfessionalMembershipJpaEntity entity = new ProfessionalMembershipJpaEntity();

        entity.id = domain.getId();
        entity.professional = professional;
        entity.membershipName = domain.getMembershipName();
        entity.membershipDescription = domain.getMembershipDescription();
        entity.membershipYear = domain.getMembershipYear();

        return entity;
    }

    public static ProfessionalMembership toDomain(ProfessionalMembershipJpaEntity entity) {
        return ProfessionalMembership.restore(
                entity.id,
                entity.professional.id,
                entity.membershipName,
                entity.membershipDescription,
                entity.membershipYear
        );
    }
}