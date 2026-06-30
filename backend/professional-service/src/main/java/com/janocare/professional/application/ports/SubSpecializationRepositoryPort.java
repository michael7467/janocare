package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.SubSpecialization;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubSpecializationRepositoryPort {

    SubSpecialization save(
            SubSpecialization subSpecialization
    );

    Optional<SubSpecialization> findDomainById(UUID id);

    List<SubSpecialization> findBySpecializationId(
            UUID specializationId
    );
    Optional<SubSpecialization> findByName(String name);
    List<SubSpecialization> findAllSubSpecializations();

    void deleteSubSpecializationById(UUID id);
}