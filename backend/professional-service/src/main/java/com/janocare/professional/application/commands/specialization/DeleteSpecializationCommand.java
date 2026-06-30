package com.janocare.professional.application.commands.specialization;

import java.util.UUID;

public class DeleteSpecializationCommand {

    public UUID specializationId;

    public DeleteSpecializationCommand() {}

    public DeleteSpecializationCommand(
            UUID specializationId
    ) {
        this.specializationId = specializationId;
    }
}