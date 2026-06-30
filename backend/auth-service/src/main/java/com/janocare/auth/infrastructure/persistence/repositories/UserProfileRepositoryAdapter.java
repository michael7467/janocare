package com.janocare.auth.infrastructure.persistence.repositories;

import com.janocare.auth.application.ports.UserProfilePort;
import com.janocare.auth.domain.entities.UserProfile;
import com.janocare.auth.infrastructure.persistence.entities.UserProfileEntity;
import com.janocare.auth.infrastructure.persistence.mappers.UserProfileMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserProfileRepositoryAdapter implements UserProfilePort {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public UserProfile save(UserProfile profile) {
        UserProfileEntity entity = UserProfileMapper.toEntity(profile);

        if (entity.getCreatedAt() == null) {
            em.persist(entity);
            return UserProfileMapper.toDomain(entity);
        }

        UserProfileEntity saved = em.merge(entity);
        return UserProfileMapper.toDomain(saved);
    }

    @Override
    public Optional<UserProfile> findByUserId(UUID userId) {
        return em.createQuery(
                        "SELECT p FROM UserProfileEntity p WHERE p.userId = :userId",
                        UserProfileEntity.class
                )
                .setParameter("userId", userId)
                .getResultStream()
                .findFirst()
                .map(UserProfileMapper::toDomain);
    }
}