package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalSubSpecializationApiMapper;
import com.janocare.professional.api.responses.professionalsubspecialization.ProfessionalSubSpecializationResponse;
import com.janocare.professional.application.commands.professionalsubspecialization.CreateProfessionalSubSpecializationCommand;
import com.janocare.professional.application.commands.professionalsubspecialization.DeleteProfessionalSubSpecializationCommand;
import com.janocare.professional.application.commands.professionalsubspecialization.UpdateProfessionalSubSpecializationCommand;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalSubSpecializationRepositoryPort;
import com.janocare.professional.application.ports.SubSpecializationRepositoryPort;
import com.janocare.professional.application.queries.professionalsubspecialization.FindAllProfessionalSubSpecializationsQuery;
import com.janocare.professional.application.queries.professionalsubspecialization.FindProfessionalSubSpecializationByIdQuery;
import com.janocare.professional.application.queries.professionalsubspecialization.FindProfessionalSubSpecializationsByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalSubSpecialization;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalSubSpecializationHandler {

    @Inject
    ProfessionalSubSpecializationRepositoryPort
            subSpecializationRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Inject
    SubSpecializationRepositoryPort
            masterSubSpecializationRepository;

    @Transactional
    public ProfessionalSubSpecializationResponse create(
            CreateProfessionalSubSpecializationCommand command
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

        if (command.subSpecializationId == null) {
            throw new ValidationException(
                    "Sub specialization ID is required"
            );
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(
                        ProfessionalNotFoundException::new
                );

        masterSubSpecializationRepository.findDomainById(
                command.subSpecializationId
        ).orElseThrow(() ->
                new ValidationException(
                        "Sub specialization not found"
                )
        );

        ProfessionalSubSpecialization subSpecialization =
                ProfessionalSubSpecialization.create(
                        command.professionalId,
                        command.subSpecializationId
                );

        ProfessionalSubSpecialization saved =
                subSpecializationRepository.save(
                        subSpecialization
                );

        return ProfessionalSubSpecializationApiMapper
                .toResponse(saved);
    }

    @Transactional
    public ProfessionalSubSpecializationResponse update(
            UpdateProfessionalSubSpecializationCommand command
    ) {

        if (command == null) {
            throw new ValidationException(
                    "Command body is required"
            );
        }

        if (
                command.professionalSubSpecializationId
                        == null
        ) {
            throw new ValidationException(
                    "Professional sub specialization ID is required"
            );
        }

        ProfessionalSubSpecialization subSpecialization =
                subSpecializationRepository.findDomainById(
                        command.professionalSubSpecializationId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Professional sub specialization not found"
                        )
                );

        subSpecialization.update(
                command.subSpecializationId
        );

        ProfessionalSubSpecialization saved =
                subSpecializationRepository.save(
                        subSpecialization
                );

        return ProfessionalSubSpecializationApiMapper
                .toResponse(saved);
    }

    @Transactional
    public void delete(
            DeleteProfessionalSubSpecializationCommand command
    ) {

        if (
                command == null ||
                        command.professionalSubSpecializationId
                                == null
        ) {
            throw new ValidationException(
                    "Professional sub specialization ID is required"
            );
        }

        subSpecializationRepository.findDomainById(
                command.professionalSubSpecializationId
        ).orElseThrow(() ->
                new ValidationException(
                        "Professional sub specialization not found"
                )
        );

        subSpecializationRepository
                .deleteProfessionalSubSpecializationById(
                        command.professionalSubSpecializationId
                );
    }

    public ProfessionalSubSpecializationResponse findById(
            FindProfessionalSubSpecializationByIdQuery query
    ) {

        if (
                query == null ||
                        query.professionalSubSpecializationId
                                == null
        ) {
            throw new ValidationException(
                    "Professional sub specialization ID is required"
            );
        }

        ProfessionalSubSpecialization subSpecialization =
                subSpecializationRepository.findDomainById(
                        query.professionalSubSpecializationId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Professional sub specialization not found"
                        )
                );

        return ProfessionalSubSpecializationApiMapper
                .toResponse(subSpecialization);
    }

    public List<ProfessionalSubSpecializationResponse>
    findByProfessionalId(
            FindProfessionalSubSpecializationsByProfessionalIdQuery query
    ) {

        if (
                query == null ||
                        query.professionalId == null
        ) {
            throw new ValidationException(
                    "Professional ID is required"
            );
        }

        return subSpecializationRepository
                .findByProfessionalId(
                        query.professionalId
                )
                .stream()
                .map(
                        ProfessionalSubSpecializationApiMapper
                                ::toResponse
                )
                .toList();
    }

    public List<ProfessionalSubSpecializationResponse>
    findAll(
            FindAllProfessionalSubSpecializationsQuery query
    ) {

        return subSpecializationRepository.findAllProfessionalSubSpecialization()
                .stream()
                .map(
                        ProfessionalSubSpecializationApiMapper
                                ::toResponse
                )
                .toList();
    }
}