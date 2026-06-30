package com.janocare.professional.application.queries.professional;

import java.util.UUID;

public class FindProfessionalsByProfessionTypeQuery {

    public UUID professionTypeId;

    public FindProfessionalsByProfessionTypeQuery() {}

    public FindProfessionalsByProfessionTypeQuery(
            UUID professionTypeId
    ) {
        this.professionTypeId = professionTypeId;
    }
}