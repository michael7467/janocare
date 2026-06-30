CREATE TABLE user_profiles (
                               id UUID PRIMARY KEY,
                               user_id UUID NOT NULL,

                               first_name VARCHAR(255),
                               last_name VARCHAR(255),
                               profile_pic VARCHAR(500),
                               gender VARCHAR(50),

                               country_id UUID,
                               state_id UUID,
                               city_id UUID,

                               created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                               updated_at TIMESTAMP,
                               deleted_at TIMESTAMP,

                               CONSTRAINT fk_user_profile_user
                                   FOREIGN KEY (user_id) REFERENCES users(id),

                               CONSTRAINT fk_user_profile_country
                                   FOREIGN KEY (country_id) REFERENCES countries(id),

                               CONSTRAINT fk_user_profile_state
                                   FOREIGN KEY (state_id) REFERENCES states(id),

                               CONSTRAINT fk_user_profile_city
                                   FOREIGN KEY (city_id) REFERENCES cities(id)
);