package com.janocare.auth.infrastructure.persistence.mappers;

import com.janocare.auth.domain.entities.UserAccess;
import com.janocare.auth.domain.entities.AccessDevice;
import com.janocare.auth.infrastructure.persistence.entities.UserAccessEntity;
import com.janocare.auth.infrastructure.persistence.entities.AccessDeviceEntity;

import java.util.stream.Collectors;

public class UserAccessMapper {

    // ---------------------------------------------------------
    // Domain → Entity (with cascading AccessDevice mapping)
    // ---------------------------------------------------------
    public static UserAccessEntity toEntity(UserAccess domain) {
        UserAccessEntity entity = new UserAccessEntity();

        entity.setId(domain.getId());
        entity.setAccessChannel(domain.getAccessChannel());
        entity.setClientName(domain.getClientName());
        entity.setAllowedUrls(domain.getAllowedUrls());
        entity.setApiClientId(domain.getApiClientId());
        entity.setDeviceUuid(domain.getDeviceUuid());
        entity.setOtpCode(domain.getOtpCode());
        entity.setSecretHash(domain.getSecretHash());
        entity.setTempSecretHash(domain.getTempSecretHash());
        entity.setEmailActivationToken(domain.getEmailActivationToken());
        entity.setFirebaseToken(domain.getFirebaseToken());
        entity.setStatus(domain.getStatus());

        // 🔥 Map AccessDevices and attach them to the aggregate root
        var deviceEntities = domain.getAccessDevices()
                .stream()
                .map(UserAccessMapper::mapDevice)
                .collect(Collectors.toList());

        // Set both sides of the relationship
        deviceEntities.forEach(d -> d.setUserAccess(entity));
        entity.setAccessDevices(deviceEntities);

        return entity;
    }

    private static AccessDeviceEntity mapDevice(AccessDevice device) {
        AccessDeviceEntity entity = new AccessDeviceEntity();
        entity.setId(device.getId());
        entity.setDeviceName(device.getDeviceName());
        entity.setDeviceType(device.getDeviceType());
        return entity;
    }

    // ---------------------------------------------------------
    // Entity → Domain (reflection-based immutable reconstruction)
    // ---------------------------------------------------------
    public static UserAccess toDomain(UserAccessEntity entity) {
        UserAccess domain = new UserAccess(
                entity.getUser().getId(),
                entity.getAccessChannel()
        );

        try {
            var idField = UserAccess.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(domain, entity.getId());

            var clientNameField = UserAccess.class.getDeclaredField("clientName");
            clientNameField.setAccessible(true);
            clientNameField.set(domain, entity.getClientName());

            var allowedUrlsField = UserAccess.class.getDeclaredField("allowedUrls");
            allowedUrlsField.setAccessible(true);
            allowedUrlsField.set(domain, entity.getAllowedUrls());

            var apiClientIdField = UserAccess.class.getDeclaredField("apiClientId");
            apiClientIdField.setAccessible(true);
            apiClientIdField.set(domain, entity.getApiClientId());

            var deviceUuidField = UserAccess.class.getDeclaredField("deviceUuid");
            deviceUuidField.setAccessible(true);
            deviceUuidField.set(domain, entity.getDeviceUuid());

            var otpCodeField = UserAccess.class.getDeclaredField("otpCode");
            otpCodeField.setAccessible(true);
            otpCodeField.set(domain, entity.getOtpCode());

            var secretHashField = UserAccess.class.getDeclaredField("secretHash");
            secretHashField.setAccessible(true);
            secretHashField.set(domain, entity.getSecretHash());

            var tempSecretHashField = UserAccess.class.getDeclaredField("tempSecretHash");
            tempSecretHashField.setAccessible(true);
            tempSecretHashField.set(domain, entity.getTempSecretHash());

            var emailTokenField = UserAccess.class.getDeclaredField("emailActivationToken");
            emailTokenField.setAccessible(true);
            emailTokenField.set(domain, entity.getEmailActivationToken());

            var firebaseField = UserAccess.class.getDeclaredField("firebaseToken");
            firebaseField.setAccessible(true);
            firebaseField.set(domain, entity.getFirebaseToken());

            var statusField = UserAccess.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(domain, entity.getStatus());

        } catch (Exception e) {
            throw new RuntimeException("Failed to map UserAccessEntity → UserAccess", e);
        }

        return domain;
    }
}
