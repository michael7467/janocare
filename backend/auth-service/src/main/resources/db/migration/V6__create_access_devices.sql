CREATE TABLE access_devices (
                                id UUID PRIMARY KEY,

                                device_name VARCHAR(255) NOT NULL,
                                device_type VARCHAR(50) NOT NULL,

                                user_access_id UUID NOT NULL,

    -- BaseEntity fields
                                created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                updated_at TIMESTAMP,
                                deleted_at TIMESTAMP,

                                CONSTRAINT fk_access_device_user_access
                                    FOREIGN KEY (user_access_id) REFERENCES user_accesses(id)
);
