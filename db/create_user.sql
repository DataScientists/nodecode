INSERT INTO `nodecode`.`user`
(
`id`,
`ssoId`,
`password`,
`firstName`,
`lastName`,
`email`,
`state`)
VALUES
(1,
'admin',
'$2a$10$8.YE0MMFjQE3PGOBaPl2JutKIhsMfL4l69I7aMngdKqUZgGsFEKha',
'admin',
'admin',
'int@yahoo.com',
'Active');

INSERT INTO `nodecode`.`roles`
(`id`,
`type`)
VALUES
(5,
'ADMIN');

INSERT INTO `nodecode`.`userrole`
(`user_id`,
`user_profile_id`)
VALUES
(1,
5);


