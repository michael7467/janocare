package com.janocare.professional.api.requests.professionalachievement;

import java.time.LocalDate;
import java.util.UUID;

public class CreateProfessionalAchievementRequest {
    public UUID professionalId = null;
    public String awardOrRecognitionName;
    public String awardDescription;
    public LocalDate awardYear;
}