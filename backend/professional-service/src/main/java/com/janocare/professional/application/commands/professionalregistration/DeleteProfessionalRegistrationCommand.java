package com.janocare.professional.application.commands.professionalregistration;

import java.util.UUID;

public class DeleteProfessionalRegistrationCommand {
    public UUID registrationId;

    public DeleteProfessionalRegistrationCommand() {}

    public DeleteProfessionalRegistrationCommand(UUID registrationId) {
        this.registrationId = registrationId;
    }
}