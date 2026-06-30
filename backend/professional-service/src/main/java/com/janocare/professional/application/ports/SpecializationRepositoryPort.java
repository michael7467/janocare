package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.Specialization;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpecializationRepositoryPort {

    Specialization save(
            Specialization specialization
    );

    Optional<Specialization> findDomainById(UUID id);

    List<Specialization> findAllSpecializations();
    Optional<Specialization> findByName(String name);
    void deleteSpecializationById(UUID id);
}