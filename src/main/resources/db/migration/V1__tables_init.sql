CREATE TABLE post
(
    id                   uuid        DEFAULT gen_random_uuid(),
    title                VARCHAR(100),
    article              VARCHAR(1000),
    publication_datetime TIMESTAMPTZ DEFAULT NOW(),
    PRIMARY KEY (id)
)