CREATE TABLE post
(
    id                   uuid        DEFAULT gen_random_uuid(),
    user_id              uuid NOT NULL,
    title                VARCHAR(100),
    article              VARCHAR(1000),
    publication_datetime TIMESTAMPTZ DEFAULT NOW(),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES usr (id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO post (user_id, title, article)
VALUES ((SELECT id from usr WHERE username = 'user'), 'Post 1',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
       ((SELECT id from usr WHERE username = 'admin'), 'Post 2',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.'),
       ((SELECT id from usr WHERE username = 'user'), 'Post 3',
        'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
       ((SELECT id from usr WHERE username = 'user'), 'Post 4',
        'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
       ((SELECT id from usr WHERE username = 'admin'), 'Post 5',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'),
       ((SELECT id from usr WHERE username = 'user'), 'Post 6',
        'Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
       ((SELECT id from usr WHERE username = 'user'), 'Post 7',
        'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
       ((SELECT id from usr WHERE username = 'user'), 'Post 8',
        ' Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.');