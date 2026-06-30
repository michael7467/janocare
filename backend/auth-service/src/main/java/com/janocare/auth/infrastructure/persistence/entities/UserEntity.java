package com.janocare.auth.infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "realm", nullable = false)
    private String realm;

    @Column(name = "status", nullable = false)
    private String status;

    // NEW FIELD
    @Column(name = "must_change_password", nullable = false)
    private boolean mustChangePassword;

    // -------------------------
    // GETTERS
    // -------------------------
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public String getRealm() { return realm; }
    public String getStatus() { return status; }
    public boolean isMustChangePassword() { return mustChangePassword; }

    // -------------------------
    // SETTERS
    // -------------------------
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRole(String role) { this.role = role; }
    public void setRealm(String realm) { this.realm = realm; }
    public void setStatus(String status) { this.status = status; }
    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }
}
