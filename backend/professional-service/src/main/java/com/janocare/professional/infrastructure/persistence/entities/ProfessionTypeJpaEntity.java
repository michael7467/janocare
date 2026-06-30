package com.janocare.professional.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "profession_types",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_profession_type_name",
                        columnNames = "name"
                )
        }
)
public class ProfessionTypeJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    @Column(nullable = false, unique = true)
    public String name;

    @Column(length = 500)
    public String description;

    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "professionType",
            fetch = FetchType.LAZY
    )
    public List<ProfessionalJpaEntity> professionals = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}