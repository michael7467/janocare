package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professional.ProfessionalResponse;
import com.janocare.professional.domain.entities.Professional;

public class ProfessionalApiMapper {

    private ProfessionalApiMapper() {
    }

    public static ProfessionalResponse toResponse(
            Professional professional
    ) {

        if (professional == null) {
            return null;
        }

        ProfessionalResponse response =
                new ProfessionalResponse();

        response.id =
                professional.getId();

        response.userId =
                professional.getUserId();

        response.professionTypeId =
                professional.getProfessionTypeId();

        response.bio =
                professional.getBio();

        response.consultationFee =
                professional.getConsultationFee();

        response.status =
                professional.getStatus().name();

        response.verified =
                professional.isVerified();

        return response;
    }
}