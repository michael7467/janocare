package com.janocare.auth.infrastructure.persistence.mappers;

import com.janocare.auth.domain.entities.AccessDevice;
import com.janocare.auth.infrastructure.persistence.entities.AccessDeviceEntity;

public class AccessDeviceMapper {

    // ---------------------------------------------------------
    // Domain → Entity
    // ---------------------------------------------------------
    public static AccessDeviceEntity toEntity(AccessDevice domain) {
        AccessDeviceEntity entity = new AccessDeviceEntity();
        entity.setId(domain.getId());
        entity.setDeviceName(domain.getDeviceName());
        entity.setDeviceType(domain.getDeviceType());
        return entity;
    }

    // ---------------------------------------------------------
    // Entity → Domain
    // ---------------------------------------------------------
    public static AccessDevice toDomain(AccessDeviceEntity entity) {
        // Use the new constructor (String, String)
        AccessDevice domain = new AccessDevice(
                entity.getDeviceName(),
                entity.getDeviceType()
        );

        // Restore ID via reflection (same pattern as UserAccess)
        try {
            var idField = AccessDevice.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(domain, entity.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to map AccessDeviceEntity → AccessDevice", e);
        }

        return domain;
    }
}
