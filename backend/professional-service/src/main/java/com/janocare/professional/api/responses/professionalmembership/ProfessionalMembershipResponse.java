package com.janocare.professional.api.responses.professionalmembership;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalMembershipResponse {
    public UUID id;
    public UUID professionalId;
    public String membershipName;
    public String membershipDescription;
    public LocalDate membershipYear;
}