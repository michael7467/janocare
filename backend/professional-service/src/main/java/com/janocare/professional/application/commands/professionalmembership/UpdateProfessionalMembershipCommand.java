package com.janocare.professional.application.commands.professionalmembership;

import java.time.LocalDate;
import java.util.UUID;

public class UpdateProfessionalMembershipCommand {
    public UUID membershipId;
    public String membershipName;
    public String membershipDescription;
    public LocalDate membershipYear;
}