package com.janocare.professional.api.mappers;

import com.janocare.professional.api.responses.professionalservice.ProfessionalServiceResponse;
import com.janocare.professional.domain.entities.ProfessionalService;

public class ProfessionalServiceApiMapper {

    private ProfessionalServiceApiMapper() {}

    public static ProfessionalServiceResponse toResponse(ProfessionalService service) {
        if (service == null) return null;

        ProfessionalServiceResponse r = new ProfessionalServiceResponse();

        r.id = service.getId();
        r.professionalId = service.getProfessionalId();
        r.serviceName = service.getServiceName();

        return r;
    }
}