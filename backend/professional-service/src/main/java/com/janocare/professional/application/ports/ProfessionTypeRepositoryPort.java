package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionTypeRepositoryPort {

    ProfessionType save(ProfessionType professionType);

    Optional<ProfessionType> findDomainById(UUID id);
    Optional<ProfessionType> findByType(String type);
    List<ProfessionType> findAllProfessionTypes();

    void deleteProfessionTypeById(UUID id);
}