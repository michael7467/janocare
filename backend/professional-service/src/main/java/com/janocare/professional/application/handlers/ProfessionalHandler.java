package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalApiMapper;
import com.janocare.professional.api.responses.professional.ProfessionalResponse;
import com.janocare.professional.api.responses.professional.UserResponse;
import com.janocare.professional.application.commands.professional.*;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.ports.ProfessionTypeRepositoryPort;
import com.janocare.professional.application.queries.professional.*;
import com.janocare.professional.domain.entities.Professional;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ProfessionTypeNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;
import com.janocare.professional.infrastructure.clients.AuthServiceClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class ProfessionalHandler {

    private static final Logger LOG =
            Logger.getLogger(ProfessionalHandler.class);

    @Inject
    ProfessionalRepositoryPort professionalRepository;

    @Inject
    ProfessionTypeRepositoryPort professionTypeRepository;

    @Inject
    @RestClient
    AuthServiceClient authServiceClient;

    // =====================================================
    // CREATE — called internally by auth service
    // =====================================================

    @Transactional
    public ProfessionalResponse createFromAuthService(
            CreateProfessionalCommand command) {

        if (command == null)
            throw new ValidationException("Command body is required");
        if (command.userId == null)
            throw new ValidationException("User ID is required");
        if (command.professionTypeId == null)
            throw new ValidationException("Profession type ID is required");

        // guard — prevent duplicate professional profile
        professionalRepository.findByUserId(command.userId)
                .ifPresent(existing -> {
                    throw new ValidationException(
                            "Professional already exists for user: "
                                    + command.userId);
                });

        // validate profession type exists — knowledge level check
        professionTypeRepository
                .findDomainById(command.professionTypeId)
                .orElseThrow(() -> new ProfessionTypeNotFoundException(
                        "Profession type not found: "
                                + command.professionTypeId));

        Professional professional = Professional.create(
                command.userId,
                command.professionTypeId
        );

        Professional saved = professionalRepository.save(professional);

        LOG.infof("Professional profile created for user %s " +
                  "with profession type %s — status PENDING",
                command.userId, command.professionTypeId);

        return ProfessionalApiMapper.toResponse(saved);
    }

    // =====================================================
    // UPDATE PROFILE
    // =====================================================

        @Transactional
        public ProfessionalResponse update(
                UpdateProfessionalCommand command) {

        if (command == null || command.professionalId == null)
                throw new ValidationException("Professional ID is required");

        Professional professional = professionalRepository
                .findDomainById(command.professionalId)
                .orElseThrow(() -> new ProfessionalNotFoundException(
                        "Professional not found: "
                                + command.professionalId));

        // ── ownership check ───────────────────────────────────────
        // A professional can only update their own profile.
        // requestingUserId comes from JWT via SecurityContext —
        // never trusted from the request body.
        if (command.requestingUserId != null &&
                !professional.getUserId()
                        .equals(command.requestingUserId)) {
                throw new ValidationException(
                        "You can only update your own profile");
        }

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

        Professional saved = professionalRepository.save(professional);
        LOG.infof("Professional profile updated: %s by user %s",
                command.professionalId, command.requestingUserId);
        return ProfessionalApiMapper.toResponse(saved);
        }
    // =====================================================
    // APPROVE — admin only
    // Accountability pattern: admin activates operational level
    // =====================================================

    @Transactional
    public ProfessionalResponse approve(
            ApproveProfessionalCommand command) {

        if (command == null || command.professionalId == null)
            throw new ValidationException("Professional ID is required");

        Professional professional = professionalRepository
                .findDomainById(command.professionalId)
                .orElseThrow(() -> new ProfessionalNotFoundException(
                        "Professional not found: "
                                + command.professionalId));

        // domain method enforces PENDING → APPROVED guard
        professional.approve();

        Professional saved = professionalRepository.save(professional);

        LOG.infof("Professional %s approved by admin %s",
                command.professionalId, command.approvedByUserId);

        return ProfessionalApiMapper.toResponse(saved);
    }

    // =====================================================
    // REJECT — admin only
    // =====================================================

    @Transactional
    public ProfessionalResponse reject(
            RejectProfessionalCommand command) {

        if (command == null || command.professionalId == null)
            throw new ValidationException("Professional ID is required");

        Professional professional = professionalRepository
                .findDomainById(command.professionalId)
                .orElseThrow(() -> new ProfessionalNotFoundException(
                        "Professional not found: "
                                + command.professionalId));

        // domain method enforces PENDING → REJECTED guard
        professional.reject();

        Professional saved = professionalRepository.save(professional);

        LOG.infof("Professional %s rejected", command.professionalId);

        return ProfessionalApiMapper.toResponse(saved);
    }

    // =====================================================
    // VERIFY
    // =====================================================

    @Transactional
    public ProfessionalResponse verify(
            VerifyProfessionalCommand command) {

        if (command == null || command.professionalId == null)
            throw new ValidationException("Professional ID is required");

        Professional professional = professionalRepository
                .findDomainById(command.professionalId)
                .orElseThrow(() -> new ProfessionalNotFoundException(
                        "Professional not found: "
                                + command.professionalId));

        boolean verified = command.verified != null
                ? command.verified
                : true;

        professional.verify(verified);

        Professional saved = professionalRepository.save(professional);

        LOG.infof("Professional %s verification set to %s",
                command.professionalId, verified);

        return ProfessionalApiMapper.toResponse(saved);
    }

    // =====================================================
    // ENABLE ONLINE CONSULTATION
    // =====================================================

    @Transactional
    public ProfessionalResponse enableOnlineConsultation(
            EnableOnlineConsultationCommand command) {

        if (command == null || command.professionalId == null)
            throw new ValidationException("Professional ID is required");

        Professional professional = professionalRepository
                .findDomainById(command.professionalId)
                .orElseThrow(() -> new ProfessionalNotFoundException(
                        "Professional not found: "
                                + command.professionalId));

        boolean enabled = command.enabled != null
                ? command.enabled
                : true;

        professional.enableOnlineConsultation(enabled);

        Professional saved = professionalRepository.save(professional);

        LOG.infof("Professional %s online consultation set to %s",
                command.professionalId, enabled);

        return ProfessionalApiMapper.toResponse(saved);
    }

    // =====================================================
    // DELETE
    // =====================================================

    @Transactional
    public void delete(DeleteProfessionalCommand command) {

        if (command == null || command.professionalId == null)
            throw new ValidationException("Professional ID is required");

        professionalRepository
                .findDomainById(command.professionalId)
                .orElseThrow(() -> new ProfessionalNotFoundException(
                        "Professional not found: "
                                + command.professionalId));

        professionalRepository.deleteProfessionalById(
                command.professionalId);

        LOG.infof("Professional deleted: %s", command.professionalId);
    }

    // =====================================================
    // FIND BY ID — enriched with user data
    // =====================================================

    public ProfessionalResponse findById(
            FindProfessionalByIdQuery query) {

        if (query == null || query.professionalId == null)
            throw new ValidationException("Professional ID is required");

        Professional professional = professionalRepository
                .findDomainById(query.professionalId)
                .orElseThrow(() -> new ProfessionalNotFoundException(
                        "Professional not found: "
                                + query.professionalId));

        ProfessionalResponse response =
                ProfessionalApiMapper.toResponse(professional);

        try {
            response.user = authServiceClient
                    .getUserById(professional.getUserId());
        } catch (Exception e) {
            LOG.warnf("Could not fetch user %s from auth service: %s",
                    professional.getUserId(), e.getMessage());
        }

        return response;
    }

    // =====================================================
    // FIND BY USER ID
    // =====================================================

    public ProfessionalResponse findByUserId(
            FindProfessionalByUserIdQuery query) {

        if (query == null || query.userId == null)
            throw new ValidationException("User ID is required");

        Professional professional = professionalRepository
                .findByUserId(query.userId)
                .orElseThrow(() -> new ProfessionalNotFoundException(
                        "Professional not found for user: "
                                + query.userId));

        ProfessionalResponse response =
                ProfessionalApiMapper.toResponse(professional);

        try {
            response.user = authServiceClient
                    .getUserById(professional.getUserId());
        } catch (Exception e) {
            LOG.warnf("Could not fetch user %s from auth service: %s",
                    query.userId, e.getMessage());
        }

        return response;
    }

    // =====================================================
    // FIND ALL — batch user enrichment (N+1 fix)
    // =====================================================

    public List<ProfessionalResponse> findAll(
            FindAllProfessionalsQuery query) {

        List<Professional> professionals =
                professionalRepository.findAllProfessionals();

        if (professionals.isEmpty()) {
            return List.of();
        }

        // collect all user IDs — single batch call instead of N
        List<UUID> userIds = professionals.stream()
                .map(Professional::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        // one HTTP call to auth service
        Map<String, UserResponse> userMap = new HashMap<>();
        try {
            List<UserResponse> users =
                    authServiceClient.getUsersByIds(userIds);
            if (users != null) {
                users.forEach(u -> {
                    if (u != null && u.id != null) {
                        userMap.put(u.id, u);
                    }
                });
            }
            LOG.debugf("Fetched %d users from auth service " +
                       "for %d professionals",
                    userMap.size(), professionals.size());
        } catch (Exception e) {
            LOG.warnf("Could not fetch users from auth service: %s",
                    e.getMessage());
        }

        // filter by status if provided
        return professionals.stream()
                .filter(p -> query.status == null
                        || p.getStatus().name()
                                .equals(query.status))
                .map(professional -> {
                    ProfessionalResponse response =
                            ProfessionalApiMapper.toResponse(professional);
                    response.user = userMap.get(
                            professional.getUserId().toString());
                    return response;
                })
                .toList();
    }
}