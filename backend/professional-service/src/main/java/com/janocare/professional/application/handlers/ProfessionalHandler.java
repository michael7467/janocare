package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalApiMapper;
import com.janocare.professional.api.responses.professional.ProfessionalResponse;
import com.janocare.professional.application.commands.professional.*;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.ports.ProfessionTypeRepositoryPort;
import com.janocare.professional.application.queries.professional.*;
import com.janocare.professional.domain.entities.Professional;
import com.janocare.professional.domain.exceptions.*;
import com.janocare.professional.infrastructure.clients.AuthServiceClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;

@ApplicationScoped
public class ProfessionalHandler {

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Inject
    ProfessionTypeRepositoryPort professionTypeRepository;
    @Inject
    @RestClient
    AuthServiceClient authServiceClient;
    @Transactional
    public ProfessionalResponse createFromAuthService(CreateProfessionalCommand command) {
        if (command == null) throw new ValidationException("Command body is required");
        if (command.userId == null) throw new ValidationException("User ID is required");
        if (command.professionTypeId == null) throw new ValidationException("Profession type ID is required");

        professionalRepository.findByUserId(command.userId).ifPresent(existing -> {
            throw new ValidationException("Professional already exists for this user");
        });

        professionTypeRepository.findDomainById(command.professionTypeId)
                .orElseThrow(ProfessionTypeNotFoundException::new);

        Professional saved = professionalRepository.save(
                Professional.create(command.userId, command.professionTypeId)
        );

        return ProfessionalApiMapper.toResponse(saved);
    }

    @Transactional
    public ProfessionalResponse update(UpdateProfessionalCommand command) {
        if (command == null || command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        Professional professional = professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        professional.updateProfile(
                command.bio,
                command.practicingFrom,
                command.consultationFee,
                command.bookingFee,
                command.instantConsultationFee,
                command.inpersonEnabled,
                command.onlineConsultationEnabled,
                command.instantCallEnabled
        );

        return ProfessionalApiMapper.toResponse(professionalRepository.save(professional));
    }

    @Transactional
    public void delete(DeleteProfessionalCommand command) {
        if (command == null || command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        professionalRepository.deleteProfessionalById(command.professionalId);
    }

    @Transactional
    public ProfessionalResponse approve(ApproveProfessionalCommand command) {
        if (command == null || command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        Professional professional = professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        professional.approve();

        return ProfessionalApiMapper.toResponse(professionalRepository.save(professional));
    }

    @Transactional
    public ProfessionalResponse verify(VerifyProfessionalCommand command) {
        if (command == null || command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        Professional professional = professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        professional.verify(command.verified != null ? command.verified : true);

        return ProfessionalApiMapper.toResponse(professionalRepository.save(professional));
    }

    @Transactional
    public ProfessionalResponse enableOnlineConsultation(EnableOnlineConsultationCommand command) {
        if (command == null || command.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        Professional professional = professionalRepository.findDomainById(command.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        professional.enableOnlineConsultation(command.enabled != null ? command.enabled : true);

        return ProfessionalApiMapper.toResponse(professionalRepository.save(professional));
    }

    public ProfessionalResponse findById(FindProfessionalByIdQuery query) {
        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        return ProfessionalApiMapper.toResponse(
                professionalRepository.findDomainById(query.professionalId)
                        .orElseThrow(ProfessionalNotFoundException::new)
        );
    }

    public ProfessionalResponse findByUserId(FindProfessionalByUserIdQuery query) {
        if (query == null || query.userId == null) {
            throw new ValidationException("User ID is required");
        }

        return ProfessionalApiMapper.toResponse(
                professionalRepository.findByUserId(query.userId)
                        .orElseThrow(ProfessionalNotFoundException::new)
        );
    }

   public List<ProfessionalResponse> findAll(FindAllProfessionalsQuery query) {

    return professionalRepository.findAllProfessionals()
            .stream()
            .map(professional -> {

                ProfessionalResponse response =
                        ProfessionalApiMapper.toResponse(professional);

                response.user =
                        authServiceClient.getUserById(professional.getUserId());

                return response;
            })
            .toList();
    }
}