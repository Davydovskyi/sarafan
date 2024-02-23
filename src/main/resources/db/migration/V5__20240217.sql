DROP TABLE IF EXISTS user_subscriptions;

CREATE TABLE user_subscriptions
(
    id              BIGSERIAL PRIMARY KEY,
    subscriber_id   varchar(255) NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    subscription_id varchar(255) NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (subscriber_id, subscription_id)
);