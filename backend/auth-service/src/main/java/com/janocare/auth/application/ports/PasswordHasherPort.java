package com.janocare.auth.application.ports;

public interface PasswordHasherPort {
    String hash(String rawPassword);
    boolean verify(String rawPassword, String hashedPassword);
}
