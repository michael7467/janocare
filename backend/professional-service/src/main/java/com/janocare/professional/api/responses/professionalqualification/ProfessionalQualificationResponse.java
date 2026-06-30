package com.janocare.professional.api.responses.professionalqualification;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalQualificationResponse {
    public UUID id;
    public UUID professionalId;
    public String qualificationName;
    public String institutionName;
    public LocalDate procurementYear;
}