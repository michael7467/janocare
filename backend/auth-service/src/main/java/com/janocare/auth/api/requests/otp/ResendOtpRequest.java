package com.janocare.auth.api.requests.otp;

import jakarta.validation.constraints.NotBlank;

public class ResendOtpRequest {

    @NotBlank(message = "Email is required")
    public String identifier;
}