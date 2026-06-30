package com.janocare.professional.application.commands.professiontype;

import java.util.UUID;

public class DeleteProfessionalTypeCommand {

    public UUID professionTypeId;

    public DeleteProfessionalTypeCommand() {}

    public DeleteProfessionalTypeCommand(
            UUID professionTypeId
    ) {
        this.professionTypeId = professionTypeId;
    }
}