package com.janocare.professional.application.queries.professionalmembership;

import java.util.UUID;

public class FindProfessionalMembershipByIdQuery {
    public UUID membershipId;

    public FindProfessionalMembershipByIdQuery() {}

    public FindProfessionalMembershipByIdQuery(UUID membershipId) {
        this.membershipId = membershipId;
    }
}