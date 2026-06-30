package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalRegistrationApiMapper;
import com.janocare.professional.api.responses.professionalregistration.ProfessionalRegistrationResponse;
import com.janocare.professional.application.commands.professionalregistration.CreateProfessionalRegistrationCommand;
import com.janocare.professional.application.commands.professionalregistration.DeleteProfessionalRegistrationCommand;
import com.janocare.professional.application.commands.professionalregistration.UpdateProfessionalRegistrationCommand;
import com.janocare.professional.application.ports.ProfessionalRegistrationRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.queries.professionalregistration.FindAllProfessionalRegistrationsQuery;
import com.janocare.professional.application.queries.professionalregistration.FindProfessionalRegistrationByIdQuery;
import com.janocare.professional.application.queries.professionalregistration.FindProfessionalRegistrationsByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalRegistration;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalRegistrationHandler {

    @Inject
    ProfessionalRegistrationRepositoryPort registrationRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Transactional
    public ProfessionalRegistrationResponse create(CreateProfessionalRegistrationCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        ProfessionalRegistration registration = ProfessionalRegistration.create(
                command.professionalId,
                command.registrationName,
                command.registrationDate,
                command.certificatePhoto
        );

        ProfessionalRegistration saved = registrationRepository.save(registration);

        return ProfessionalRegistrationApiMapper.toResponse(saved);
    }

    @Transactional
    public ProfessionalRegistrationResponse update(UpdateProfessionalRegistrationCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.registrationId == null) {
            throw new ValidationException("Registration ID is required");
        }

        ProfessionalRegistration registration = registrationRepository.findDomainById(command.registrationId)
                .orElseThrow(() -> new ValidationException("Registration not found"));

        registration.update(
                command.registrationName,
                command.registrationDate,
                command.certificatePhoto
        );

        ProfessionalRegistration saved = registrationRepository.save(registration);

        return ProfessionalRegistrationApiMapper.toResponse(saved);
    }

    @Transactional
    public void delete(DeleteProfessionalRegistrationCommand command) {
        if (command == null || command.registrationId == null) {
            throw new ValidationException("Registration ID is required");
        }

        registrationRepository.findDomainById(command.registrationId)
                .orElseThrow(() -> new ValidationException("Registration not found"));

        registrationRepository.deleteRegistrationById(command.registrationId);
    }

    public ProfessionalRegistrationResponse findById(FindProfessionalRegistrationByIdQuery query) {
        if (query == null || query.registrationId == null) {
            throw new ValidationException("Registration ID is required");
        }

        ProfessionalRegistration registration = registrationRepository.findDomainById(query.registrationId)
                .orElseThrow(() -> new ValidationException("Registration not found"));

        return ProfessionalRegistrationApiMapper.toResponse(registration);
    }

    public List<ProfessionalRegistrationResponse> findByProfessionalId(
            FindProfessionalRegistrationsByProfessionalIdQuery query
    ) {
        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        return registrationRepository.findByProfessionalId(query.professionalId)
                .stream()
                .map(ProfessionalRegistrationApiMapper::toResponse)
                .toList();
    }

    public List<ProfessionalRegistrationResponse> findAll(FindAllProfessionalRegistrationsQuery query) {
        return registrationRepository.findAllProfessionalRegistrations()
                .stream()
                .map(ProfessionalRegistrationApiMapper::toResponse)
                .toList();
    }
}