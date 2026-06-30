package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalSubSpecialization;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalSubSpecializationRepositoryPort {

    ProfessionalSubSpecialization save(
            ProfessionalSubSpecialization subSpecialization
    );

    Optional<ProfessionalSubSpecialization> findDomainById(UUID id);

    List<ProfessionalSubSpecialization> findByProfessionalId(
            UUID professionalId
    );

    List<ProfessionalSubSpecialization> findAllProfessionalSubSpecialization();

    void deleteProfessionalSubSpecializationById(UUID id);
}