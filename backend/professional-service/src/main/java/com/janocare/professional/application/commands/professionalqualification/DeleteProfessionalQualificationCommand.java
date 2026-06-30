package com.janocare.professional.application.commands.professionalqualification;

import java.util.UUID;

public class DeleteProfessionalQualificationCommand {
    public UUID qualificationId;

    public DeleteProfessionalQualificationCommand() {}

    public DeleteProfessionalQualificationCommand(UUID qualificationId) {
        this.qualificationId = qualificationId;
    }
}