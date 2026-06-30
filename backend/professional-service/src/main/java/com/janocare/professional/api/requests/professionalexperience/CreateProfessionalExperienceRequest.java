package com.janocare.professional.api.requests.professionalexperience;

import java.time.LocalDate;
import java.util.UUID;

public class CreateProfessionalExperienceRequest {

    public UUID professionalId;

    public String experience;

    public String specialization;

    public String place;

    public LocalDate startYear;

    public LocalDate endYear;
}