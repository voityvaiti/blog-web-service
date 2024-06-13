CREATE TABLE avatars
(
    id           uuid DEFAULT gen_random_uuid(),
    user_id      uuid         NOT NULL UNIQUE,
    public_id    VARCHAR(255) NOT NULL,
    url          VARCHAR(255) NOT NULL,
    created_date TIMESTAMPTZ,
    updated_date TIMESTAMPTZ,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES usr (id) ON UPDATE CASCADE ON DELETE CASCADE
);