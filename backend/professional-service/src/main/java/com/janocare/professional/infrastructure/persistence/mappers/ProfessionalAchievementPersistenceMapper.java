package com.janocare.professional.infrastructure.persistence.mappers;

import com.janocare.professional.domain.entities.ProfessionalAchievement;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalAchievementJpaEntity;
import com.janocare.professional.infrastructure.persistence.entities.ProfessionalJpaEntity;

public class ProfessionalAchievementPersistenceMapper {

    private ProfessionalAchievementPersistenceMapper() {}

    public static ProfessionalAchievementJpaEntity toJpaEntity(
            ProfessionalAchievement domain,
            ProfessionalJpaEntity professional
    ) {
        ProfessionalAchievementJpaEntity entity =
                new ProfessionalAchievementJpaEntity();

        entity.id = domain.getId();
        entity.professional = professional;
        entity.awardOrRecognitionName = domain.getAwardOrRecognitionName();
        entity.awardDescription = domain.getAwardDescription();
        entity.awardYear = domain.getAwardYear();

        return entity;
    }

    public static ProfessionalAchievement toDomain(
            ProfessionalAchievementJpaEntity entity
    ) {
        return ProfessionalAchievement.restore(
                entity.id,
                entity.professional.id,
                entity.awardOrRecognitionName,
                entity.awardDescription,
                entity.awardYear
        );
    }
}