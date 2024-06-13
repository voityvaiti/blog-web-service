CREATE TABLE images
(
    id   UUID DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    url  VARCHAR(255),
    created_date TIMESTAMPTZ,
    updated_date TIMESTAMPTZ,
    PRIMARY KEY (id)
);