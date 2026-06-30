CREATE TABLE activity_logs (
                               id UUID PRIMARY KEY,

                               log_title VARCHAR(255) NOT NULL,
                               log_text TEXT NOT NULL,
                               ip_address VARCHAR(100) NOT NULL,
                               channel VARCHAR(50),
                               user_id UUID,

    -- BaseEntity fields (REQUIRED)
                               created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                               updated_at TIMESTAMP,
                               deleted_at TIMESTAMP,

                               CONSTRAINT fk_activity_user FOREIGN KEY (user_id) REFERENCES users(id)
);
