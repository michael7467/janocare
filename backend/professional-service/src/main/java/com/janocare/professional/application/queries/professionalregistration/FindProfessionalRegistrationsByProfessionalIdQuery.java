package com.janocare.professional.application.queries.professionalregistration;

import java.util.UUID;

public class FindProfessionalRegistrationsByProfessionalIdQuery {
    public UUID professionalId;

    public FindProfessionalRegistrationsByProfessionalIdQuery() {}

    public FindProfessionalRegistrationsByProfessionalIdQuery(UUID professionalId) {
        this.professionalId = professionalId;
    }
}