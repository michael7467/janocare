package com.janocare.professional.application.queries.professionalinfo;

import java.util.UUID;

public class FindProfessionalInfosByProfessionalIdQuery {
    public UUID professionalId;

    public FindProfessionalInfosByProfessionalIdQuery() {}

    public FindProfessionalInfosByProfessionalIdQuery(UUID professionalId) {
        this.professionalId = professionalId;
    }
}