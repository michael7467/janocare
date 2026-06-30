package com.janocare.professional.application.commands.professionalinfo;

import java.util.UUID;

public class DeleteProfessionalInfoCommand {
    public UUID infoId;

    public DeleteProfessionalInfoCommand() {}

    public DeleteProfessionalInfoCommand(UUID infoId) {
        this.infoId = infoId;
    }
}