package com.janocare.professional.api.requests.professionalmembership;

import java.time.LocalDate;
import java.util.UUID;

public class CreateProfessionalMembershipRequest {
    public UUID professionalId;
    public String membershipName;
    public String membershipDescription;
    public LocalDate membershipYear;
}