package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalMembershipApiMapper;
import com.janocare.professional.api.responses.professionalmembership.ProfessionalMembershipResponse;
import com.janocare.professional.application.commands.professionalmembership.CreateProfessionalMembershipCommand;
import com.janocare.professional.application.commands.professionalmembership.DeleteProfessionalMembershipCommand;
import com.janocare.professional.application.commands.professionalmembership.UpdateProfessionalMembershipCommand;
import com.janocare.professional.application.ports.ProfessionalMembershipRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.queries.professionalmembership.FindAllProfessionalMembershipsQuery;
import com.janocare.professional.application.queries.professionalmembership.FindProfessionalMembershipByIdQuery;
import com.janocare.professional.application.queries.professionalmembership.FindProfessionalMembershipsByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalMembership;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalMembershipHandler {

    @Inject
    ProfessionalMembershipRepositoryPort membershipRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Transactional
    public ProfessionalMembershipResponse create(CreateProfessionalMembershipCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        ProfessionalMembership membership = ProfessionalMembership.create(
                command.professionalId,
                command.membershipName,
                command.membershipDescription,
                command.membershipYear
        );

        ProfessionalMembership saved = membershipRepository.save(membership);

        return ProfessionalMembershipApiMapper.toResponse(saved);
    }

    @Transactional
    public ProfessionalMembershipResponse update(UpdateProfessionalMembershipCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.membershipId == null) {
            throw new ValidationException("Membership ID is required");
        }

        ProfessionalMembership membership = membershipRepository.findDomainById(command.membershipId)
                .orElseThrow(() -> new ValidationException("Membership not found"));

        membership.update(
                command.membershipName,
                command.membershipDescription,
                command.membershipYear
        );

        ProfessionalMembership saved = membershipRepository.save(membership);

        return ProfessionalMembershipApiMapper.toResponse(saved);
    }

    @Transactional
    public void delete(DeleteProfessionalMembershipCommand command) {
        if (command == null || command.membershipId == null) {
            throw new ValidationException("Membership ID is required");
        }

        membershipRepository.findDomainById(command.membershipId)
                .orElseThrow(() -> new ValidationException("Membership not found"));

        membershipRepository.deleteMembershipById(command.membershipId);
    }

    public ProfessionalMembershipResponse findById(FindProfessionalMembershipByIdQuery query) {
        if (query == null || query.membershipId == null) {
            throw new ValidationException("Membership ID is required");
        }

        ProfessionalMembership membership = membershipRepository.findDomainById(query.membershipId)
                .orElseThrow(() -> new ValidationException("Membership not found"));

        return ProfessionalMembershipApiMapper.toResponse(membership);
    }

    public List<ProfessionalMembershipResponse> findByProfessionalId(
            FindProfessionalMembershipsByProfessionalIdQuery query
    ) {
        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        return membershipRepository.findByProfessionalId(query.professionalId)
                .stream()
                .map(ProfessionalMembershipApiMapper::toResponse)
                .toList();
    }

    public List<ProfessionalMembershipResponse> findAll(FindAllProfessionalMembershipsQuery query) {
        return membershipRepository.findAllMembership()
                .stream()
                .map(ProfessionalMembershipApiMapper::toResponse)
                .toList();
    }
}