
CREATE TABLE `users` (
    `username`      VARCHAR(64)     NOT NULL,
    `password`      VARCHAR(128)    NOT NULL,
    `role`          VARCHAR(8)      NOT NULL,
    `disabled`      BOOLEAN         DEFAULT FALSE,
    PRIMARY KEY (`username`)
);

INSERT INTO `users` (`username`, `password`, `role`, `disabled`)
VALUES
    ('alice@example.com','{bcrypt}$2a$10$vx/N6rEtLmjWi8uWrfFyhOI.m4yZhUcgKuSugT0mUWOGSdHubCF2a','ADMIN', FALSE),
    ('bob@example.com','{bcrypt}$2a$10$xB9uod1pcNSgPIC8POM/buX279w3VA2gJi50NwV9od6MI4sRAND/i','STAFF', FALSE),
    ('charly@example.com','{noop}password-c','USER', FALSE),
    ('daniel@example.com','{noop}password-d','USER', TRUE);


