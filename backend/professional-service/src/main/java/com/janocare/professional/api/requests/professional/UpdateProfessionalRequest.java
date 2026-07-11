package com.janocare.professional.api.requests.professional;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateProfessionalRequest {

    @Size(max = 1000, message = "Bio must not exceed 1000 characters")
    public String bio;

    public LocalDate practicingFrom;

    @DecimalMin(value = "0.00",
                message = "Consultation fee cannot be negative")
    public BigDecimal consultationFee;

    @DecimalMin(value = "0.00",
                message = "Booking fee cannot be negative")
    public BigDecimal bookingFee;

    @DecimalMin(value = "0.00",
                message = "Instant consultation fee cannot be negative")
    public BigDecimal instantConsultationFee;

    public Boolean inpersonEnabled;
    public Boolean onlineConsultationEnabled;
    public Boolean instantCallEnabled;
}