package com.janocare.professional.application.queries.professionalinfo;

import java.util.UUID;

public class FindProfessionalInfoByIdQuery {
    public UUID infoId;

    public FindProfessionalInfoByIdQuery() {}

    public FindProfessionalInfoByIdQuery(UUID infoId) {
        this.infoId = infoId;
    }
}