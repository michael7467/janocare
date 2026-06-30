package com.janocare.professional.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalExperience {

    private UUID id;

    private UUID professionalId;

    private String experience;

    private String specialization;

    private String place;

    private LocalDate startYear;

    private LocalDate endYear;

    protected ProfessionalExperience() {}

    public static ProfessionalExperience create(
            UUID professionalId,
            String experience,
            String specialization,
            String place,
            LocalDate startYear,
            LocalDate endYear
    ) {

        ProfessionalExperience professionalExperience =
                new ProfessionalExperience();

        professionalExperience.id = UUID.randomUUID();

        professionalExperience.professionalId = professionalId;

        professionalExperience.experience = experience;

        professionalExperience.specialization = specialization;

        professionalExperience.place = place;

        professionalExperience.startYear = startYear;

        professionalExperience.endYear = endYear;

        return professionalExperience;
    }

    public static ProfessionalExperience restore(
            UUID id,
            UUID professionalId,
            String experience,
            String specialization,
            String place,
            LocalDate startYear,
            LocalDate endYear
    ) {

        ProfessionalExperience professionalExperience =
                new ProfessionalExperience();

        professionalExperience.id = id;

        professionalExperience.professionalId = professionalId;

        professionalExperience.experience = experience;

        professionalExperience.specialization = specialization;

        professionalExperience.place = place;

        professionalExperience.startYear = startYear;

        professionalExperience.endYear = endYear;

        return professionalExperience;
    }

    // =========================================================
    // BUSINESS METHODS
    // =========================================================

    public void update(
            String experience,
            String specialization,
            String place,
            LocalDate startYear,
            LocalDate endYear
    ) {

        this.experience = experience;

        this.specialization = specialization;

        this.place = place;

        this.startYear = startYear;

        this.endYear = endYear;
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public UUID getId() {
        return id;
    }

    public UUID getProfessionalId() {
        return professionalId;
    }

    public String getExperience() {
        return experience;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPlace() {
        return place;
    }

    public LocalDate getStartYear() {
        return startYear;
    }

    public LocalDate getEndYear() {
        return endYear;
    }
}