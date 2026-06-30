package com.janocare.professional.application.commands.professionalinfo;

import com.janocare.professional.domain.enums.DaysOfWeek;

import java.util.UUID;

public class CreateProfessionalInfoCommand {
    public UUID professionalId;
    public String institutionName;
    public String officeNumber;
    public DaysOfWeek daysOfWeek;
    public String startTime;
    public String endTime;
    public boolean available;
}