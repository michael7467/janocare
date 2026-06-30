package com.janocare.professional.api.responses.professionalregistration;

import java.time.LocalDate;
import java.util.UUID;

public class ProfessionalRegistrationResponse {
    public UUID id;
    public UUID professionalId;
    public String registrationName;
    public LocalDate registrationDate;
    public String certificatePhoto;
}