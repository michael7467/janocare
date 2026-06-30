package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.specialization.SpecializationResponse;
import com.janocare.professional.domain.entities.Specialization;

public class SpecializationApiMapper {

    private SpecializationApiMapper() {}

    public static SpecializationResponse toResponse(
            Specialization specialization
    ) {

        if (specialization == null) {
            return null;
        }

        SpecializationResponse r =
                new SpecializationResponse();

        r.id = specialization.getId();

        r.name = specialization.getName();

        r.description = specialization.getDescription();

        return r;
    }
}