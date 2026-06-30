package com.janocare.professional.application.commands.professionalsubspecialization;

import java.util.UUID;

public class DeleteProfessionalSubSpecializationCommand {

    public UUID professionalSubSpecializationId;

    public DeleteProfessionalSubSpecializationCommand() {}

    public DeleteProfessionalSubSpecializationCommand(
            UUID professionalSubSpecializationId
    ) {
        this.professionalSubSpecializationId =
                professionalSubSpecializationId;
    }
}