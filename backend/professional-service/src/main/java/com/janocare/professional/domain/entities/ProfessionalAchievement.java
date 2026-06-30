package com.janocare.professional.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalAchievement {

    private UUID id;
    private UUID professionalId;
    private String awardOrRecognitionName;
    private String awardDescription;
    private LocalDate awardYear;

    protected ProfessionalAchievement() {}

    public static ProfessionalAchievement create(
            UUID professionalId,
            String awardOrRecognitionName,
            String awardDescription,
            LocalDate awardYear
    ) {
        ProfessionalAchievement achievement = new ProfessionalAchievement();
        achievement.id = UUID.randomUUID();
        achievement.professionalId = professionalId;
        achievement.awardOrRecognitionName = awardOrRecognitionName;
        achievement.awardDescription = awardDescription;
        achievement.awardYear = awardYear;
        return achievement;
    }

    public static ProfessionalAchievement restore(
            UUID id,
            UUID professionalId,
            String awardOrRecognitionName,
            String awardDescription,
            LocalDate awardYear
    ) {
        ProfessionalAchievement achievement = new ProfessionalAchievement();
        achievement.id = id;
        achievement.professionalId = professionalId;
        achievement.awardOrRecognitionName = awardOrRecognitionName;
        achievement.awardDescription = awardDescription;
        achievement.awardYear = awardYear;
        return achievement;
    }

    public void update(
            String awardOrRecognitionName,
            String awardDescription,
            LocalDate awardYear
    ) {
        this.awardOrRecognitionName = awardOrRecognitionName;
        this.awardDescription = awardDescription;
        this.awardYear = awardYear;
    }

    public UUID getId() { return id; }
    public UUID getProfessionalId() { return professionalId; }
    public String getAwardOrRecognitionName() { return awardOrRecognitionName; }
    public String getAwardDescription() { return awardDescription; }
    public LocalDate getAwardYear() { return awardYear; }
}