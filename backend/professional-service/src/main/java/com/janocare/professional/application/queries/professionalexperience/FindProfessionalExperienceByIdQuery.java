package com.janocare.professional.application.queries.professionalexperience;

import java.util.UUID;

public class FindProfessionalExperienceByIdQuery {

    public UUID experienceId;

    public FindProfessionalExperienceByIdQuery() {}

    public FindProfessionalExperienceByIdQuery(UUID experienceId) {
        this.experienceId = experienceId;
    }
}