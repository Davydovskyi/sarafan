DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         varchar(255) UNIQUE PRIMARY KEY,
    name       VARCHAR(255)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    user_pic   VARCHAR(255),
    gender     VARCHAR(255) DEFAULT 'OTHER',
    locale     VARCHAR(255),
    role       VARCHAR(255)        NOT NULL,
    last_visit TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);