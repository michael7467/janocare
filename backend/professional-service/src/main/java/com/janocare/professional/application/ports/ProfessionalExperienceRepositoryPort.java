package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalExperience;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalExperienceRepositoryPort {

    ProfessionalExperience save(ProfessionalExperience experience);

    Optional<ProfessionalExperience> findDomainById(UUID id);

    List<ProfessionalExperience> findByProfessionalId(UUID professionalId);

    List<ProfessionalExperience> findAllExperiences();

    void deleteExperienceById(UUID id);
}