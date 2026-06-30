package com.janocare.auth.application.ports;

public interface VerificationServicePort {
    String generateAndSendCode(String email);  // return OTP
    boolean verifyCode(String email, String code);
}
