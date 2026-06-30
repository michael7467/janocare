package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalmembership.ProfessionalMembershipResponse;
import com.janocare.professional.domain.entities.ProfessionalMembership;

public class ProfessionalMembershipApiMapper {

    private ProfessionalMembershipApiMapper() {}

    public static ProfessionalMembershipResponse toResponse(ProfessionalMembership membership) {
        if (membership == null) {
            return null;
        }

        ProfessionalMembershipResponse r = new ProfessionalMembershipResponse();

        r.id = membership.getId();
        r.professionalId = membership.getProfessionalId();
        r.membershipName = membership.getMembershipName();
        r.membershipDescription = membership.getMembershipDescription();
        r.membershipYear = membership.getMembershipYear();

        return r;
    }
}