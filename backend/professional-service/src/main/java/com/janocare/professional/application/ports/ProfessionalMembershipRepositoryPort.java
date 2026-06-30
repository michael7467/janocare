package com.janocare.professional.application.ports;

import com.janocare.professional.domain.entities.ProfessionalMembership;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessionalMembershipRepositoryPort {

    ProfessionalMembership save(ProfessionalMembership membership);

    Optional<ProfessionalMembership> findDomainById(UUID id);

    List<ProfessionalMembership> findByProfessionalId(UUID professionalId);

    List<ProfessionalMembership> findAllMembership();

    void deleteMembershipById(UUID id);
}