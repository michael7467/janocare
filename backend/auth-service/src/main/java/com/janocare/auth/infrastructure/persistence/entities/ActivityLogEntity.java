package com.janocare.auth.infrastructure.persistence.entities;

import jakarta.persistence.*;   // <-- REQUIRED
import java.util.UUID;         // <-- If needed
import java.time.Instant;      // <-- If needed

@Entity
@Table(name = "activity_logs")
public class ActivityLogEntity extends BaseEntity {

    @Column(name = "log_title", nullable = false)
    private String logTitle;

    @Column(name = "log_text", columnDefinition = "TEXT")
    private String logText;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "channel")
    private String channel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;   // <-- MUST import UserEntity

    // --- Getters ---
    public String getLogTitle() {
        return logTitle;
    }

    public String getLogText() {
        return logText;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getChannel() {
        return channel;
    }

    public UserEntity getUser() {
        return user;
    }

    // --- Setters ---
    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
