package com.janocare.auth.infrastructure.persistence.repositories;

import com.janocare.auth.application.ports.UserAccessPort;
import com.janocare.auth.domain.entities.UserAccess;
import com.janocare.auth.infrastructure.persistence.entities.UserAccessEntity;
import com.janocare.auth.infrastructure.persistence.entities.UserEntity;
import com.janocare.auth.infrastructure.persistence.mappers.UserAccessMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class UserAccessRepositoryAdapter implements UserAccessPort {

    @PersistenceContext
    EntityManager em;

    @Override
    public void save(UserAccess access) {
        // Map domain → entity (includes cascading AccessDevice mapping)
        UserAccessEntity entity = UserAccessMapper.toEntity(access);

        // Attach the parent UserEntity
        UserEntity userEntity = em.getReference(UserEntity.class, access.getUserId());
        entity.setUser(userEntity);

        // Persist only the aggregate root
        em.persist(entity);
    }
}
