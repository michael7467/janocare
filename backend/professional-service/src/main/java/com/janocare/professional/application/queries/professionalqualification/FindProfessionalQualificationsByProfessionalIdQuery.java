package com.janocare.professional.application.queries.professionalqualification;

import java.util.UUID;

public class FindProfessionalQualificationsByProfessionalIdQuery {
    public UUID professionalId;

    public FindProfessionalQualificationsByProfessionalIdQuery() {}

    public FindProfessionalQualificationsByProfessionalIdQuery(UUID professionalId) {
        this.professionalId = professionalId;
    }
}