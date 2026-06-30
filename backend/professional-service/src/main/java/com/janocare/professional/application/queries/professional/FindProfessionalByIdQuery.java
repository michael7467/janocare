package com.janocare.professional.application.queries.professional;

import java.util.UUID;

public class FindProfessionalByIdQuery {

    public UUID professionalId;

    public FindProfessionalByIdQuery() {}

    public FindProfessionalByIdQuery(UUID professionalId) {
        this.professionalId = professionalId;
    }
}