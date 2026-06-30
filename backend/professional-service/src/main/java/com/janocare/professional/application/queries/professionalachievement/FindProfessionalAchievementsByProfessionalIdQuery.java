package com.janocare.professional.application.queries.professionalachievement;

import java.util.UUID;

public class FindProfessionalAchievementsByProfessionalIdQuery {
    public UUID professionalId;

    public FindProfessionalAchievementsByProfessionalIdQuery() {}

    public FindProfessionalAchievementsByProfessionalIdQuery(UUID professionalId) {
        this.professionalId = professionalId;
    }
}