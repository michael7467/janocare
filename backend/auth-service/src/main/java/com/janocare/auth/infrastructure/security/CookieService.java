package com.janocare.auth.infrastructure.security;

import io.vertx.core.http.Cookie;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CookieService {

    public Cookie createRefreshTokenCookie(String token) {
        Cookie cookie = Cookie.cookie("refresh_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        // remove SameSite usage for now
        return cookie;
    }
    public Cookie clearRefreshTokenCookie() {
        Cookie cookie = Cookie.cookie("refresh_token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}
