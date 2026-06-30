package com.janocare.professional.application.commands.professional;

import java.util.UUID;

public class VerifyProfessionalCommand {

    public UUID professionalId;

    public Boolean verified;

    public VerifyProfessionalCommand() {}

    public VerifyProfessionalCommand(
            UUID professionalId,
            Boolean verified
    ) {
        this.professionalId = professionalId;
        this.verified = verified;
    }
}