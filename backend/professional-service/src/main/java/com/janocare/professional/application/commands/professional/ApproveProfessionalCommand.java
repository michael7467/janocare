package com.janocare.professional.application.commands.professional;

import java.util.UUID;

public class ApproveProfessionalCommand {

    public UUID professionalId;

    public UUID approvedByUserId;

    public ApproveProfessionalCommand() {}

    public ApproveProfessionalCommand(
            UUID professionalId,
            UUID approvedByUserId
    ) {
        this.professionalId = professionalId;
        this.approvedByUserId = approvedByUserId;
    }
}