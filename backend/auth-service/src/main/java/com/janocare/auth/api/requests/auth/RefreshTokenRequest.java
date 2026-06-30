package com.janocare.auth.api.requests.auth;

public class RefreshTokenRequest {

    public String refreshToken;

    public RefreshTokenRequest() {}

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}