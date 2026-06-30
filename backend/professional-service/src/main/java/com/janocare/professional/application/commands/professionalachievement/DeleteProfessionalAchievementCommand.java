package com.janocare.professional.application.commands.professionalachievement;

import java.util.UUID;

public class DeleteProfessionalAchievementCommand {
    public UUID achievementId;

    public DeleteProfessionalAchievementCommand() {}

    public DeleteProfessionalAchievementCommand(UUID achievementId) {
        this.achievementId = achievementId;
    }
}