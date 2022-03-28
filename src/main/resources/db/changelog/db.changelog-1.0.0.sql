CREATE TABLE user_role
(
    id                      BIGSERIAL PRIMARY KEY,
    name                    VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE users
(
    id                      BIGSERIAL PRIMARY KEY,
    email                   VARCHAR(128) UNIQUE NOT NULL,
    password                VARCHAR(128) NOT NULL,
    user_role_id            BIGINT REFERENCES user_role(id) NOT NULL
);

INSERT INTO user_role (id, name) VALUES(1, 'USER');
INSERT INTO user_role (id, name) VALUES(2, 'ADMIN');
INSERT INTO user_role (id, name) VALUES(3, 'MODERATOR');