package com.janocare.auth.infrastructure.persistence.mappers;

import com.janocare.auth.domain.entities.ActivityLog;
import com.janocare.auth.infrastructure.persistence.entities.ActivityLogEntity;

public class ActivityLogMapper {

    public static ActivityLogEntity toEntity(ActivityLog domain) {
        ActivityLogEntity entity = new ActivityLogEntity();
        entity.setId(domain.getId());
        entity.setLogTitle(domain.getLogTitle());
        entity.setLogText(domain.getLogText());
        entity.setIpAddress(domain.getIpAddress());
        entity.setChannel(domain.getChannel());
        return entity;
    }

    public static ActivityLog toDomain(ActivityLogEntity entity) {
        ActivityLog domain = new ActivityLog(
                entity.getUser().getId(),
                entity.getLogTitle(),
                entity.getLogText(),
                entity.getIpAddress(),
                entity.getChannel()
        );

        try {
            var idField = ActivityLog.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(domain, entity.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to map ActivityLogEntity → ActivityLog", e);
        }

        return domain;
    }
}
