package com.janocare.professional.api.responses.professional;

import java.math.BigDecimal;
import java.util.UUID;

public class ProfessionalSummaryResponse {

    public UUID id;

    public UUID userId;

    public UUID professionTypeId;

    public String bio;

    public String status;

    public boolean verified;

    public BigDecimal consultationFee;
}