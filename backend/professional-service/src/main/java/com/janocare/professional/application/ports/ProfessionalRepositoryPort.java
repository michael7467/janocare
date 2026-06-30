package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.Professional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRepositoryPort {

    Professional save(Professional professional);

    Optional<Professional> findDomainById(UUID id);

    Optional<Professional> findByUserId(UUID userId);

    List<Professional> findAllProfessionals();

    void deleteProfessionalById(UUID id);
}