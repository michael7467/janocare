package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalQualificationApiMapper;
import com.janocare.professional.api.responses.professionalqualification.ProfessionalQualificationResponse;
import com.janocare.professional.application.commands.professionalqualification.CreateProfessionalQualificationCommand;
import com.janocare.professional.application.commands.professionalqualification.DeleteProfessionalQualificationCommand;
import com.janocare.professional.application.commands.professionalqualification.UpdateProfessionalQualificationCommand;
import com.janocare.professional.application.ports.ProfessionalQualificationRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.queries.professionalqualification.FindAllProfessionalQualificationsQuery;
import com.janocare.professional.application.queries.professionalqualification.FindProfessionalQualificationByIdQuery;
import com.janocare.professional.application.queries.professionalqualification.FindProfessionalQualificationsByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalQualification;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalQualificationHandler {

    @Inject
    ProfessionalQualificationRepositoryPort qualificationRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Transactional
    public ProfessionalQualificationResponse create(CreateProfessionalQualificationCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        ProfessionalQualification qualification = ProfessionalQualification.create(
                command.professionalId,
                command.qualificationName,
                command.institutionName,
                command.procurementYear
        );

        ProfessionalQualification saved = qualificationRepository.save(qualification);

        return ProfessionalQualificationApiMapper.toResponse(saved);
    }

    @Transactional
    public ProfessionalQualificationResponse update(UpdateProfessionalQualificationCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.qualificationId == null) {
            throw new ValidationException("Qualification ID is required");
        }

        ProfessionalQualification qualification = qualificationRepository.findDomainById(command.qualificationId)
                .orElseThrow(() -> new ValidationException("Qualification not found"));

        qualification.update(
                command.qualificationName,
                command.institutionName,
                command.procurementYear
        );

        ProfessionalQualification saved = qualificationRepository.save(qualification);

        return ProfessionalQualificationApiMapper.toResponse(saved);
    }

    @Transactional
    public void delete(DeleteProfessionalQualificationCommand command) {
        if (command == null || command.qualificationId == null) {
            throw new ValidationException("Qualification ID is required");
        }

        qualificationRepository.findDomainById(command.qualificationId)
                .orElseThrow(() -> new ValidationException("Qualification not found"));

        qualificationRepository.deleteQualificationById(command.qualificationId);
    }

    public ProfessionalQualificationResponse findById(FindProfessionalQualificationByIdQuery query) {
        if (query == null || query.qualificationId == null) {
            throw new ValidationException("Qualification ID is required");
        }

        ProfessionalQualification qualification = qualificationRepository.findDomainById(query.qualificationId)
                .orElseThrow(() -> new ValidationException("Qualification not found"));

        return ProfessionalQualificationApiMapper.toResponse(qualification);
    }

    public List<ProfessionalQualificationResponse> findByProfessionalId(
            FindProfessionalQualificationsByProfessionalIdQuery query
    ) {
        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        return qualificationRepository.findByProfessionalId(query.professionalId)
                .stream()
                .map(ProfessionalQualificationApiMapper::toResponse)
                .toList();
    }

    public List<ProfessionalQualificationResponse> findAll(FindAllProfessionalQualificationsQuery query) {
        return qualificationRepository.findAllQualifications()
                .stream()
                .map(ProfessionalQualificationApiMapper::toResponse)
                .toList();
    }
}