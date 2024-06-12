CREATE TABLE images
(
    id   UUID DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    url  VARCHAR(255),
    PRIMARY KEY (id)
);