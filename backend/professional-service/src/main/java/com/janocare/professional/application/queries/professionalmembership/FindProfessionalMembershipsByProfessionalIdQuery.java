package com.janocare.professional.application.queries.professionalmembership;

import java.util.UUID;

public class FindProfessionalMembershipsByProfessionalIdQuery {
    public UUID professionalId;

    public FindProfessionalMembershipsByProfessionalIdQuery() {}

    public FindProfessionalMembershipsByProfessionalIdQuery(UUID professionalId) {
        this.professionalId = professionalId;
    }
}