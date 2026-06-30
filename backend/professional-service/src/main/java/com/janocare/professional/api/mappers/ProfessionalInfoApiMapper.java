package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalinfo.ProfessionalInfoResponse;
import com.janocare.professional.domain.entities.ProfessionalInfo;

public class ProfessionalInfoApiMapper {

    private ProfessionalInfoApiMapper() {}

    public static ProfessionalInfoResponse toResponse(ProfessionalInfo info) {
        if (info == null) {
            return null;
        }

        ProfessionalInfoResponse r = new ProfessionalInfoResponse();

        r.id = info.getId();
        r.professionalId = info.getProfessionalId();
        r.institutionName = info.getInstitutionName();
        r.officeNumber = info.getOfficeNumber();
        r.daysOfWeek = info.getDaysOfWeek().name();
        r.startTime = info.getStartTime();
        r.endTime = info.getEndTime();
        r.available = info.isAvailable();

        return r;
    }
}