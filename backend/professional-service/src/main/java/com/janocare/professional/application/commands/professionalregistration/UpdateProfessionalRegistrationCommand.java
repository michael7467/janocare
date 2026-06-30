package com.janocare.professional.application.commands.professionalregistration;

import java.time.LocalDate;
import java.util.UUID;

public class UpdateProfessionalRegistrationCommand {
    public UUID registrationId;
    public String registrationName;
    public LocalDate registrationDate;
    public String certificatePhoto;
}