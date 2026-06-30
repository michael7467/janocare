INSERT INTO users (id, username, email, phone, password_hash, role, realm, status, created_at)
VALUES (
           '00000000-0000-0000-0000-000000000001',
           'admin',
           'admin@janocare.com',
           '+0000000000',
           '$2a$10$HASHEDPASSWORDHERE',
           'ADMIN',
           'INTERNAL',
           'ACTIVE',
           NOW()
       );
