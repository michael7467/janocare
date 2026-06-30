package com.janocare.professional.application.commands.professionalexperience;

import java.time.LocalDate;
import java.util.UUID;

public class UpdateProfessionalExperienceCommand {

    public UUID experienceId;

    public String experience;

    public String specialization;

    public String place;

    public LocalDate startYear;

    public LocalDate endYear;
}