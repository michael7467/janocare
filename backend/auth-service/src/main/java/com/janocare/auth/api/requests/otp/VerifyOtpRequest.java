package com.janocare.auth.api.requests.otp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class VerifyOtpRequest {

    @NotBlank(message = "Email is required")
    public String identifier;

    @NotBlank(message = "OTP code is required")
    @Pattern(
        regexp = "^[0-9]{6}$",
        message = "OTP must be exactly 6 digits"
    )
    public String otp;
}