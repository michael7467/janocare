package com.janocare.auth.infrastructure.security;

import com.janocare.auth.application.ports.JwtProviderPort;
import com.janocare.auth.domain.entities.User;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class JwtProviderAdapter implements JwtProviderPort {

    private static final Duration ACCESS_TOKEN_TTL = Duration.ofMinutes(15);
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    @Inject
    JWTParser jwtParser; // SmallRye JWT parser

    // -----------------------------
    // Generate Access Token (RS256)
    // -----------------------------
    @Override
    public String generateAccessToken(User user) {
        Instant now = Instant.now();

        JwtClaimsBuilder builder = Jwt.claims()
                .issuer("janocare-auth")
                .subject(user.getId().toString())          // sub = userId (for SecurityIdentity)
                .issuedAt(now)
                .expiresAt(now.plus(ACCESS_TOKEN_TTL))
                .claim("groups", Set.of(user.getRole().name())) // roles for @RolesAllowed
                .claim("email", user.getEmail().getValue());

        return builder.sign(); // uses RS256 keys configured in application.properties
    }

    // -----------------------------
    // Generate Refresh Token (RS256)
    // -----------------------------
    @Override
    public String generateRefreshToken(User user) {
        Instant now = Instant.now();

        JwtClaimsBuilder builder = Jwt.claims()
                .issuer("janocare-auth")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plus(REFRESH_TOKEN_TTL))
                .claim("type", "refresh");

        return builder.sign();
    }

    // -----------------------------
    // Validate Access Token
    // -----------------------------
    @Override
    public boolean validateAccessToken(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            return jwt.getExpirationTime() > (System.currentTimeMillis() / 1000L)
                    && !"refresh".equals(jwt.getClaim("type"));
        } catch (ParseException e) {
            return false;
        }
    }

    // -----------------------------
    // Validate Refresh Token
    // -----------------------------
    @Override
    public boolean validateRefreshToken(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            return jwt.getExpirationTime() > (System.currentTimeMillis() / 1000L)
                    && "refresh".equals(jwt.getClaim("type"));
        } catch (ParseException e) {
            return false;
        }
    }

    // -----------------------------
    // Extract User ID (from sub)
    // -----------------------------
    @Override
    public UUID extractUserId(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            String sub = jwt.getSubject();
            return UUID.fromString(sub);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token subject", e);
        }
    }

    // -----------------------------
    // Extract Roles (from groups)
    // -----------------------------
    @Override
    public String[] extractRoles(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            var groups = jwt.getGroups();
            return groups == null ? new String[0] : groups.toArray(new String[0]);
        } catch (ParseException e) {
            return new String[0];
        }
    }
}
