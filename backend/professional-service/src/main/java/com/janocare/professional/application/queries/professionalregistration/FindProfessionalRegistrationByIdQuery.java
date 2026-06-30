package com.janocare.professional.application.queries.professionalregistration;

import java.util.UUID;

public class FindProfessionalRegistrationByIdQuery {
    public UUID registrationId;

    public FindProfessionalRegistrationByIdQuery() {}

    public FindProfessionalRegistrationByIdQuery(UUID registrationId) {
        this.registrationId = registrationId;
    }
}