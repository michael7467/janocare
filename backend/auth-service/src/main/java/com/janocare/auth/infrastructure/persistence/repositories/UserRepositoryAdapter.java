package com.janocare.auth.infrastructure.persistence.repositories;

import com.janocare.auth.application.ports.UserRepositoryPort;
import com.janocare.auth.domain.entities.User;
import com.janocare.auth.infrastructure.persistence.mappers.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort {

    @Inject
    UserJpaRepository jpaRepository;

    @Override
    @Transactional
    public User save(User user) {
        var entity = UserMapper.toEntity(user);
        var saved = jpaRepository.getEntityManager().merge(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findByIdOptional(id)
                .map(UserMapper::toDomain);
    }
}
