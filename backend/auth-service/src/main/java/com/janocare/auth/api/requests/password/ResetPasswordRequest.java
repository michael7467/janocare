package com.janocare.auth.api.requests.password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {

    @NotBlank(message = "Email is required")
    public String identifier;

    @NotBlank(message = "OTP code is required")
    @Pattern(
        regexp = "^[0-9]{6}$",
        message = "OTP must be exactly 6 digits"
    )
    public String otp;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    public String newPassword;

    @NotBlank(message = "Confirm password is required")
    public String confirmPassword;
}