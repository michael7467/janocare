package com.janocare.professional.application.commands.professionalexperience;

import java.util.UUID;

public class DeleteProfessionalExperienceCommand {

    public UUID experienceId;

    public DeleteProfessionalExperienceCommand() {}

    public DeleteProfessionalExperienceCommand(UUID experienceId) {
        this.experienceId = experienceId;
    }
}