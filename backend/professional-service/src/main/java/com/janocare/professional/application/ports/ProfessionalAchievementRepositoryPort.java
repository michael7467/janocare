package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalAchievement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalAchievementRepositoryPort {

    ProfessionalAchievement save(ProfessionalAchievement achievement);

    Optional<ProfessionalAchievement> findDomainById(UUID id);

    List<ProfessionalAchievement> findByProfessionalId(UUID professionalId);

    List<ProfessionalAchievement> findAllAchievements();

    void deleteAchievementById(UUID id);
}