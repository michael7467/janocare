package com.janocare.auth.infrastructure.persistence.repositories;

import com.janocare.auth.infrastructure.persistence.entities.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    public void save(UserEntity entity) {
        em.persist(entity);
    }

    public Optional<UserEntity> findById(UUID id) {
        return Optional.ofNullable(em.find(UserEntity.class, id));
    }

    public Optional<UserEntity> findByEmail(String email) {
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    public void update(UserEntity entity) {
        em.merge(entity);
    }
}
