INSERT INTO `nodecode`.`app_user`
(
`id`,
`sso_id`,
`password`,
`first_name`,
`last_name`,
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

INSERT INTO `nodecode`.`user_profile`
(`id`,
`type`)
VALUES
(5,
'ADMIN');

INSERT INTO `nodecode`.`app_user_user_profile`
(`user_id`,
`user_profile_id`)
VALUES
(1,
5);


