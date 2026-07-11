-- =====================================================
-- Professional Service — Initial Schema
-- V1__init.sql
-- =====================================================

-- ── Profession Types (knowledge level — Accountability pattern) ──
CREATE TABLE profession_types (
    id              UUID            NOT NULL,
    name            VARCHAR(100)    NOT NULL,
    description     VARCHAR(500),
    slot_interval   INTEGER         NOT NULL DEFAULT 30,
    active          BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP,

    CONSTRAINT pk_profession_types PRIMARY KEY (id),
    CONSTRAINT uk_profession_type_name UNIQUE (name)
);

-- ── Professionals (operational level — Accountability pattern) ──
CREATE TABLE professionals (
    id                              UUID            NOT NULL,
    user_id                         UUID            NOT NULL,
    profession_type_id              UUID            NOT NULL,
    practicing_from                 DATE,
    consultation_fee                DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    booking_fee                     DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    instant_consultation_fee        DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    up_votes                        INTEGER         NOT NULL DEFAULT 0,
    down_votes                      INTEGER         NOT NULL DEFAULT 0,
    view_counts                     INTEGER         NOT NULL DEFAULT 0,
    bio                             VARCHAR(1024),
    status                          VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    is_verified                     BOOLEAN         NOT NULL DEFAULT FALSE,
    is_inperson_enabled             BOOLEAN         NOT NULL DEFAULT FALSE,
    is_online_consultation_enabled  BOOLEAN         NOT NULL DEFAULT FALSE,
    is_instant_call_enabled         BOOLEAN         NOT NULL DEFAULT FALSE,
    wallet_balance                  DECIMAL(10,2)   NOT NULL DEFAULT 0.00,
    created_at                      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at                      TIMESTAMP,

    CONSTRAINT pk_professionals
        PRIMARY KEY (id),
    CONSTRAINT uk_professionals_user_id
        UNIQUE (user_id),
    CONSTRAINT fk_professionals_profession_type
        FOREIGN KEY (profession_type_id)
        REFERENCES profession_types(id),
    CONSTRAINT chk_professionals_status
        CHECK (status IN ('PENDING','APPROVED','REJECTED'))
);

CREATE INDEX idx_professionals_user_id
    ON professionals(user_id);
CREATE INDEX idx_professionals_profession_type_id
    ON professionals(profession_type_id);
CREATE INDEX idx_professionals_status
    ON professionals(status);

-- ── Professional Experiences ──
CREATE TABLE professional_experiences (
    id                  UUID            NOT NULL,
    professional_id     UUID            NOT NULL,
    experience          VARCHAR(500)    NOT NULL,
    place               VARCHAR(200),
    start_year          DATE,
    end_year            DATE,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP,

    CONSTRAINT pk_professional_experiences
        PRIMARY KEY (id),
    CONSTRAINT fk_experiences_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_experiences_professional_id
    ON professional_experiences(professional_id);

-- ── Professional Qualifications ──
CREATE TABLE professional_qualifications (
    id                      UUID            NOT NULL,
    professional_id         UUID            NOT NULL,
    qualification_name      VARCHAR(200)    NOT NULL,
    institution_name        VARCHAR(200),
    procurement_year        DATE,
    created_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP,

    CONSTRAINT pk_professional_qualifications
        PRIMARY KEY (id),
    CONSTRAINT fk_qualifications_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_qualifications_professional_id
    ON professional_qualifications(professional_id);

-- ── Professional Achievements ──
CREATE TABLE professional_achievements (
    id                  UUID            NOT NULL,
    professional_id     UUID            NOT NULL,
    award_name          VARCHAR(200)    NOT NULL,
    award_description   VARCHAR(500),
    award_year          DATE,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP,

    CONSTRAINT pk_professional_achievements
        PRIMARY KEY (id),
    CONSTRAINT fk_achievements_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_achievements_professional_id
    ON professional_achievements(professional_id);

-- ── Professional Memberships ──
CREATE TABLE professional_memberships (
    id                  UUID            NOT NULL,
    professional_id     UUID            NOT NULL,
    membership_name     VARCHAR(200)    NOT NULL,
    membership_year     DATE,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP,

    CONSTRAINT pk_professional_memberships
        PRIMARY KEY (id),
    CONSTRAINT fk_memberships_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_memberships_professional_id
    ON professional_memberships(professional_id);

-- ── Professional Registrations ──
CREATE TABLE professional_registrations (
    id                      UUID            NOT NULL,
    professional_id         UUID            NOT NULL,
    registration_name       VARCHAR(200)    NOT NULL,
    registration_number     VARCHAR(100),
    registration_year       DATE,
    created_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP,

    CONSTRAINT pk_professional_registrations
        PRIMARY KEY (id),
    CONSTRAINT fk_registrations_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_registrations_professional_id
    ON professional_registrations(professional_id);

-- ── Professional Reviews ──
CREATE TABLE professional_reviews (
    id                      UUID            NOT NULL,
    professional_id         UUID            NOT NULL,
    user_id                 UUID            NOT NULL,
    overall_rating          DECIMAL(3,1)    NOT NULL,
    wait_time_rating        VARCHAR(20),
    doctor_recommended      BOOLEAN         NOT NULL DEFAULT FALSE,
    comment                 VARCHAR(1000),
    created_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP,

    CONSTRAINT pk_professional_reviews
        PRIMARY KEY (id),
    CONSTRAINT fk_reviews_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE,
    CONSTRAINT chk_overall_rating
        CHECK (overall_rating >= 1.0 AND overall_rating <= 5.0)
);

CREATE INDEX idx_reviews_professional_id
    ON professional_reviews(professional_id);

-- ── Professional Services ──
CREATE TABLE professional_services (
    id                  UUID            NOT NULL,
    professional_id     UUID            NOT NULL,
    service_name        VARCHAR(200)    NOT NULL,
    description         VARCHAR(500),
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP,

    CONSTRAINT pk_professional_services
        PRIMARY KEY (id),
    CONSTRAINT fk_services_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_services_professional_id
    ON professional_services(professional_id);

-- ── Professional Info ──
CREATE TABLE professional_infos (
    id                  UUID            NOT NULL,
    professional_id     UUID            NOT NULL,
    info_type           VARCHAR(100)    NOT NULL,
    info_value          VARCHAR(500),
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP,

    CONSTRAINT pk_professional_infos
        PRIMARY KEY (id),
    CONSTRAINT fk_infos_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_infos_professional_id
    ON professional_infos(professional_id);

-- ── Specializations ──
CREATE TABLE specializations (
    id              UUID            NOT NULL,
    name            VARCHAR(200)    NOT NULL,
    description     VARCHAR(500),
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP,

    CONSTRAINT pk_specializations PRIMARY KEY (id),
    CONSTRAINT uk_specialization_name UNIQUE (name)
);

-- ── Sub Specializations ──
CREATE TABLE sub_specializations (
    id                  UUID            NOT NULL,
    specialization_id   UUID            NOT NULL,
    name                VARCHAR(200)    NOT NULL,
    description         VARCHAR(500),
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP,

    CONSTRAINT pk_sub_specializations
        PRIMARY KEY (id),
    CONSTRAINT fk_sub_spec_specialization
        FOREIGN KEY (specialization_id)
        REFERENCES specializations(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_sub_specializations_specialization_id
    ON sub_specializations(specialization_id);

-- ── Professional Specializations (join table) ──
CREATE TABLE professional_specializations (
    id                  UUID    NOT NULL,
    professional_id     UUID    NOT NULL,
    specialization_id   UUID    NOT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_professional_specializations
        PRIMARY KEY (id),
    CONSTRAINT fk_prof_spec_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_prof_spec_specialization
        FOREIGN KEY (specialization_id)
        REFERENCES specializations(id)
        ON DELETE CASCADE,
    CONSTRAINT uk_professional_specialization
        UNIQUE (professional_id, specialization_id)
);

-- ── Professional Sub Specializations (join table) ──
CREATE TABLE professional_sub_specializations (
    id                      UUID    NOT NULL,
    professional_id         UUID    NOT NULL,
    sub_specialization_id   UUID    NOT NULL,
    created_at              TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_professional_sub_specializations
        PRIMARY KEY (id),
    CONSTRAINT fk_prof_sub_spec_professional
        FOREIGN KEY (professional_id)
        REFERENCES professionals(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_prof_sub_spec_sub_specialization
        FOREIGN KEY (sub_specialization_id)
        REFERENCES sub_specializations(id)
        ON DELETE CASCADE,
    CONSTRAINT uk_professional_sub_specialization
        UNIQUE (professional_id, sub_specialization_id)
);