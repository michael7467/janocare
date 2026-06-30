package com.janocare.professional.application.commands.professional;

import java.util.UUID;

public class DeleteProfessionalCommand {

    public UUID professionalId;

    public DeleteProfessionalCommand() {}

    public DeleteProfessionalCommand(UUID professionalId) {
        this.professionalId = professionalId;
    }
}