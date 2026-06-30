package com.janocare.auth.domain.entities;

import com.janocare.auth.infrastructure.persistence.enums.Channel;
import com.janocare.auth.infrastructure.persistence.enums.UserAccessStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserAccess {

    private UUID id;
    private UUID userId;
    private Channel accessChannel;
    private String clientName;
    private String allowedUrls;
    private String apiClientId;
    private String deviceUuid;
    private Integer otpCode;
    private String secretHash;
    private String tempSecretHash;
    private String emailActivationToken;
    private String firebaseToken;
    private UserAccessStatus status = UserAccessStatus.PENDING;

    // ⭐ REQUIRED for cascading
    private List<AccessDevice> accessDevices = new ArrayList<>();

    // ---------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------
    public UserAccess(UUID userId, Channel accessChannel) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.accessChannel = accessChannel;
    }
    public static UserAccess createInitial(User user) {
        return new UserAccess(
                user.getId(),
                Channel.WEB // or MOBILE, depending on your flow
        );
    }

    // ---------------------------------------------------------
    // Getters
    // ---------------------------------------------------------
    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Channel getAccessChannel() {
        return accessChannel;
    }

    public String getClientName() {
        return clientName;
    }

    public String getAllowedUrls() {
        return allowedUrls;
    }

    public String getApiClientId() {
        return apiClientId;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public Integer getOtpCode() {
        return otpCode;
    }

    public String getSecretHash() {
        return secretHash;
    }

    public String getTempSecretHash() {
        return tempSecretHash;
    }

    public String getEmailActivationToken() {
        return emailActivationToken;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public UserAccessStatus getStatus() {
        return status;
    }

    // ⭐ REQUIRED by mapper
    public List<AccessDevice> getAccessDevices() {
        return accessDevices;
    }

    // ---------------------------------------------------------
    // Aggregate behavior
    // ---------------------------------------------------------
    public void addDevice(AccessDevice device) {
        this.accessDevices.add(device);
    }
}
