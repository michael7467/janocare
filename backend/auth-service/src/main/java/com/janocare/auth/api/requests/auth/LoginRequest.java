package com.janocare.auth.api.requests.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Email or phone is required")
    public String identifier;

    @NotBlank(message = "Password is required")
    public String password;
}