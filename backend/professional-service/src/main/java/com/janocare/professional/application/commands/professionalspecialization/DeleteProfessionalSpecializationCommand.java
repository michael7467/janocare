package com.janocare.professional.application.commands.professionalspecialization;

import java.util.UUID;

public class DeleteProfessionalSpecializationCommand {

    public UUID professionalSpecializationId;

    public DeleteProfessionalSpecializationCommand() {}

    public DeleteProfessionalSpecializationCommand(
            UUID professionalSpecializationId
    ) {
        this.professionalSpecializationId =
                professionalSpecializationId;
    }
}