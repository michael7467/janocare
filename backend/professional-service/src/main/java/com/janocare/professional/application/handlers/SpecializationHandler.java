package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.SpecializationApiMapper;
import com.janocare.professional.api.responses.specialization.SpecializationResponse;
import com.janocare.professional.application.commands.specialization.CreateSpecializationCommand;
import com.janocare.professional.application.commands.specialization.DeleteSpecializationCommand;
import com.janocare.professional.application.commands.specialization.UpdateSpecializationCommand;
import com.janocare.professional.application.ports.SpecializationRepositoryPort;
import com.janocare.professional.application.queries.specialization.FindAllSpecializationsQuery;
import com.janocare.professional.application.queries.specialization.FindSpecializationByIdQuery;
import com.janocare.professional.domain.entities.Specialization;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SpecializationHandler {

    @Inject
    SpecializationRepositoryPort specializationRepository;

    @Transactional
    public SpecializationResponse create(
            CreateSpecializationCommand command
    ) {

        if (command == null) {
            throw new ValidationException(
                    "Command body is required"
            );
        }

        if (
                command.name == null ||
                        command.name.isBlank()
        ) {
            throw new ValidationException(
                    "Specialization name is required"
            );
        }

        Specialization specialization =
                Specialization.create(
                        command.name,
                        command.description
                );

        Specialization saved =
                specializationRepository.save(
                        specialization
                );

        return SpecializationApiMapper
                .toResponse(saved);
    }

    @Transactional
    public SpecializationResponse update(
            UpdateSpecializationCommand command
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

        Specialization specialization =
                specializationRepository.findDomainById(
                        command.specializationId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Specialization not found"
                        )
                );

        specialization.update(
                command.name,
                command.description
        );

        Specialization saved =
                specializationRepository.save(
                        specialization
                );

        return SpecializationApiMapper
                .toResponse(saved);
    }

    @Transactional
    public void delete(
            DeleteSpecializationCommand command
    ) {

        if (
                command == null ||
                        command.specializationId == null
        ) {
            throw new ValidationException(
                    "Specialization ID is required"
            );
        }

        specializationRepository.findDomainById(
                command.specializationId
        ).orElseThrow(() ->
                new ValidationException(
                        "Specialization not found"
                )
        );

        specializationRepository
                .deleteSpecializationById(
                        command.specializationId
                );
    }

    public SpecializationResponse findById(
            FindSpecializationByIdQuery query
    ) {

        if (
                query == null ||
                        query.specializationId == null
        ) {
            throw new ValidationException(
                    "Specialization ID is required"
            );
        }

        Specialization specialization =
                specializationRepository.findDomainById(
                        query.specializationId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Specialization not found"
                        )
                );

        return SpecializationApiMapper
                .toResponse(specialization);
    }

    public List<SpecializationResponse> findAll(
            FindAllSpecializationsQuery query
    ) {

        return specializationRepository.findAllSpecializations()
                .stream()
                .map(
                        SpecializationApiMapper::toResponse
                )
                .toList();
    }
}