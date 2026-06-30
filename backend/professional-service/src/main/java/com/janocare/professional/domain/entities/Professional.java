package com.janocare.professional.domain.entities;

import com.janocare.professional.domain.enums.ProfessionalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Professional {

    private UUID id;

    // User from auth-service
    private UUID userId;

    private UUID professionTypeId;

    private LocalDate practicingFrom;

    private BigDecimal consultationFee;

    private BigDecimal bookingFee;

    private BigDecimal instantConsultationFee;

    private Integer upVotes;

    private Integer downVotes;

    private Integer viewCounts;

    private String bio;

    private ProfessionalStatus status;

    private boolean verified;

    private boolean inpersonEnabled;

    private boolean onlineConsultationEnabled;

    private boolean instantCallEnabled;

    private BigDecimal walletBalance;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // =========================================================
    // RELATIONSHIPS
    // =========================================================

    private List<ProfessionalAchievement> achievements = new ArrayList<>();

    private List<ProfessionalExperience> experiences = new ArrayList<>();

    private List<ProfessionalInfo> infos = new ArrayList<>();

    private List<ProfessionalMembership> memberships = new ArrayList<>();

    private List<ProfessionalQualification> qualifications = new ArrayList<>();

    private List<ProfessionalRegistration> registrations = new ArrayList<>();

    private List<ProfessionalReview> reviews = new ArrayList<>();

    private List<ProfessionalService> services = new ArrayList<>();

    private List<ProfessionalSpecialization> specializations = new ArrayList<>();

    private List<ProfessionalSubSpecialization> subSpecializations = new ArrayList<>();

    // =========================================================

    protected Professional() {}

  public static Professional create(
        UUID userId,
        UUID professionTypeId
) {

    Professional professional = new Professional();

    professional.id = UUID.randomUUID();

    professional.userId = userId;

    professional.professionTypeId = professionTypeId;

    professional.practicingFrom = LocalDate.now();

    professional.consultationFee =
            new BigDecimal("30.00");

    professional.bookingFee =
            new BigDecimal("30.00");

    professional.instantConsultationFee =
            new BigDecimal("30.00");

    professional.upVotes = 0;

    professional.downVotes = 0;

    professional.viewCounts = 0;

    professional.status =
            ProfessionalStatus.APPROVED;

    professional.verified = true;

    professional.inpersonEnabled = true;

    professional.onlineConsultationEnabled = true;

    professional.instantCallEnabled = true;

    professional.walletBalance = BigDecimal.ZERO;

    professional.createdAt = LocalDateTime.now();

    return professional;
}

    public static Professional restore(
            UUID id,
            UUID userId,
            UUID professionTypeId,
            LocalDate practicingFrom,
            BigDecimal consultationFee,
            BigDecimal bookingFee,
            BigDecimal instantConsultationFee,
            Integer upVotes,
            Integer downVotes,
            Integer viewCounts,
            String bio,
            ProfessionalStatus status,
            boolean verified,
            boolean inpersonEnabled,
            boolean onlineConsultationEnabled,
            boolean instantCallEnabled,
            BigDecimal walletBalance,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {

        Professional professional = new Professional();

        professional.id = id;

        professional.userId = userId;

        professional.professionTypeId = professionTypeId;

        professional.practicingFrom = practicingFrom;

        professional.consultationFee = consultationFee;

        professional.bookingFee = bookingFee;

        professional.instantConsultationFee = instantConsultationFee;

        professional.upVotes = upVotes;

        professional.downVotes = downVotes;

        professional.viewCounts = viewCounts;

        professional.bio = bio;

        professional.status = status;

        professional.verified = verified;

        professional.inpersonEnabled = inpersonEnabled;

        professional.onlineConsultationEnabled = onlineConsultationEnabled;

        professional.instantCallEnabled = instantCallEnabled;

        professional.walletBalance = walletBalance;

        professional.createdAt = createdAt;

        professional.updatedAt = updatedAt;

        return professional;
    }

    // =========================================================
    // BUSINESS METHODS
    // =========================================================

    public void updateProfile(
            String bio,
            LocalDate practicingFrom,
            BigDecimal consultationFee,
            BigDecimal bookingFee,
            BigDecimal instantConsultationFee,
            Boolean inpersonEnabled,
            Boolean onlineConsultationEnabled,
            Boolean instantCallEnabled
    ) {

        this.bio = bio;

        this.practicingFrom = practicingFrom;

        if (consultationFee != null) {
            this.consultationFee = consultationFee;
        }

        if (bookingFee != null) {
            this.bookingFee = bookingFee;
        }

        if (instantConsultationFee != null) {
            this.instantConsultationFee = instantConsultationFee;
        }

        if (inpersonEnabled != null) {
            this.inpersonEnabled = inpersonEnabled;
        }

        if (onlineConsultationEnabled != null) {
            this.onlineConsultationEnabled =
                    onlineConsultationEnabled;
        }

        if (instantCallEnabled != null) {
            this.instantCallEnabled =
                    instantCallEnabled;
        }

        this.updatedAt = LocalDateTime.now();
    }

    public void approve() {

        this.status = ProfessionalStatus.APPROVED;

        this.updatedAt = LocalDateTime.now();
    }

    public void reject() {

        this.status = ProfessionalStatus.REJECTED;

        this.updatedAt = LocalDateTime.now();
    }

    public void verify(boolean verified) {

        this.verified = verified;

        this.updatedAt = LocalDateTime.now();
    }

    public void enableOnlineConsultation(
            boolean enabled
    ) {

        this.onlineConsultationEnabled = enabled;

        this.updatedAt = LocalDateTime.now();
    }

    public void enableInstantCall(
            boolean enabled
    ) {

        this.instantCallEnabled = enabled;

        this.updatedAt = LocalDateTime.now();
    }

    public void enableInpersonConsultation(
            boolean enabled
    ) {

        this.inpersonEnabled = enabled;

        this.updatedAt = LocalDateTime.now();
    }

    public void increaseProfileView() {

        if (this.viewCounts == null) {
            this.viewCounts = 0;
        }

        this.viewCounts++;

        this.updatedAt = LocalDateTime.now();
    }

    public void increaseUpVote() {

        if (this.upVotes == null) {
            this.upVotes = 0;
        }

        this.upVotes++;

        this.updatedAt = LocalDateTime.now();
    }

    public void increaseDownVote() {

        if (this.downVotes == null) {
            this.downVotes = 0;
        }

        this.downVotes++;

        this.updatedAt = LocalDateTime.now();
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getProfessionTypeId() {
        return professionTypeId;
    }

    public LocalDate getPracticingFrom() {
        return practicingFrom;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public BigDecimal getBookingFee() {
        return bookingFee;
    }

    public BigDecimal getInstantConsultationFee() {
        return instantConsultationFee;
    }

    public Integer getUpVotes() {
        return upVotes;
    }

    public Integer getDownVotes() {
        return downVotes;
    }

    public Integer getViewCounts() {
        return viewCounts;
    }

    public String getBio() {
        return bio;
    }

    public ProfessionalStatus getStatus() {
        return status;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isInpersonEnabled() {
        return inpersonEnabled;
    }

    public boolean isOnlineConsultationEnabled() {
        return onlineConsultationEnabled;
    }

    public boolean isInstantCallEnabled() {
        return instantCallEnabled;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<ProfessionalAchievement> getAchievements() {
        return achievements;
    }

    public List<ProfessionalExperience> getExperiences() {
        return experiences;
    }

    public List<ProfessionalInfo> getInfos() {
        return infos;
    }

    public List<ProfessionalMembership> getMemberships() {
        return memberships;
    }

    public List<ProfessionalQualification> getQualifications() {
        return qualifications;
    }

    public List<ProfessionalRegistration> getRegistrations() {
        return registrations;
    }

    public List<ProfessionalReview> getReviews() {
        return reviews;
    }

    public List<ProfessionalService> getServices() {
        return services;
    }

    public List<ProfessionalSpecialization> getSpecializations() {
        return specializations;
    }

    public List<ProfessionalSubSpecialization> getSubSpecializations() {
        return subSpecializations;
    }
}