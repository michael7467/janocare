package com.janocare.professional.application.queries.professiontype;

import java.util.UUID;

public class FindProfessionalTypeByIdQuery {

    public UUID professionTypeId;

    public FindProfessionalTypeByIdQuery() {}

    public FindProfessionalTypeByIdQuery(
            UUID professionTypeId
    ) {
        this.professionTypeId = professionTypeId;
    }
}