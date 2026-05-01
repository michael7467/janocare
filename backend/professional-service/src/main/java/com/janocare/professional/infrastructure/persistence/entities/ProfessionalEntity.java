package infrastructure.persistence.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "professionals")
public class ProfessionalEntity {

    @Id
    private UUID id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private domain.enums.ApprovalStatus approvalStatus;

    // --- Getters & Setters ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public domain.enums.ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(domain.enums.ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
