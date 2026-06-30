package com.janocare.professional.application.commands.professional;

import java.util.UUID;

public class EnableOnlineConsultationCommand {

    public UUID professionalId;

    public Boolean enabled;

    public EnableOnlineConsultationCommand() {}

    public EnableOnlineConsultationCommand(
            UUID professionalId,
            Boolean enabled
    ) {
        this.professionalId = professionalId;
        this.enabled = enabled;
    }
}