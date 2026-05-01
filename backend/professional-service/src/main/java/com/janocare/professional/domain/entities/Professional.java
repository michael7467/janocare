package domain.entities;

import domain.enums.ApprovalStatus;
import domain.valueobjects.FullName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Professional {

    private final UUID id;
    private FullName name;
    private ApprovalStatus approvalStatus;

    private final List<Specialization> specializations = new ArrayList<>();
    private final List<AvailabilitySlot> availability = new ArrayList<>();
    private final List<ProfessionalReview> reviews = new ArrayList<>();

    public Professional(UUID id, FullName name) {
        this.id = id;
        this.name = name;
        this.approvalStatus = ApprovalStatus.PENDING;
    }

    public UUID getId() { return id; }
    public FullName getName() { return name; }
    public ApprovalStatus getApprovalStatus() { return approvalStatus; }

    public List<Specialization> getSpecializations() { return specializations; }
    public List<AvailabilitySlot> getAvailability() { return availability; }
    public List<ProfessionalReview> getReviews() { return reviews; }

    public void approve() {
        this.approvalStatus = ApprovalStatus.APPROVED;
    }

    public void reject() {
        this.approvalStatus = ApprovalStatus.REJECTED;
    }

    public void addSpecialization(Specialization specialization) {
        specializations.add(specialization);
    }

    public void addAvailability(AvailabilitySlot slot) {
        availability.add(slot);
    }

    public void addReview(ProfessionalReview review) {
        reviews.add(review);
    }
}
