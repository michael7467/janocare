CREATE TABLE user_accesses (
                               id UUID PRIMARY KEY,

                               access_channel VARCHAR(50) NOT NULL,
                               client_name VARCHAR(255),
                               allowed_urls VARCHAR(2048),
                               api_client_id VARCHAR(255),
                               device_uuid VARCHAR(255),
                               otp_code INT,
                               secret_hash VARCHAR(255),
                               temp_secret_hash VARCHAR(255),
                               email_activation_token VARCHAR(255),
                               firebase_token VARCHAR(255),
                               status VARCHAR(50) NOT NULL,

                               user_id UUID NOT NULL,

    -- BaseEntity fields (REQUIRED)
                               created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                               updated_at TIMESTAMP,
                               deleted_at TIMESTAMP,

                               CONSTRAINT fk_user_access_user FOREIGN KEY (user_id) REFERENCES users(id),
                               CONSTRAINT unique_access UNIQUE (access_channel, user_id, api_client_id)
);
