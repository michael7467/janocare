package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalexperience.ProfessionalExperienceResponse;
import com.janocare.professional.domain.entities.ProfessionalExperience;

public class ProfessionalExperienceApiMapper {

    private ProfessionalExperienceApiMapper() {}

    public static ProfessionalExperienceResponse toResponse(
            ProfessionalExperience experience
    ) {
        if (experience == null) {
            return null;
        }

        ProfessionalExperienceResponse r =
                new ProfessionalExperienceResponse();

        r.id = experience.getId();
        r.professionalId = experience.getProfessionalId();
        r.experience = experience.getExperience();
        r.specialization = experience.getSpecialization();
        r.place = experience.getPlace();
        r.startYear = experience.getStartYear();
        r.endYear = experience.getEndYear();

        return r;
    }
}