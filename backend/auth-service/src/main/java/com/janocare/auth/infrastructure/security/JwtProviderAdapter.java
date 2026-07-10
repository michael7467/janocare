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
import org.jboss.logging.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class JwtProviderAdapter implements JwtProviderPort {

    private static final Logger LOG =
            Logger.getLogger(JwtProviderAdapter.class);

    private static final Duration ACCESS_TOKEN_TTL  = Duration.ofMinutes(15);
    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(7);

    @Inject
    JWTParser jwtParser;

    @Override
    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        JwtClaimsBuilder builder = Jwt.claims()
                .issuer("janocare-auth")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plus(ACCESS_TOKEN_TTL))
                .claim("groups", Set.of(user.getRole().name()))
                .claim("email", user.getEmail().getValue());

        String token = builder.sign();
        LOG.debugf("Access token generated for user %s role %s",
                user.getId(), user.getRole());
        return token;
    }

    @Override
    public String generateRefreshToken(User user) {
        Instant now = Instant.now();
        JwtClaimsBuilder builder = Jwt.claims()
                .issuer("janocare-auth")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plus(REFRESH_TOKEN_TTL))
                .claim("type", "refresh");

        LOG.debugf("Refresh token generated for user %s", user.getId());
        return builder.sign();
    }

    @Override
    public boolean validateAccessToken(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            return jwt.getExpirationTime() > (System.currentTimeMillis() / 1000L)
                    && !"refresh".equals(jwt.getClaim("type"));
        } catch (ParseException e) {
            LOG.warnf("Access token validation failed: %s", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean validateRefreshToken(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            return jwt.getExpirationTime() > (System.currentTimeMillis() / 1000L)
                    && "refresh".equals(jwt.getClaim("type"));
        } catch (ParseException e) {
            LOG.warnf("Refresh token validation failed: %s", e.getMessage());
            return false;
        }
    }

    @Override
    public UUID extractUserId(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            return UUID.fromString(jwt.getSubject());
        } catch (Exception e) {
            LOG.errorf("Failed to extract user ID from token: %s", e.getMessage());
            throw new RuntimeException("Invalid token subject", e);
        }
    }

    @Override
    public String[] extractRoles(String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token);
            var groups = jwt.getGroups();
            return groups == null ? new String[0] : groups.toArray(new String[0]);
        } catch (ParseException e) {
            LOG.warnf("Failed to extract roles from token: %s", e.getMessage());
            return new String[0];
        }
    }
}