package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalServiceRepositoryPort {

    ProfessionalService save(ProfessionalService service);

    Optional<ProfessionalService> findDomainById(UUID id);

    List<ProfessionalService> findByProfessionalId(UUID professionalId);

    List<ProfessionalService> findAllProfessionalServices();

    void deleteServiceById(UUID id);
}