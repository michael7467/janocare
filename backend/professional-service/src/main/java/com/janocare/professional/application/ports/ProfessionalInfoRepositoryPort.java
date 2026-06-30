package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalInfoRepositoryPort {

    ProfessionalInfo save(ProfessionalInfo info);

    Optional<ProfessionalInfo> findDomainById(UUID id);

    List<ProfessionalInfo> findByProfessionalId(UUID professionalId);
    List<ProfessionalInfo> findAllProfessionalInfo();
    void deleteInfoById(UUID id);
}