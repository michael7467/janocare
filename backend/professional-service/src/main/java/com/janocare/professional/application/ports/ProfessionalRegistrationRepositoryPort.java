package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalRegistration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRegistrationRepositoryPort {

    ProfessionalRegistration save(ProfessionalRegistration registration);

    Optional<ProfessionalRegistration> findDomainById(UUID id);

    List<ProfessionalRegistration> findByProfessionalId(UUID professionalId);

    List<ProfessionalRegistration> findAllProfessionalRegistrations();

    void deleteRegistrationById(UUID id);
}