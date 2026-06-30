package com.janocare.professional.application.commands.professional;

import java.util.UUID;

public class CreateProfessionalCommand {

    public UUID userId;

    public UUID professionTypeId;

    public CreateProfessionalCommand() {}

    public CreateProfessionalCommand(
            UUID userId,
            UUID professionTypeId
    ) {
        this.userId = userId;
        this.professionTypeId = professionTypeId;
    }
}