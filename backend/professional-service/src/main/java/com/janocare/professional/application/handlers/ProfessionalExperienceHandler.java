package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalExperienceApiMapper;
import com.janocare.professional.api.responses.professionalexperience.ProfessionalExperienceResponse;
import com.janocare.professional.application.commands.professionalexperience.CreateProfessionalExperienceCommand;
import com.janocare.professional.application.commands.professionalexperience.DeleteProfessionalExperienceCommand;
import com.janocare.professional.application.commands.professionalexperience.UpdateProfessionalExperienceCommand;
import com.janocare.professional.application.ports.ProfessionalExperienceRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.queries.professionalexperience.FindAllProfessionalExperiencesQuery;
import com.janocare.professional.application.queries.professionalexperience.FindProfessionalExperienceByIdQuery;
import com.janocare.professional.application.queries.professionalexperience.FindProfessionalExperiencesByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalExperience;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalExperienceHandler {

    @Inject
    ProfessionalExperienceRepositoryPort experienceRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Transactional
    public ProfessionalExperienceResponse create(
            CreateProfessionalExperienceCommand command
    ) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        ProfessionalExperience experience =
                ProfessionalExperience.create(
                        command.professionalId,
                        command.experience,
                        command.specialization,
                        command.place,
                        command.startYear,
                        command.endYear
                );

        ProfessionalExperience saved =
                experienceRepository.save(experience);

        return ProfessionalExperienceApiMapper.toResponse(saved);
    }

    @Transactional
    public ProfessionalExperienceResponse update(
            UpdateProfessionalExperienceCommand command
    ) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.experienceId == null) {
            throw new ValidationException("Experience ID is required");
        }

        ProfessionalExperience experience =
                experienceRepository.findDomainById(command.experienceId)
                        .orElseThrow(() ->
                                new ValidationException("Experience not found"));

        experience.update(
                command.experience,
                command.specialization,
                command.place,
                command.startYear,
                command.endYear
        );

        ProfessionalExperience saved =
                experienceRepository.save(experience);

        return ProfessionalExperienceApiMapper.toResponse(saved);
    }

    @Transactional
    public void delete(
            DeleteProfessionalExperienceCommand command
    ) {
        if (command == null || command.experienceId == null) {
            throw new ValidationException("Experience ID is required");
        }

        experienceRepository.findDomainById(command.experienceId)
                .orElseThrow(() ->
                        new ValidationException("Experience not found"));

        experienceRepository.deleteExperienceById(command.experienceId);
    }

    public ProfessionalExperienceResponse findById(
            FindProfessionalExperienceByIdQuery query
    ) {
        if (query == null || query.experienceId == null) {
            throw new ValidationException("Experience ID is required");
        }

        ProfessionalExperience experience =
                experienceRepository.findDomainById(query.experienceId)
                        .orElseThrow(() ->
                                new ValidationException("Experience not found"));

        return ProfessionalExperienceApiMapper.toResponse(experience);
    }

    public List<ProfessionalExperienceResponse> findByProfessionalId(
            FindProfessionalExperiencesByProfessionalIdQuery query
    ) {
        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        return experienceRepository.findByProfessionalId(query.professionalId)
                .stream()
                .map(ProfessionalExperienceApiMapper::toResponse)
                .toList();
    }

    public List<ProfessionalExperienceResponse> findAll(
            FindAllProfessionalExperiencesQuery query
    ) {
        return experienceRepository.findAllExperiences()
                .stream()
                .map(ProfessionalExperienceApiMapper::toResponse)
                .toList();
    }
}