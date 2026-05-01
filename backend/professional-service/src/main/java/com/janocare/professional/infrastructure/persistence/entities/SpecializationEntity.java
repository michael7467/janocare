package infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "specializations")
public class SpecializationEntity {

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private ProfessionalEntity professional;

    // --- Getters & Setters ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfessionalEntity getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalEntity professional) {
        this.professional = professional;
    }
}
