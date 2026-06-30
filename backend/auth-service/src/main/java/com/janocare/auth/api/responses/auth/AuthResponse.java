package com.janocare.auth.api.responses.auth;

import com.janocare.auth.api.responses.user.UserResponse;

public class AuthResponse {

    public boolean success;

    public String access_token;

    public String refresh_token;

    public boolean mustChangePassword;

    public UserResponse user;
}