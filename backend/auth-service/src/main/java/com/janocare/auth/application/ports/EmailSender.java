package com.janocare.auth.application.ports;

public interface EmailSender {
    void sendOtpEmail(String to, String code);
}
