package com.janocare.professional.application.commands.professional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class UpdateProfessionalCommand {

    public UUID professionalId;

    public String bio;
    public UUID requestingUserId;
    public LocalDate practicingFrom;

    public BigDecimal consultationFee;

    public BigDecimal bookingFee;

    public BigDecimal instantConsultationFee;

    public Boolean inpersonEnabled;

    public Boolean onlineConsultationEnabled;

    public Boolean instantCallEnabled;

    public UpdateProfessionalCommand() {}
}