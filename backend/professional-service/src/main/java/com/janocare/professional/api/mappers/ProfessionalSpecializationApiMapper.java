package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalspecialization.ProfessionalSpecializationResponse;
import com.janocare.professional.domain.entities.ProfessionalSpecialization;

public class ProfessionalSpecializationApiMapper {

    private ProfessionalSpecializationApiMapper() {}

    public static ProfessionalSpecializationResponse toResponse(
            ProfessionalSpecialization specialization
    ) {

        if (specialization == null) {
            return null;
        }

        ProfessionalSpecializationResponse r =
                new ProfessionalSpecializationResponse();

        r.id = specialization.getId();

        r.professionalId =
                specialization.getProfessionalId();

        r.specializationId =
                specialization.getSpecializationId();

        return r;
    }
}