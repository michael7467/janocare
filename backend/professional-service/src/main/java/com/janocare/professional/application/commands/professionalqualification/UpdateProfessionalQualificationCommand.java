package com.janocare.professional.application.commands.professionalqualification;

import java.time.LocalDate;
import java.util.UUID;

public class UpdateProfessionalQualificationCommand {
    public UUID qualificationId;
    public String qualificationName;
    public String institutionName;
    public LocalDate procurementYear;
}