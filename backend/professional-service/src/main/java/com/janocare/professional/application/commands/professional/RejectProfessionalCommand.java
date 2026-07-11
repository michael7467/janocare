package com.janocare.professional.application.commands.professional;

import java.util.UUID;

public class RejectProfessionalCommand {

    public UUID professionalId;

    public RejectProfessionalCommand() {}

    public RejectProfessionalCommand(UUID professionalId) {
        this.professionalId = professionalId;
    }
}