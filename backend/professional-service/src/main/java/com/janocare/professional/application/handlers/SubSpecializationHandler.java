package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.SubSpecializationApiMapper;
import com.janocare.professional.api.responses.subspecialization.SubSpecializationResponse;
import com.janocare.professional.application.commands.subspecialization.CreateSubSpecializationCommand;
import com.janocare.professional.application.commands.subspecialization.DeleteSubSpecializationCommand;
import com.janocare.professional.application.commands.subspecialization.UpdateSubSpecializationCommand;
import com.janocare.professional.application.ports.SpecializationRepositoryPort;
import com.janocare.professional.application.ports.SubSpecializationRepositoryPort;
import com.janocare.professional.application.queries.subspecialization.FindAllSubSpecializationsQuery;
import com.janocare.professional.application.queries.subspecialization.FindSubSpecializationByIdQuery;
import com.janocare.professional.application.queries.subspecialization.FindSubSpecializationsBySpecializationIdQuery;
import com.janocare.professional.domain.entities.SubSpecialization;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SubSpecializationHandler {

    @Inject
    SubSpecializationRepositoryPort
            subSpecializationRepository;

    @Inject
    SpecializationRepositoryPort
            specializationRepository;

    @Transactional
    public SubSpecializationResponse create(
            CreateSubSpecializationCommand command
    ) {

        if (command == null) {
            throw new ValidationException(
                    "Command body is required"
            );
        }

        if (command.specializationId == null) {
            throw new ValidationException(
                    "Specialization ID is required"
            );
        }

        if (
                command.name == null ||
                        command.name.isBlank()
        ) {
            throw new ValidationException(
                    "Sub specialization name is required"
            );
        }

        specializationRepository.findDomainById(
                command.specializationId
        ).orElseThrow(() ->
                new ValidationException(
                        "Specialization not found"
                )
        );

        SubSpecialization subSpecialization =
                SubSpecialization.create(
                        command.specializationId,
                        command.name,
                        command.description
                );

        SubSpecialization saved =
                subSpecializationRepository.save(
                        subSpecialization
                );

        return SubSpecializationApiMapper
                .toResponse(saved);
    }

    @Transactional
    public SubSpecializationResponse update(
            UpdateSubSpecializationCommand command
    ) {

        if (command == null) {
            throw new ValidationException(
                    "Command body is required"
            );
        }

        if (command.subSpecializationId == null) {
            throw new ValidationException(
                    "Sub specialization ID is required"
            );
        }

        SubSpecialization subSpecialization =
                subSpecializationRepository.findDomainById(
                        command.subSpecializationId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Sub specialization not found"
                        )
                );

        subSpecialization.update(
                command.name,
                command.description
        );

        SubSpecialization saved =
                subSpecializationRepository.save(
                        subSpecialization
                );

        return SubSpecializationApiMapper
                .toResponse(saved);
    }

    @Transactional
    public void delete(
            DeleteSubSpecializationCommand command
    ) {

        if (
                command == null ||
                        command.subSpecializationId == null
        ) {
            throw new ValidationException(
                    "Sub specialization ID is required"
            );
        }

        subSpecializationRepository.findDomainById(
                command.subSpecializationId
        ).orElseThrow(() ->
                new ValidationException(
                        "Sub specialization not found"
                )
        );

        subSpecializationRepository
                .deleteSubSpecializationById(
                        command.subSpecializationId
                );
    }

    public SubSpecializationResponse findById(
            FindSubSpecializationByIdQuery query
    ) {

        if (
                query == null ||
                        query.subSpecializationId == null
        ) {
            throw new ValidationException(
                    "Sub specialization ID is required"
            );
        }

        SubSpecialization subSpecialization =
                subSpecializationRepository.findDomainById(
                        query.subSpecializationId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Sub specialization not found"
                        )
                );

        return SubSpecializationApiMapper
                .toResponse(subSpecialization);
    }

    public List<SubSpecializationResponse>
    findBySpecializationId(
            FindSubSpecializationsBySpecializationIdQuery query
    ) {

        if (
                query == null ||
                        query.specializationId == null
        ) {
            throw new ValidationException(
                    "Specialization ID is required"
            );
        }

        return subSpecializationRepository
                .findBySpecializationId(
                        query.specializationId
                )
                .stream()
                .map(
                        SubSpecializationApiMapper
                                ::toResponse
                )
                .toList();
    }

    public List<SubSpecializationResponse> findAll(
            FindAllSubSpecializationsQuery query
    ) {

        return subSpecializationRepository.findAllSubSpecializations()
                .stream()
                .map(
                        SubSpecializationApiMapper
                                ::toResponse
                )
                .toList();
    }
}