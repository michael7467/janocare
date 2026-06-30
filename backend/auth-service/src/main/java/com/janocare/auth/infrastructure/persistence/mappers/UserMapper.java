package com.janocare.auth.infrastructure.persistence.mappers;

import com.janocare.auth.domain.entities.User;
import com.janocare.auth.domain.enums.*;
import com.janocare.auth.domain.valueobjects.*;
import com.janocare.auth.infrastructure.persistence.entities.UserEntity;

public class UserMapper {

    // -----------------------------------------
    // Convert Domain → Persistence Entity
    // -----------------------------------------
    public static UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail().getValue());
        entity.setPhone(domain.getPhone().getValue());
        entity.setPasswordHash(domain.getPasswordHash().getValue());
        entity.setRole(domain.getRole().name());
        entity.setRealm(domain.getRealm().name());
        entity.setStatus(domain.getStatus().name());
        entity.setCreatedAt(domain.getCreatedAt());

        // NEW FIELD
        entity.setMustChangePassword(domain.isMustChangePassword());

        return entity;
    }

    // -----------------------------------------
    // Convert Persistence Entity → Domain
    // -----------------------------------------
    public static User toDomain(UserEntity entity) {
        return User.hydrate(
                entity.getId(),
                entity.getUsername(),
                new Email(entity.getEmail()),
                new PhoneNumber(entity.getPhone()),
                new PasswordHash(entity.getPasswordHash()),
                UserRole.valueOf(entity.getRole()),
                UserRealm.valueOf(entity.getRealm()),
                UserStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),

                // NEW FIELD
                entity.isMustChangePassword()
        );
    }
}
