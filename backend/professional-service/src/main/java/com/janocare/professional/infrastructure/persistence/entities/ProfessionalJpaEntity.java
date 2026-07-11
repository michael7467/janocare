public class ProfessionalJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    public UUID id;

    // Reference by Identity — auth service (cross-context, no FK constraint)
    @Column(name = "user_id", nullable = false, unique = true)
    public UUID userId;

    // Real FK — same database (intra-domain)
    // Many professionals → One profession type (knowledge level)
    // Accountability pattern: profession type governs slot generation
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "profession_type_id",
            referencedColumnName = "id",
            nullable = false
    )
    public ProfessionTypeJpaEntity professionType;

    // Plain UUID column for mapper convenience
    // Avoids loading the full ProfessionType just to get the ID
    @Column(
            name = "profession_type_id",
            nullable = false,
            insertable = false,
            updatable = false
    )
    public UUID professionTypeId;

    @Column(name = "practicing_from")
    public LocalDate practicingFrom;

    @Column(
            name = "consultation_fee",
            precision = 10,
            scale = 2,
            nullable = false
    )
    public BigDecimal consultationFee = BigDecimal.ZERO;

    @Column(
            name = "booking_fee",
            precision = 10,
            scale = 2,
            nullable = false
    )
    public BigDecimal bookingFee = BigDecimal.ZERO;

    @Column(
            name = "instant_consultation_fee",
            precision = 10,
            scale = 2,
            nullable = false
    )
    public BigDecimal instantConsultationFee = BigDecimal.ZERO;

    @Column(name = "up_votes", nullable = false)
    public Integer upVotes = 0;

    @Column(name = "down_votes", nullable = false)
    public Integer downVotes = 0;

    @Column(name = "view_counts", nullable = false)
    public Integer viewCounts = 0;

    @Column(length = 1024)
    public String bio;

    // State pattern — PENDING → APPROVED | REJECTED
    // Guard conditions enforced in Professional.approve() and reject()
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ProfessionalStatus status = ProfessionalStatus.PENDING;

    @Column(name = "is_verified", nullable = false)
    public boolean verified = false;

    @Column(name = "is_inperson_enabled", nullable = false)
    public boolean inpersonEnabled = false;

    @Column(name = "is_online_consultation_enabled", nullable = false)
    public boolean onlineConsultationEnabled = false;

    @Column(name = "is_instant_call_enabled", nullable = false)
    public boolean instantCallEnabled = false;

    @Column(
            name = "wallet_balance",
            precision = 10,
            scale = 2,
            nullable = false
    )
    public BigDecimal walletBalance = BigDecimal.ZERO;

    // =========================================================
    // RELATIONSHIPS — owned collections
    // CascadeType.ALL + orphanRemoval = true:
    // deleting Professional cascades to all child entities
    // removing from list triggers DELETE on that child row
    // =========================================================

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalAchievementJpaEntity> achievements
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalExperienceJpaEntity> experiences
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalInfoJpaEntity> infos
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalMembershipJpaEntity> memberships
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalQualificationJpaEntity> qualifications
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalRegistrationJpaEntity> registrations
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalReviewJpaEntity> reviews
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalServiceJpaEntity> services
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalSpecializationJpaEntity> specializations
            = new ArrayList<>();

    @OneToMany(mappedBy = "professional", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProfessionalSubSpecializationJpaEntity> subSpecializations
            = new ArrayList<>();

    // =========================================================

    // updatable = false — creation timestamp never changes
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.updatedAt == null) this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}