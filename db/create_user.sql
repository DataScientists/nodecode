INSERT INTO `User`
(
`userName`,
`password`,
`firstName`,
`lastName`,
`email`,
`state`)
VALUES
(
'admin',
'$2a$10$A3xIwkcNXV27bS2h1Ha7/uxLbvMLLk74k1JrkhhmZmD92P1O0Idz2',
'admin',
'P@ssw0rd',
'some@email.com',
'Active');

INSERT INTO `Role`
(
`name`)
VALUES
(
'ADMIN');

INSERT INTO `User_Role`
(`users_id`,
`roles_id`)
VALUES
(1,
1);


