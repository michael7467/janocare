package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professiontype.ProfessionalTypeResponse;
import com.janocare.professional.domain.entities.ProfessionType;

public class ProfessionalTypeApiMapper {

    private ProfessionalTypeApiMapper() {}

    public static ProfessionalTypeResponse toResponse(
            ProfessionType professionType
    ) {

        if (professionType == null) {
            return null;
        }

        ProfessionalTypeResponse response =
                new ProfessionalTypeResponse();

        response.id = professionType.getId();

        response.name = professionType.getName();

        response.description =
                professionType.getDescription();

        return response;
    }
}