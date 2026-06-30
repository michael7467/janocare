package com.janocare.professional.application.queries.professionalexperience;

import java.util.UUID;

public class FindProfessionalExperiencesByProfessionalIdQuery {

    public UUID professionalId;

    public FindProfessionalExperiencesByProfessionalIdQuery() {}

    public FindProfessionalExperiencesByProfessionalIdQuery(
            UUID professionalId
    ) {
        this.professionalId = professionalId;
    }
}