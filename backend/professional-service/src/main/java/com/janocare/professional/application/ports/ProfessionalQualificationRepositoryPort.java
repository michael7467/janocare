package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalQualification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalQualificationRepositoryPort {

    ProfessionalQualification save(ProfessionalQualification qualification);

    Optional<ProfessionalQualification> findDomainById(UUID id);

    List<ProfessionalQualification> findByProfessionalId(UUID professionalId);

    List<ProfessionalQualification> findAllQualifications();

    void deleteQualificationById(UUID id);
}