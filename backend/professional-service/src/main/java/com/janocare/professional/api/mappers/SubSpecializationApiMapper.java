package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.subspecialization.SubSpecializationResponse;
import com.janocare.professional.domain.entities.SubSpecialization;

public class SubSpecializationApiMapper {

    private SubSpecializationApiMapper() {}

    public static SubSpecializationResponse toResponse(
            SubSpecialization subSpecialization
    ) {

        if (subSpecialization == null) {
            return null;
        }

        SubSpecializationResponse r =
                new SubSpecializationResponse();

        r.id = subSpecialization.getId();

        r.specializationId =
                subSpecialization.getSpecializationId();

        r.name = subSpecialization.getName();

        r.description =
                subSpecialization.getDescription();

        return r;
    }
}