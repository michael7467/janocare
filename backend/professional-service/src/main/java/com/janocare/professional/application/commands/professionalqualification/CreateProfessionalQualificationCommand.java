package com.janocare.professional.application.commands.professionalqualification;

import java.time.LocalDate;
import java.util.UUID;

public class CreateProfessionalQualificationCommand {
    public UUID professionalId;
    public String qualificationName;
    public String institutionName;
    public LocalDate procurementYear;
}