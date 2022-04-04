CREATE TABLE company
(
    id                       BIGSERIAL PRIMARY KEY,
    name                    VARCHAR(128) NOT NULL
);

ALTER TABLE users ADD COLUMN company_id BIGINT REFERENCES company(id);