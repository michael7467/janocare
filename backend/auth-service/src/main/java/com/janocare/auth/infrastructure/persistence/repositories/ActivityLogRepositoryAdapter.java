package com.janocare.auth.infrastructure.persistence.repositories;

import com.janocare.auth.application.ports.ActivityLogPort;
import com.janocare.auth.domain.entities.ActivityLog;
import com.janocare.auth.infrastructure.persistence.entities.ActivityLogEntity;
import com.janocare.auth.infrastructure.persistence.entities.UserEntity;
import com.janocare.auth.infrastructure.persistence.mappers.ActivityLogMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class ActivityLogRepositoryAdapter implements ActivityLogPort {

    @PersistenceContext
    EntityManager em;

    @Override
    public void save(ActivityLog log) {
        ActivityLogEntity entity = ActivityLogMapper.toEntity(log);

        // 🔥 REQUIRED: attach the user so user_id is not null
        UserEntity userEntity = em.getReference(UserEntity.class, log.getUserId());
        entity.setUser(userEntity);

        em.persist(entity);
    }
}
