package com.janocare.professional.application.queries.professional;

import java.util.UUID;

public class FindProfessionalByUserIdQuery {

    public UUID userId;

    public FindProfessionalByUserIdQuery() {}

    public FindProfessionalByUserIdQuery(UUID userId) {
        this.userId = userId;
    }
}