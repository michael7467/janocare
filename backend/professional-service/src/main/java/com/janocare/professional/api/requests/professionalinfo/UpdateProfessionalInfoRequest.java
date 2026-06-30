package com.janocare.professional.api.requests.professionalinfo;

import com.janocare.professional.domain.enums.DaysOfWeek;

public class UpdateProfessionalInfoRequest {
    public String institutionName;
    public String officeNumber;
    public DaysOfWeek daysOfWeek;
    public String startTime;
    public String endTime;
    public boolean available;
}