package com.janocare.auth.infrastructure.security;

import io.vertx.core.http.Cookie;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CookieService {

    private static final Logger LOG =
            Logger.getLogger(CookieService.class);

    private static final long REFRESH_TOKEN_MAX_AGE =
            7 * 24 * 60 * 60; // 7 days in seconds — matches JWT TTL

    @ConfigProperty(name = "app.cookie.secure", defaultValue = "true")
    boolean secureCookie;

    public Cookie createRefreshTokenCookie(String token) {
        Cookie cookie = Cookie.cookie("refresh_token", token);
        cookie.setHttpOnly(true);          // not accessible via JavaScript
        cookie.setSecure(secureCookie);    // HTTPS only in prod, configurable for dev
        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_TOKEN_MAX_AGE);
        LOG.debug("Refresh token cookie created");
        return cookie;
    }

    public Cookie clearRefreshTokenCookie() {
        Cookie cookie = Cookie.cookie("refresh_token", "");
        cookie.setMaxAge(0);               // expires immediately
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        LOG.debug("Refresh token cookie cleared");
        return cookie;
    }
}