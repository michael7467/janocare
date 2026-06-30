CREATE TABLE countries (
                           id UUID PRIMARY KEY,

                           country_name VARCHAR(255) NOT NULL,
                           phone_prefix VARCHAR(50) NOT NULL,
                           is_active BOOLEAN NOT NULL DEFAULT TRUE,

                           created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                           updated_at TIMESTAMP
);

CREATE TABLE states (
                        id UUID PRIMARY KEY,

                        country_id UUID NOT NULL,
                        state_name VARCHAR(255) NOT NULL,
                        is_active BOOLEAN NOT NULL DEFAULT TRUE,

                        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                        updated_at TIMESTAMP,

                        CONSTRAINT fk_state_country
                            FOREIGN KEY (country_id) REFERENCES countries(id)
);

CREATE TABLE cities (
                        id UUID PRIMARY KEY,

                        country_id UUID NOT NULL,
                        state_id UUID NOT NULL,
                        city_name VARCHAR(255) NOT NULL,
                        is_active BOOLEAN NOT NULL DEFAULT TRUE,

                        created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                        updated_at TIMESTAMP,

                        CONSTRAINT fk_city_country
                            FOREIGN KEY (country_id) REFERENCES countries(id),

                        CONSTRAINT fk_city_state
                            FOREIGN KEY (state_id) REFERENCES states(id)
);