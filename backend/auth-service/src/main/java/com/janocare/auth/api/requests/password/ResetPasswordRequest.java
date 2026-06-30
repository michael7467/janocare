package com.janocare.auth.api.requests.password;

public class ResetPasswordRequest {

    public String identifier;

    public String otp;

    public String newPassword;

    public String confirmPassword;
}