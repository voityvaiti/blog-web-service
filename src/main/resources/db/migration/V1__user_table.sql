CREATE TABLE usr
(
    id       uuid DEFAULT gen_random_uuid(),
    username VARCHAR(20) NOT NULL UNIQUE,
    password CHAR(60)    NOT NULL,
    role     VARCHAR(20) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO usr (username, password, role, nickname)
VALUES ('admin', '$2a$10$8SnMIiHCu53DrUQVMRHgL.eUNQaVJ2cDn1jw3CkcKEjXQ3bj9nLOG', 'ADMIN', 'Admin_DEFAULT'),
       ('user', '$2a$10$cCbvLd.2Cb9tZ74mLnbV/uthvslLs4.eEOPQ0AU2MtKuW68/I5c5.', 'USER', 'User_DEFAULT');