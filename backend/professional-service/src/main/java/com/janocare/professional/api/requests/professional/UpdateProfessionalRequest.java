package com.janocare.professional.api.requests.professional;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateProfessionalRequest {

    public String bio;

    public LocalDate practicingFrom;

    public BigDecimal consultationFee;

    public BigDecimal bookingFee;

    public BigDecimal instantConsultationFee;

    public Boolean inpersonEnabled;

    public Boolean onlineConsultationEnabled;

    public Boolean instantCallEnabled;
}