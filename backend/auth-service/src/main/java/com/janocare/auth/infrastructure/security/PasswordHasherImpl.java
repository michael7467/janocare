package com.janocare.auth.infrastructure.security;

import com.janocare.auth.application.ports.PasswordHasherPort;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class PasswordHasherImpl implements PasswordHasherPort {

    private static final Logger LOG =
            Logger.getLogger(PasswordHasherImpl.class);

    @Override
    public String hash(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    @Override
    public boolean verify(String rawPassword, String hashedPassword) {
        boolean result = BCrypt.checkpw(rawPassword, hashedPassword);
        if (!result) {
            LOG.warn("Password verification failed");
        }
        return result;
    }
}