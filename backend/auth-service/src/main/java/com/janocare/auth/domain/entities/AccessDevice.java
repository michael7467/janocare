package com.janocare.auth.domain.entities;

import java.util.UUID;

public class AccessDevice {

    private UUID id;
    private String deviceName;
    private String deviceType;

    public AccessDevice(String deviceName, String deviceType) {
        this.id = UUID.randomUUID();
        this.deviceName = deviceName;
        this.deviceType = deviceType;
    }
    public static AccessDevice fromRegistration(String deviceName, String deviceType) {
        return new AccessDevice(deviceName, deviceType);
    }


    public UUID getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
