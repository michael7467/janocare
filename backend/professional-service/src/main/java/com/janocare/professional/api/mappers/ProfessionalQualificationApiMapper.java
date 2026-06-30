package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalqualification.ProfessionalQualificationResponse;
import com.janocare.professional.domain.entities.ProfessionalQualification;

public class ProfessionalQualificationApiMapper {

    private ProfessionalQualificationApiMapper() {}

    public static ProfessionalQualificationResponse toResponse(ProfessionalQualification q) {
        if (q == null) return null;

        ProfessionalQualificationResponse r = new ProfessionalQualificationResponse();

        r.id = q.getId();
        r.professionalId = q.getProfessionalId();
        r.qualificationName = q.getQualificationName();
        r.institutionName = q.getInstitutionName();
        r.procurementYear = q.getProcurementYear();

        return r;
    }
}