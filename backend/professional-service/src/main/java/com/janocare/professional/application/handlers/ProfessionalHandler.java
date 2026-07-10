package com.janocare.professional.application.handlers;
import com.janocare.professional.api.responses.professional.UserResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProfessionalHandler {
    private static final Logger LOG = Logger.getLogger(ProfessionalHandler.class);
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

    Professional professional = professionalRepository
            .findDomainById(query.professionalId)
            .orElseThrow(ProfessionalNotFoundException::new);

    ProfessionalResponse response = ProfessionalApiMapper.toResponse(professional);

    try {
        UserResponse user = authServiceClient.getUserById(professional.getUserId());
        response.user = user;
    } catch (Exception e) {
        System.out.println("Could not fetch user: " + e.getMessage());
    }

    return response;
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

    List<Professional> professionals =
            professionalRepository.findAllProfessionals();

    if (professionals.isEmpty()) {
        return List.of();
    }

    // Collect all user IDs — single batch call instead of N calls
    List<UUID> userIds = professionals.stream()
            .map(Professional::getUserId)
            .filter(Objects::nonNull)
            .distinct()
            .toList();

    // One HTTP call to auth service
    Map<String, UserResponse> userMap = new HashMap<>();
    try {
        List<UserResponse> users = authServiceClient.getUsersByIds(userIds);
        if (users != null) {
            users.forEach(u -> {
                if (u != null && u.id != null) {
                    userMap.put(u.id, u);
                }
            });
        }
    } catch (Exception e) {
        LOG.warnf("Could not fetch users from auth service: %s",
                e.getMessage());
    }

    // Enrich each professional response from the map
    return professionals.stream()
            .map(professional -> {
                ProfessionalResponse response =
                        ProfessionalApiMapper.toResponse(professional);
                response.user = userMap.get(
                        professional.getUserId().toString()
                );
                return response;
            })
            .toList();
}
}