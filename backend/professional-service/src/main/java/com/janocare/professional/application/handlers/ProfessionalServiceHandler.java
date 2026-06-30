package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalServiceApiMapper;
import com.janocare.professional.api.responses.professionalservice.ProfessionalServiceResponse;
import com.janocare.professional.application.commands.professionalservice.CreateProfessionalServiceCommand;
import com.janocare.professional.application.commands.professionalservice.DeleteProfessionalServiceCommand;
import com.janocare.professional.application.commands.professionalservice.UpdateProfessionalServiceCommand;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalServiceRepositoryPort;
import com.janocare.professional.application.queries.professionalservice.FindAllProfessionalServicesQuery;
import com.janocare.professional.application.queries.professionalservice.FindProfessionalServiceByIdQuery;
import com.janocare.professional.application.queries.professionalservice.FindProfessionalServicesByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalService;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalServiceHandler {

    @Inject
    ProfessionalServiceRepositoryPort serviceRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Transactional
    public ProfessionalServiceResponse create(CreateProfessionalServiceCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        ProfessionalService service = ProfessionalService.create(
                command.professionalId,
                command.serviceName
        );

        ProfessionalService saved = serviceRepository.save(service);

        return ProfessionalServiceApiMapper.toResponse(saved);
    }

    @Transactional
    public ProfessionalServiceResponse update(UpdateProfessionalServiceCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.serviceId == null) {
            throw new ValidationException("Service ID is required");
        }

        ProfessionalService service = serviceRepository.findDomainById(command.serviceId)
                .orElseThrow(() -> new ValidationException("Professional service not found"));

        service.update(command.serviceName);

        ProfessionalService saved = serviceRepository.save(service);

        return ProfessionalServiceApiMapper.toResponse(saved);
    }

    @Transactional
    public void delete(DeleteProfessionalServiceCommand command) {
        if (command == null || command.serviceId == null) {
            throw new ValidationException("Service ID is required");
        }

        serviceRepository.findDomainById(command.serviceId)
                .orElseThrow(() -> new ValidationException("Professional service not found"));

        serviceRepository.deleteServiceById(command.serviceId);
    }

    public ProfessionalServiceResponse findById(FindProfessionalServiceByIdQuery query) {
        if (query == null || query.serviceId == null) {
            throw new ValidationException("Service ID is required");
        }

        ProfessionalService service = serviceRepository.findDomainById(query.serviceId)
                .orElseThrow(() -> new ValidationException("Professional service not found"));

        return ProfessionalServiceApiMapper.toResponse(service);
    }

    public List<ProfessionalServiceResponse> findByProfessionalId(
            FindProfessionalServicesByProfessionalIdQuery query
    ) {
        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        return serviceRepository.findByProfessionalId(query.professionalId)
                .stream()
                .map(ProfessionalServiceApiMapper::toResponse)
                .toList();
    }

    public List<ProfessionalServiceResponse> findAll(FindAllProfessionalServicesQuery query) {
        return serviceRepository.findAllProfessionalServices()
                .stream()
                .map(ProfessionalServiceApiMapper::toResponse)
                .toList();
    }
}