package com.janocare.professional.api.responses.professionalinfo;

import java.util.UUID;

public class ProfessionalInfoResponse {
    public UUID id;
    public UUID professionalId;
    public String institutionName;
    public String officeNumber;
    public String daysOfWeek;
    public String startTime;
    public String endTime;
    public boolean available;
}