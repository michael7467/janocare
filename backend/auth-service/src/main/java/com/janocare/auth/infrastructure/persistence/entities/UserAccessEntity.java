package com.janocare.auth.infrastructure.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.janocare.auth.infrastructure.persistence.enums.Channel;
import com.janocare.auth.infrastructure.persistence.enums.UserAccessStatus;

@Entity
@Table(
        name = "user_accesses",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"access_channel", "user_id", "api_client_id"}
        )
)
public class UserAccessEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "access_channel", nullable = false)
    private Channel accessChannel;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "allowed_urls", length = 2048)
    private String allowedUrls;

    @JsonIgnore
    @Column(name = "api_client_id")
    private String apiClientId;

    @JsonIgnore
    @Column(name = "device_uuid")
    private String deviceUuid;

    @JsonIgnore
    @Column(name = "otp_code")
    private Integer otpCode;

    @JsonIgnore
    @Column(name = "secret_hash")
    private String secretHash;

    @JsonIgnore
    @Column(name = "temp_secret_hash")
    private String tempSecretHash;

    @JsonIgnore
    @Column(name = "email_activation_token")
    private String emailActivationToken;

    @Column(name = "firebase_token")
    private String firebaseToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserAccessStatus status = UserAccessStatus.PENDING;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "userAccess", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccessDeviceEntity> accessDevices = new ArrayList<>();

    // -------------------------
    // Getters
    // -------------------------

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

    public UserEntity getUser() {
        return user;
    }

    public List<AccessDeviceEntity> getAccessDevices() {
        return accessDevices;
    }

    // -------------------------
    // Setters
    // -------------------------

    public void setAccessChannel(Channel accessChannel) {
        this.accessChannel = accessChannel;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setAllowedUrls(String allowedUrls) {
        this.allowedUrls = allowedUrls;
    }

    public void setApiClientId(String apiClientId) {
        this.apiClientId = apiClientId;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public void setOtpCode(Integer otpCode) {
        this.otpCode = otpCode;
    }

    public void setSecretHash(String secretHash) {
        this.secretHash = secretHash;
    }

    public void setTempSecretHash(String tempSecretHash) {
        this.tempSecretHash = tempSecretHash;
    }

    public void setEmailActivationToken(String emailActivationToken) {
        this.emailActivationToken = emailActivationToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public void setStatus(UserAccessStatus status) {
        this.status = status;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setAccessDevices(List<AccessDeviceEntity> accessDevices) {
        this.accessDevices = accessDevices;
    }

    // -------------------------
    // Aggregate helper
    // -------------------------

    public void addAccessDevice(AccessDeviceEntity device) {
        device.setUserAccess(this);
        this.accessDevices.add(device);
    }
}
