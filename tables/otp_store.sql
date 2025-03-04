CREATE TABLE otp_store (
    user_email VARCHAR(255) PRIMARY KEY,
    otp_code VARCHAR(6),
    expiry_time TIMESTAMP
);
