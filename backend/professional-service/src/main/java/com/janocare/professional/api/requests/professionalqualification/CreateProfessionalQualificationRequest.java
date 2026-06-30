package com.janocare.professional.api.requests.professionalqualification;

import java.time.LocalDate;
import java.util.UUID;

public class CreateProfessionalQualificationRequest {
    public UUID professionalId;
    public String qualificationName;
    public String institutionName;
    public LocalDate procurementYear;
}