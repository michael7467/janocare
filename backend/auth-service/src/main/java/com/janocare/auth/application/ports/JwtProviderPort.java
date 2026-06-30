package com.janocare.auth.application.ports;

import com.janocare.auth.domain.entities.User;

import java.util.UUID;

public interface JwtProviderPort {

    String generateAccessToken(User user);
    String generateRefreshToken(User user);

    boolean validateAccessToken(String token);
    boolean validateRefreshToken(String token);

    UUID extractUserId(String token);
    String[] extractRoles(String token);
}
