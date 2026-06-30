package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalSpecializationApiMapper;
import com.janocare.professional.api.responses.professionalspecialization.ProfessionalSpecializationResponse;
import com.janocare.professional.application.commands.professionalspecialization.CreateProfessionalSpecializationCommand;
import com.janocare.professional.application.commands.professionalspecialization.DeleteProfessionalSpecializationCommand;
import com.janocare.professional.application.commands.professionalspecialization.UpdateProfessionalSpecializationCommand;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalSpecializationRepositoryPort;
import com.janocare.professional.application.ports.SpecializationRepositoryPort;
import com.janocare.professional.application.queries.professionalspecialization.FindAllProfessionalSpecializationsQuery;
import com.janocare.professional.application.queries.professionalspecialization.FindProfessionalSpecializationByIdQuery;
import com.janocare.professional.application.queries.professionalspecialization.FindProfessionalSpecializationsByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalSpecialization;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalSpecializationHandler {

    @Inject
    ProfessionalSpecializationRepositoryPort specializationRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Inject
    SpecializationRepositoryPort masterSpecializationRepository;

    @Transactional
    public ProfessionalSpecializationResponse create(
            CreateProfessionalSpecializationCommand command
    ) {

        if (command == null) {
            throw new ValidationException(
                    "Command body is required"
            );
        }

        if (command.professionalId == null) {
            throw new ValidationException(
                    "Professional ID is required"
            );
        }

        if (command.specializationId == null) {
            throw new ValidationException(
                    "Specialization ID is required"
            );
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(
                        ProfessionalNotFoundException::new
                );

        masterSpecializationRepository.findDomainById(
                command.specializationId
        ).orElseThrow(() ->
                new ValidationException(
                        "Specialization not found"
                )
        );

        ProfessionalSpecialization specialization =
                ProfessionalSpecialization.create(
                        command.professionalId,
                        command.specializationId
                );

        ProfessionalSpecialization saved =
                specializationRepository.save(specialization);

        return ProfessionalSpecializationApiMapper
                .toResponse(saved);
    }

    @Transactional
    public ProfessionalSpecializationResponse update(
            UpdateProfessionalSpecializationCommand command
    ) {

        if (command == null) {
            throw new ValidationException(
                    "Command body is required"
            );
        }

        if (command.professionalSpecializationId == null) {
            throw new ValidationException(
                    "Professional specialization ID is required"
            );
        }

        ProfessionalSpecialization specialization =
                specializationRepository.findDomainById(
                        command.professionalSpecializationId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Professional specialization not found"
                        )
                );

        specialization.update(command.specializationId);

        ProfessionalSpecialization saved =
                specializationRepository.save(specialization);

        return ProfessionalSpecializationApiMapper
                .toResponse(saved);
    }

    @Transactional
    public void delete(
            DeleteProfessionalSpecializationCommand command
    ) {

        if (
                command == null ||
                        command.professionalSpecializationId == null
        ) {
            throw new ValidationException(
                    "Professional specialization ID is required"
            );
        }

        specializationRepository.findDomainById(
                command.professionalSpecializationId
        ).orElseThrow(() ->
                new ValidationException(
                        "Professional specialization not found"
                )
        );

        specializationRepository
                .deleteProfessionalSpecializationById(
                        command.professionalSpecializationId
                );
    }

    public ProfessionalSpecializationResponse findById(
            FindProfessionalSpecializationByIdQuery query
    ) {

        if (
                query == null ||
                        query.professionalSpecializationId == null
        ) {
            throw new ValidationException(
                    "Professional specialization ID is required"
            );
        }

        ProfessionalSpecialization specialization =
                specializationRepository.findDomainById(
                        query.professionalSpecializationId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Professional specialization not found"
                        )
                );

        return ProfessionalSpecializationApiMapper
                .toResponse(specialization);
    }

    public List<ProfessionalSpecializationResponse>
    findByProfessionalId(
            FindProfessionalSpecializationsByProfessionalIdQuery query
    ) {

        if (
                query == null ||
                        query.professionalId == null
        ) {
            throw new ValidationException(
                    "Professional ID is required"
            );
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(
                        ProfessionalNotFoundException::new
                );

        return specializationRepository
                .findByProfessionalId(query.professionalId)
                .stream()
                .map(
                        ProfessionalSpecializationApiMapper::toResponse
                )
                .toList();
    }

    public List<ProfessionalSpecializationResponse> findAll(
            FindAllProfessionalSpecializationsQuery query
    ) {

        return specializationRepository.findAllProfessionalSpecialization()
                .stream()
                .map(
                        ProfessionalSpecializationApiMapper::toResponse
                )
                .toList();
    }
}