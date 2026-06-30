package com.janocare.auth.api.responses.auth;

import com.janocare.auth.api.responses.user.UserResponse;

public class LoginResponse {

    public boolean success;

    public String message;

    public String access_token;

    public String refresh_token;

    public UserResponse user;
}