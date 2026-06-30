package com.janocare.professional.api.responses.professionalexperience;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalExperienceResponse {

    public UUID id;

    public UUID professionalId;

    public String experience;

    public String specialization;

    public String place;

    public LocalDate startYear;

    public LocalDate endYear;
}