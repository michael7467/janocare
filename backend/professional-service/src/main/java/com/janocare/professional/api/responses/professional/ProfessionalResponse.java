package com.janocare.professional.api.responses.professional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProfessionalResponse {

    public UUID id;

    public UUID userId;

    public UUID professionTypeId;

    public LocalDate practicingFrom;

    public BigDecimal consultationFee;

    public BigDecimal bookingFee;

    public BigDecimal instantConsultationFee;

    public Integer upVotes;

    public Integer downVotes;

    public Integer viewCounts;

    public String bio;

    public String status;

    public boolean verified;

    public boolean inpersonEnabled;

    public boolean onlineConsultationEnabled;

    public boolean instantCallEnabled;

    public BigDecimal walletBalance;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;

    // ADD THESE
    public UserResponse user;

}