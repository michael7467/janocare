package com.janocare.auth.infrastructure.security;

import com.janocare.auth.application.ports.VerificationServicePort;

import io.quarkus.redis.client.RedisClient;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import jakarta.inject.Named;

import java.time.Duration;
import java.util.Random;

@ApplicationScoped
public class RedisVerificationServiceAdapter implements VerificationServicePort {

    private static final Logger LOG = Logger.getLogger(RedisVerificationServiceAdapter.class);
    private static final String OTP_KEY_PREFIX = "otp:";
    private static final int OTP_TTL_SECONDS = 300; // 5 minutes

    @Inject
    RedisClient redis;


    private final Random random = new Random();

    @Override
    public String generateAndSendCode(String email) {
        String code = String.format("%06d", random.nextInt(1_000_000));
        String key = OTP_KEY_PREFIX + email.toLowerCase();

        redis.setex(key, String.valueOf(OTP_TTL_SECONDS), code);

        LOG.infof("Generated OTP %s for %s", code, email);
//        emailSender.sendOtpEmail(email, code);

        return code; // <-- FIX
    }


    @Override
    public boolean verifyCode(String email, String code) {
        String key = OTP_KEY_PREFIX + email.toLowerCase();
        var res = redis.get(key);

        if (res == null || res.toString() == null) {
            return false;
        }

        String stored = res.toString();
        boolean ok = stored.equals(code);

        if (ok) {
            redis.del(java.util.List.of(key));
        }

        return ok;
    }
}
