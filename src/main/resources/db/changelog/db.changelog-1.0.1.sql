CREATE TABLE user_report
(
    id                      BIGSERIAL PRIMARY KEY,
    description             VARCHAR(512),
    latitude                DOUBLE PRECISION NOT NULL,
    longitude               DOUBLE PRECISION NOT NULL,
    image_url               VARCHAR(512),
    user_id                 BIGINT REFERENCES users(id) NOT NULL
);