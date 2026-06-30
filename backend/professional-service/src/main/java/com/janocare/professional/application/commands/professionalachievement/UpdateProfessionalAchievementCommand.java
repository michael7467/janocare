package com.janocare.professional.application.commands.professionalachievement;

import java.time.LocalDate;
import java.util.UUID;

public class UpdateProfessionalAchievementCommand {
    public UUID achievementId;
    public String awardOrRecognitionName;
    public String awardDescription;
    public LocalDate awardYear;
}