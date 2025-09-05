
CREATE TABLE `users` (
    `username`      VARCHAR(64)     NOT NULL,
    `api_key`       VARCHAR(128)    UNIQUE NOT NULL,
    `role`          VARCHAR(8)      NOT NULL,
    `disabled`      BOOLEAN         DEFAULT FALSE,
    PRIMARY KEY (`username`)
);

INSERT INTO `users` (`username`, `api_key`, `role`, `disabled`)
VALUES
    ('alice@example.com','api-key-a','ADMIN', FALSE),
    ('bob@example.com','api-key-b','STAFF', FALSE),
    ('charly@example.com','api-key-c','USER', FALSE),
    ('daniel@example.com','api-key-d','USER', TRUE);


