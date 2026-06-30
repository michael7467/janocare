package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalregistration.ProfessionalRegistrationResponse;
import com.janocare.professional.domain.entities.ProfessionalRegistration;

public class ProfessionalRegistrationApiMapper {

    private ProfessionalRegistrationApiMapper() {}

    public static ProfessionalRegistrationResponse toResponse(ProfessionalRegistration r) {
        if (r == null) return null;

        ProfessionalRegistrationResponse response = new ProfessionalRegistrationResponse();

        response.id = r.getId();
        response.professionalId = r.getProfessionalId();
        response.registrationName = r.getRegistrationName();
        response.registrationDate = r.getRegistrationDate();
        response.certificatePhoto = r.getCertificatePhoto();

        return response;
    }
}