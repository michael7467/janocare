package com.janocare.professional.api.responses.professionalachievement;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalAchievementResponse {
    public UUID id;
    public UUID professionalId;
    public String awardOrRecognitionName;
    public String awardDescription;
    public LocalDate awardYear;
}