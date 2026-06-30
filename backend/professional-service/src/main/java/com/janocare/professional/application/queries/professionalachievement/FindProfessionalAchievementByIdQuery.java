package com.janocare.professional.application.queries.professionalachievement;

import java.util.UUID;

public class FindProfessionalAchievementByIdQuery {
    public UUID achievementId;

    public FindProfessionalAchievementByIdQuery() {}

    public FindProfessionalAchievementByIdQuery(UUID achievementId) {
        this.achievementId = achievementId;
    }
}