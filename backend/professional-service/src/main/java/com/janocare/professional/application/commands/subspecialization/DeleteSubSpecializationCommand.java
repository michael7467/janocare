package com.janocare.professional.application.commands.subspecialization;

import java.util.UUID;

public class DeleteSubSpecializationCommand {

    public UUID subSpecializationId;

    public DeleteSubSpecializationCommand() {}

    public DeleteSubSpecializationCommand(
            UUID subSpecializationId
    ) {
        this.subSpecializationId =
                subSpecializationId;
    }
}