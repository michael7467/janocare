package com.janocare.professional.application.handlers;

import com.janocare.professional.api.mappers.ProfessionalAchievementApiMapper;
import com.janocare.professional.api.responses.professionalachievement.ProfessionalAchievementResponse;
import com.janocare.professional.application.commands.professionalachievement.CreateProfessionalAchievementCommand;
import com.janocare.professional.application.commands.professionalachievement.DeleteProfessionalAchievementCommand;
import com.janocare.professional.application.commands.professionalachievement.UpdateProfessionalAchievementCommand;
import com.janocare.professional.application.ports.ProfessionalAchievementRepositoryPort;
import com.janocare.professional.application.ports.ProfessionalRepositoryPort;
import com.janocare.professional.application.queries.professionalachievement.FindAllProfessionalAchievementsQuery;
import com.janocare.professional.application.queries.professionalachievement.FindProfessionalAchievementByIdQuery;
import com.janocare.professional.application.queries.professionalachievement.FindProfessionalAchievementsByProfessionalIdQuery;
import com.janocare.professional.domain.entities.ProfessionalAchievement;
import com.janocare.professional.domain.exceptions.ProfessionalNotFoundException;
import com.janocare.professional.domain.exceptions.ValidationException;
import com.janocare.professional.domain.entities.Professional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProfessionalAchievementHandler {

    @Inject
    ProfessionalAchievementRepositoryPort achievementRepository;

    @Inject
    ProfessionalRepositoryPort professionalRepository;

   @Transactional
   public ProfessionalAchievementResponse create(CreateProfessionalAchievementCommand command) {

    if (command == null) {
        throw new ValidationException("Command body is required");
    }

    if (command.userId == null) {
        throw new ValidationException("User ID is required");
    }

    Professional professional =
            professionalRepository
                    .findByUserId(command.userId)
                    .orElseThrow(ProfessionalNotFoundException::new);

        ProfessionalAchievement achievement = ProfessionalAchievement.create(
                professional.getId(),
                command.awardOrRecognitionName,
                command.awardDescription,
                command.awardYear
        );

    ProfessionalAchievement saved =
            achievementRepository.save(achievement);

    return ProfessionalAchievementApiMapper.toResponse(saved);
   }

    @Transactional
    public ProfessionalAchievementResponse update(UpdateProfessionalAchievementCommand command) {
        if (command == null) {
            throw new ValidationException("Command body is required");
        }

        if (command.achievementId == null) {
            throw new ValidationException("Achievement ID is required");
        }

        ProfessionalAchievement achievement = achievementRepository.findDomainById(command.achievementId)
                .orElseThrow(() -> new ValidationException("Achievement not found"));

        achievement.update(
                command.awardOrRecognitionName,
                command.awardDescription,
                command.awardYear
        );

        ProfessionalAchievement saved = achievementRepository.save(achievement);

        return ProfessionalAchievementApiMapper.toResponse(saved);
    }

    @Transactional
    public void delete(DeleteProfessionalAchievementCommand command) {
        if (command == null || command.achievementId == null) {
            throw new ValidationException("Achievement ID is required");
        }

        achievementRepository.findDomainById(command.achievementId)
                .orElseThrow(() -> new ValidationException("Achievement not found"));

        achievementRepository.deleteAchievementById(command.achievementId);
    }

    public ProfessionalAchievementResponse findById(
            FindProfessionalAchievementByIdQuery query
    ) {
        if (query == null || query.achievementId == null) {
            throw new ValidationException("Achievement ID is required");
        }

        ProfessionalAchievement achievement = achievementRepository.findDomainById(query.achievementId)
                .orElseThrow(() -> new ValidationException("Achievement not found"));

        return ProfessionalAchievementApiMapper.toResponse(achievement);
    }

    public List<ProfessionalAchievementResponse> findByProfessionalId(
            FindProfessionalAchievementsByProfessionalIdQuery query
    ) {
        if (query == null || query.professionalId == null) {
            throw new ValidationException("Professional ID is required");
        }

        professionalRepository.findDomainById(query.professionalId)
                .orElseThrow(ProfessionalNotFoundException::new);

        return achievementRepository.findByProfessionalId(query.professionalId)
                .stream()
                .map(ProfessionalAchievementApiMapper::toResponse)
                .toList();
    }

    public List<ProfessionalAchievementResponse> findAll(
            FindAllProfessionalAchievementsQuery query
    ) {
        return achievementRepository.findAllAchievements()
                .stream()
                .map(ProfessionalAchievementApiMapper::toResponse)
                .toList();
    }
}