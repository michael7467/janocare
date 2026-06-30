package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalsubspecialization.ProfessionalSubSpecializationResponse;
import com.janocare.professional.domain.entities.ProfessionalSubSpecialization;

public class ProfessionalSubSpecializationApiMapper {

    private ProfessionalSubSpecializationApiMapper() {}

    public static ProfessionalSubSpecializationResponse toResponse(
            ProfessionalSubSpecialization subSpecialization
    ) {

        if (subSpecialization == null) {
            return null;
        }

        ProfessionalSubSpecializationResponse r =
                new ProfessionalSubSpecializationResponse();

        r.id = subSpecialization.getId();

        r.professionalId =
                subSpecialization.getProfessionalId();

        r.subSpecializationId =
                subSpecialization.getSubSpecializationId();

        return r;
    }
}