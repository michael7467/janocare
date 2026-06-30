package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalTypeApiMapper;
import com.janocare.professional.api.responses.professiontype.ProfessionalTypeResponse;
import com.janocare.professional.application.commands.professiontype.CreateProfessionalTypeCommand;
import com.janocare.professional.application.commands.professiontype.DeleteProfessionalTypeCommand;
import com.janocare.professional.application.commands.professiontype.UpdateProfessionalTypeCommand;
import com.janocare.professional.application.ports.ProfessionTypeRepositoryPort;
import com.janocare.professional.application.queries.professiontype.FindAllProfessionalTypesQuery;
import com.janocare.professional.application.queries.professiontype.FindProfessionalTypeByIdQuery;
import com.janocare.professional.domain.entities.ProfessionType;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionTypeHandler {

    @Inject
    ProfessionTypeRepositoryPort professionTypeRepository;

    @Transactional
    public ProfessionalTypeResponse create(
            CreateProfessionalTypeCommand command
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
                    "Profession type name is required"
            );
        }

        ProfessionType professionType =
                ProfessionType.create(
                        command.name,
                        command.description
                );

        ProfessionType saved =
                professionTypeRepository.save(
                        professionType
                );

        return ProfessionalTypeApiMapper
                .toResponse(saved);
    }

    @Transactional
    public ProfessionalTypeResponse update(
            UpdateProfessionalTypeCommand command
    ) {

        if (command == null) {
            throw new ValidationException(
                    "Command body is required"
            );
        }

        if (command.professionTypeId == null) {
            throw new ValidationException(
                    "Profession type ID is required"
            );
        }

        ProfessionType professionType =
                professionTypeRepository.findDomainById(
                        command.professionTypeId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Profession type not found"
                        )
                );

        professionType.update(
                command.name,
                command.description
        );

        ProfessionType saved =
                professionTypeRepository.save(
                        professionType
                );

        return ProfessionalTypeApiMapper
                .toResponse(saved);
    }

    @Transactional
    public void delete(
            DeleteProfessionalTypeCommand command
    ) {

        if (
                command == null ||
                        command.professionTypeId == null
        ) {
            throw new ValidationException(
                    "Profession type ID is required"
            );
        }

        professionTypeRepository.findDomainById(
                command.professionTypeId
        ).orElseThrow(() ->
                new ValidationException(
                        "Profession type not found"
                )
        );

        professionTypeRepository
                .deleteProfessionTypeById(
                        command.professionTypeId
                );
    }

    public ProfessionalTypeResponse findById(
            FindProfessionalTypeByIdQuery query
    ) {

        if (
                query == null ||
                        query.professionTypeId == null
        ) {
            throw new ValidationException(
                    "Profession type ID is required"
            );
        }

        ProfessionType professionType =
                professionTypeRepository.findDomainById(
                        query.professionTypeId
                ).orElseThrow(() ->
                        new ValidationException(
                                "Profession type not found"
                        )
                );

        return ProfessionalTypeApiMapper
                .toResponse(professionType);
    }

    public List<ProfessionalTypeResponse> findAll(
            FindAllProfessionalTypesQuery query
    ) {

        return professionTypeRepository.findAllProfessionTypes()
                .stream()
                .map(
                        ProfessionalTypeApiMapper::toResponse
                )
                .toList();
    }
}