CREATE TABLE company_tag
(
    id                      BIGSERIAL PRIMARY KEY,
    name                    VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE tags_companies
(
    tag_id                      BIGINT REFERENCES company_tag(id) NOT NULL,
    company_id                  BIGINT REFERENCES company(id) NOT NULL
);