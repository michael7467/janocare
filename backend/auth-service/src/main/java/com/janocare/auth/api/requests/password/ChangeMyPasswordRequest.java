package com.janocare.auth.api.requests.password;

public class ChangeMyPasswordRequest {

    public String identifier;

    public String previousPassword;

    public String newPassword;

    public String confirmPassword;
}