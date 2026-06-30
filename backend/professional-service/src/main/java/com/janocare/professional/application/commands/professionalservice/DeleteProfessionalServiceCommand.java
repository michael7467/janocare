package com.janocare.professional.application.commands.professionalservice;

import java.util.UUID;

public class DeleteProfessionalServiceCommand {
    public UUID serviceId;

    public DeleteProfessionalServiceCommand() {}

    public DeleteProfessionalServiceCommand(UUID serviceId) {
        this.serviceId = serviceId;
    }
}