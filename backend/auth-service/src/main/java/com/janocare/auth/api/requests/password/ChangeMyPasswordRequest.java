package com.janocare.auth.api.requests.password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangeMyPasswordRequest {

    @NotBlank(message = "Email is required")
    public String identifier;

    @NotBlank(message = "Previous password is required")
    public String previousPassword;

    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    public String newPassword;

    @NotBlank(message = "Confirm password is required")
    public String confirmPassword;
}