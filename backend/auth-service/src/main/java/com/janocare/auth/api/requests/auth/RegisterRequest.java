package com.janocare.auth.api.requests.auth;

import com.janocare.auth.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    public String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    public String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^\\+?[0-9]{7,15}$",
        message = "Phone number must be 7-15 digits, optionally starting with +"
    )
    public String phone;

    public String deviceName;

    public String deviceType;

    public UserRole role;

    public UUID professionTypeId;
}