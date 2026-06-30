package com.janocare.auth.infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "access_devices")
public class AccessDeviceEntity extends BaseEntity {

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_type")
    private String deviceType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_access_id", nullable = false)
    private UserAccessEntity userAccess;

    // -------------------------
    // Getters
    // -------------------------

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public UserAccessEntity getUserAccess() {
        return userAccess;
    }

    // -------------------------
    // Setters
    // -------------------------

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setUserAccess(UserAccessEntity userAccess) {
        this.userAccess = userAccess;
    }
}
