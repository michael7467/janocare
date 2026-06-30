package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalachievement.ProfessionalAchievementResponse;
import com.janocare.professional.domain.entities.ProfessionalAchievement;

public class ProfessionalAchievementApiMapper {

    private ProfessionalAchievementApiMapper() {}

    public static ProfessionalAchievementResponse toResponse(
            ProfessionalAchievement achievement
    ) {
        if (achievement == null) {
            return null;
        }

        ProfessionalAchievementResponse r = new ProfessionalAchievementResponse();
        r.id = achievement.getId();
        r.professionalId = achievement.getProfessionalId();
        r.awardOrRecognitionName = achievement.getAwardOrRecognitionName();
        r.awardDescription = achievement.getAwardDescription();
        r.awardYear = achievement.getAwardYear();

        return r;
    }
}