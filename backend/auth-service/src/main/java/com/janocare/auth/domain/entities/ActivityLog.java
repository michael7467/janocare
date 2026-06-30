package com.janocare.auth.domain.entities;

import java.util.UUID;

public class ActivityLog {

    private UUID id;
    private UUID userId;
    private String logTitle;
    private String logText;
    private String ipAddress;
    private String channel;

    public ActivityLog(UUID userId, String logTitle, String logText, String ipAddress, String channel) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.logTitle = logTitle;
        this.logText = logText;
        this.ipAddress = ipAddress;
        this.channel = channel;
    }

    // Factory for registration
    public static ActivityLog register(User user) {
        return new ActivityLog(
                user.getId(),
                "USER_REGISTERED",
                "User registered successfully",
                "0.0.0.0",   // You can replace with real IP later
                "WEB"
        );
    }
    // Factory for OTP resend
    public static ActivityLog resendOtp(User user) {
        return new ActivityLog(
                user.getId(),
                "OTP_RESENT",
                "A new OTP was sent to the user",
                "0.0.0.0",   // Replace with real IP later
                "WEB"
        );
    }
    public static ActivityLog initialPassword(User user) {
        return new ActivityLog(
                user.getId(),
                "INITIAL_PASSWORD_SENT",
                "Initial password sent to user",
                "0.0.0.0",
                "WEB"
        );
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getLogTitle() { return logTitle; }
    public String getLogText() { return logText; }
    public String getIpAddress() { return ipAddress; }
    public String getChannel() { return channel; }
}
