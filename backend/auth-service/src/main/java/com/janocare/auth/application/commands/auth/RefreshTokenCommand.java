package com.janocare.auth.application.commands.auth;

public class RefreshTokenCommand {

    public String refreshToken;

    public RefreshTokenCommand() {}

    public RefreshTokenCommand(
            String refreshToken
    ) {
        this.refreshToken = refreshToken;
    }
}