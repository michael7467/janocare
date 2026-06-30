package com.janocare.notification.infrastructure.persistence.repositories;

import com.janocare.notification.application.ports.NotificationPort;

import com.janocare.notification.domain.entities.Notification;

import com.janocare.notification.infrastructure.persistence.entities.NotificationEntity;

import com.janocare.notification.infrastructure.persistence.mappers.NotificationMapper;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class NotificationRepositoryAdapter
        implements NotificationPort {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Notification save(
            Notification notification
    ) {

        NotificationEntity entity =
                NotificationMapper.toEntity(notification);

        NotificationEntity saved =
                em.merge(entity);

        return NotificationMapper.toDomain(saved);
    }

    @Override
    public Optional<Notification> findNotificationById(
            UUID id
    ) {

        NotificationEntity entity =
                em.find(NotificationEntity.class, id);

        if (entity == null) {
            return Optional.empty();
        }

        return Optional.of(
                NotificationMapper.toDomain(entity)
        );
    }

    @Override
    public List<Notification> findAllNotifications() {

        return em.createQuery(
                        "FROM NotificationEntity",
                        NotificationEntity.class
                )
                .getResultList()
                .stream()
                .map(NotificationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Notification> findNotificationsByUserId(
            UUID userId
    ) {

        return em.createQuery(
                        "FROM NotificationEntity n WHERE n.userId = :userId",
                        NotificationEntity.class
                )
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .map(NotificationMapper::toDomain)
                .toList();
    }
}