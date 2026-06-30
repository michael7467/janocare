package com.janocare.professional.domain.services;

import com.janocare.professional.domain.entities.Professional;
import com.janocare.professional.domain.enums.ProfessionalStatus;

public class ProfessionalEligibilityService {

    public boolean canAcceptOnlineConsultation(
            Professional professional
    ) {

        return professional.isVerified()
                && professional.isOnlineConsultationEnabled()
                && professional.getStatus() == ProfessionalStatus.APPROVED;
    }
}