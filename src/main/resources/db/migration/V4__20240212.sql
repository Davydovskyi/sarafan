DROP TABLE IF EXISTS comment;

CREATE TABLE comment
(
    id         BIGSERIAL PRIMARY KEY,
    text       VARCHAR(255) NOT NULL,
    message_id BIGINT       REFERENCES message (id) ON DELETE SET NULL,
    user_id    VARCHAR(255) NOT NULL REFERENCES users (id)
);