package com.janocare.professional.application.queries.subspecialization;

import java.util.UUID;

public class FindSubSpecializationByIdQuery {

    public UUID subSpecializationId;

    public FindSubSpecializationByIdQuery() {}

    public FindSubSpecializationByIdQuery(
            UUID subSpecializationId
    ) {
        this.subSpecializationId =
                subSpecializationId;
    }
}