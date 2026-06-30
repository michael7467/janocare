package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalSpecialization;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalSpecializationRepositoryPort {

    ProfessionalSpecialization save(
            ProfessionalSpecialization specialization
    );

    Optional<ProfessionalSpecialization> findDomainById(UUID id);

    List<ProfessionalSpecialization> findByProfessionalId(
            UUID professionalId
    );
    void deleteProfessionalSpecializationById(UUID id);
    List<ProfessionalSpecialization> findAllProfessionalSpecialization();


}