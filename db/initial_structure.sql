
CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ssoId` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `state` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sso_id` (`ssoId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;


CREATE TABLE `Roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

CREATE TABLE `UserRole` (
  `user_id` bigint(20) NOT NULL,
  `user_profile_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`user_profile_id`),
  KEY `FK_USER_ROLE` (`user_profile_id`),
  CONSTRAINT `FK_APP_USER` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_USER_PROFILE` FOREIGN KEY (`user_profile_id`) REFERENCES `Roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `Node` (
  `node_discriminator` varchar(31) NOT NULL,
  `idNode` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` int(11) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `link` bigint(20) NOT NULL,
  `name` varchar(2048) DEFAULT NULL,
  `nodeclass` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `originalId` bigint(20) NOT NULL,
  `sequence` int(11) NOT NULL,
  `topNodeId` bigint(20) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `parent_idNode` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idNode`),
  KEY `FK_1ftn9ltvvjtvvdnswwoyxkj2b` (`parent_idNode`),
  CONSTRAINT `FK_1ftn9ltvvjtvvdnswwoyxkj2b` FOREIGN KEY (`parent_idNode`) REFERENCES `Node` (`idNode`)
) ENGINE=InnoDB AUTO_INCREMENT=54605 DEFAULT CHARSET=latin1;

CREATE TABLE `Note` (
  `idNote` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` int(11) DEFAULT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `text` varchar(2048) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `node_idNode` bigint(20) DEFAULT NULL,
  `interviewId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idNote`),
  KEY `FK_kr3wlnbmt6jho41rbiv7hy693` (`node_idNode`),
  CONSTRAINT `FK_kr3wlnbmt6jho41rbiv7hy693` FOREIGN KEY (`node_idNode`) REFERENCES `Node` (`idNode`)
) ENGINE=InnoDB AUTO_INCREMENT=3914 DEFAULT CHARSET=latin1;

CREATE TABLE `SystemConfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `value` varchar(128) DEFAULT NULL,
  `updatedDt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatedBy` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

CREATE TABLE `AuditLog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `user_type` varchar(1024) DEFAULT NULL,
  `action` varchar(200) DEFAULT NULL,
  `method` varchar(200) DEFAULT NULL,
  `arguments` blob,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3629 DEFAULT CHARSET=latin1;




