package com.janocare.professional.application.queries.professionalsubspecialization;

import java.util.UUID;

public class FindProfessionalSubSpecializationByIdQuery {

    public UUID professionalSubSpecializationId;

    public FindProfessionalSubSpecializationByIdQuery() {}

    public FindProfessionalSubSpecializationByIdQuery(
            UUID professionalSubSpecializationId
    ) {
        this.professionalSubSpecializationId =
                professionalSubSpecializationId;
    }
}