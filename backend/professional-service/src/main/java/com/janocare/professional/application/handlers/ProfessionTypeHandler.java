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
import com.janocare.professional.domain.exceptions.ProfessionTypeNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class ProfessionTypeHandler {

    private static final Logger LOG =
            Logger.getLogger(ProfessionTypeHandler.class);

    @Inject
    ProfessionTypeRepositoryPort professionTypeRepository;

    // =====================================================
    // CREATE
    // Accountability pattern: admin adds a new knowledge-level
    // object that governs operational-level slot generation
    // =====================================================

    @Transactional
    public ProfessionalTypeResponse create(
            CreateProfessionalTypeCommand command) {

        if (command == null)
            throw new ValidationException("Command body is required");

        if (command.name == null || command.name.isBlank())
            throw new ValidationException(
                    "Profession type name is required");

        if (command.slotInterval == null || command.slotInterval <= 0)
            throw new ValidationException(
                    "Slot interval must be a positive number of minutes");

        // guard — prevent duplicate name
        professionTypeRepository.findByName(command.name.trim())
                .ifPresent(existing -> {
                    throw new ValidationException(
                            "Profession type already exists: "
                                    + command.name);
                });

        ProfessionType professionType = ProfessionType.create(
                command.name,
                command.description,
                command.slotInterval
        );

        ProfessionType saved =
                professionTypeRepository.save(professionType);

        LOG.infof("Profession type created: %s with slot interval %d min",
                saved.getName(), saved.getSlotInterval());

        return ProfessionalTypeApiMapper.toResponse(saved);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @Transactional
    public ProfessionalTypeResponse update(
            UpdateProfessionalTypeCommand command) {

        if (command == null)
            throw new ValidationException("Command body is required");

        if (command.professionTypeId == null)
            throw new ValidationException(
                    "Profession type ID is required");

        ProfessionType professionType = professionTypeRepository
                .findDomainById(command.professionTypeId)
                .orElseThrow(() -> new ProfessionTypeNotFoundException(
                        "Profession type not found: "
                                + command.professionTypeId));

        // guard — prevent duplicate name on rename
        if (command.name != null && !command.name.isBlank()) {
            professionTypeRepository.findByName(command.name.trim())
                    .filter(existing -> !existing.getId()
                            .equals(command.professionTypeId))
                    .ifPresent(existing -> {
                        throw new ValidationException(
                                "Profession type name already exists: "
                                        + command.name);
                    });
        }

        // domain method enforces partial update + slot interval guard
        professionType.update(
                command.name,
                command.description,
                command.slotInterval,
                command.active
        );

        ProfessionType saved =
                professionTypeRepository.save(professionType);

        LOG.infof("Profession type updated: %s", command.professionTypeId);

        return ProfessionalTypeApiMapper.toResponse(saved);
    }

    // =====================================================
    // DELETE
    // =====================================================

    @Transactional
    public void delete(DeleteProfessionalTypeCommand command) {

        if (command == null || command.professionTypeId == null)
            throw new ValidationException(
                    "Profession type ID is required");

        professionTypeRepository
                .findDomainById(command.professionTypeId)
                .orElseThrow(() -> new ProfessionTypeNotFoundException(
                        "Profession type not found: "
                                + command.professionTypeId));

        professionTypeRepository.deleteProfessionTypeById(
                command.professionTypeId);

        LOG.infof("Profession type deleted: %s",
                command.professionTypeId);
    }

    // =====================================================
    // FIND BY ID
    // =====================================================

    public ProfessionalTypeResponse findById(
            FindProfessionalTypeByIdQuery query) {

        if (query == null || query.professionTypeId == null)
            throw new ValidationException(
                    "Profession type ID is required");

        ProfessionType professionType = professionTypeRepository
                .findDomainById(query.professionTypeId)
                .orElseThrow(() -> new ProfessionTypeNotFoundException(
                        "Profession type not found: "
                                + query.professionTypeId));

        return ProfessionalTypeApiMapper.toResponse(professionType);
    }

    // =====================================================
    // FIND ALL — with optional filtering
    // =====================================================

    public List<ProfessionalTypeResponse> findAll(
            FindAllProfessionalTypesQuery query) {

        return professionTypeRepository
                .findAllProfessionTypes()
                .stream()
                .filter(pt -> query.search == null
                        || pt.getName().toLowerCase()
                                .contains(query.search.toLowerCase()))
                .filter(pt -> query.active == null
                        || pt.isActive() == query.active)
                .map(ProfessionalTypeApiMapper::toResponse)
                .toList();
    }
}