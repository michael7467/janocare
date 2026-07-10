package com.janocare.auth.domain.entities;

import com.janocare.auth.domain.enums.*;
import com.janocare.auth.domain.valueobjects.*;
import java.time.Instant;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private Email email;
    private PhoneNumber phone;
    private PasswordHash passwordHash;
    private UserRole role;
    private UserRealm realm;
    private UserStatus status;
    private Instant createdAt;
    private boolean mustChangePassword;
    private User() {}
    // ---------------------------------------------------------
    // Factory for creating NEW users
    // ---------------------------------------------------------
    public static User create(String username, String email, String phone, String hashedPassword) {
        User user = new User();
        user.id = UUID.randomUUID();
        user.username = username;
        user.email = new Email(email);
        user.phone = new PhoneNumber(phone);
        user.passwordHash = new PasswordHash(hashedPassword);
        user.role = UserRole.PATIENT;
        user.realm = UserRealm.INTERNAL;
        user.status = UserStatus.PENDING_VERIFICATION;
        user.createdAt = Instant.now();

        // NEW: initial password must be changed after OTP verification
        user.mustChangePassword = true;

        return user;
    }

    // ---------------------------------------------------------
    // Hydration factory for reconstructing from persistence
    // ---------------------------------------------------------
    public static User hydrate(
            UUID id,
            String username,
            Email email,
            PhoneNumber phone,
            PasswordHash passwordHash,
            UserRole role,
            UserRealm realm,
            UserStatus status,
            Instant createdAt,
            boolean mustChangePassword
    ) {
        User user = new User();
        user.id = id;
        user.username = username;
        user.email = email;
        user.phone = phone;
        user.passwordHash = passwordHash;
        user.role = role;
        user.realm = realm;
        user.status = status;
        user.createdAt = createdAt;
        user.mustChangePassword = mustChangePassword;
        return user;
    }

    // ---------------------------------------------------------
    // Domain behavior
    // ---------------------------------------------------------
    public void activate() {
        this.status = UserStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    public void changeRole(UserRole newRole) {
        this.role = newRole;
    }

    public void updatePassword(String hashedPassword) {
        this.passwordHash = new PasswordHash(hashedPassword);
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    // NEW: mark password change requirement
    public void setMustChangePassword(boolean value) {
        this.mustChangePassword = value;
    }

    // ---------------------------------------------------------
    // Getters
    // ---------------------------------------------------------
    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public Email getEmail() { return email; }
    public PhoneNumber getPhone() { return phone; }
    public PasswordHash getPasswordHash() { return passwordHash; }
    public UserRole getRole() { return role; }
    public UserRealm getRealm() { return realm; }
    public UserStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }

    // NEW getter
    public boolean isMustChangePassword() { return mustChangePassword; }
}
