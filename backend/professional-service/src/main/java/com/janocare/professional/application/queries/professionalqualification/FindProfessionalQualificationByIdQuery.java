package com.janocare.professional.application.queries.professionalqualification;

import java.util.UUID;

public class FindProfessionalQualificationByIdQuery {
    public UUID qualificationId;

    public FindProfessionalQualificationByIdQuery() {}

    public FindProfessionalQualificationByIdQuery(UUID qualificationId) {
        this.qualificationId = qualificationId;
    }
}