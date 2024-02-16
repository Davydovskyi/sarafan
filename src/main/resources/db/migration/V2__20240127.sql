DROP TABLE IF EXISTS message;

CREATE TABLE message
(
    id               BIGSERIAL PRIMARY KEY,
    text             VARCHAR(2048) NOT NULL,
    user_id          VARCHAR(255)  NOT NULL REFERENCES users (id),
    link             VARCHAR(255),
    link_title       VARCHAR(255),
    link_description VARCHAR(255),
    link_cover       VARCHAR(255),
    updated_at       TIMESTAMP,
    created_at       TIMESTAMP
);