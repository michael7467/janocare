package com.janocare.professional.application.commands.professionalmembership;

import java.util.UUID;

public class DeleteProfessionalMembershipCommand {
    public UUID membershipId;

    public DeleteProfessionalMembershipCommand() {}

    public DeleteProfessionalMembershipCommand(UUID membershipId) {
        this.membershipId = membershipId;
    }
}