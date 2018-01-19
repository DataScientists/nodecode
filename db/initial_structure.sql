CREATE DATABASE `nodecode` /*!40100 DEFAULT CHARACTER SET latin1 */;

CREATE TABLE `additionalfield` (
  `idadditionalfield` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idadditionalfield`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `agentinfo` (
  `agent_discriminator` varchar(31) NOT NULL,
  `idAgent` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) DEFAULT NULL,
  `agentGroup_idAgent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idAgent`),
  KEY `FK_ht6dquacdf8c1xcah9fyja94u` (`agentGroup_idAgent`),
  CONSTRAINT `FK_ht6dquacdf8c1xcah9fyja94u` FOREIGN KEY (`agentGroup_idAgent`) REFERENCES `agentinfo` (`idAgent`)
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=latin1;

CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sso_id` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `state` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sso_id` (`sso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

CREATE TABLE `app_user_user_profile` (
  `user_id` bigint(20) NOT NULL,
  `user_profile_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`user_profile_id`),
  KEY `FK_USER_PROFILE` (`user_profile_id`),
  CONSTRAINT `FK_APP_USER` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK_USER_PROFILE` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `user_type` varchar(1024) DEFAULT NULL,
  `action` varchar(200) DEFAULT NULL,
  `method` varchar(200) DEFAULT NULL,
  `arguments` blob,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3629 DEFAULT CHARSET=latin1;

CREATE TABLE `node` (
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
  CONSTRAINT `FK_1ftn9ltvvjtvvdnswwoyxkj2b` FOREIGN KEY (`parent_idNode`) REFERENCES `node` (`idNode`)
) ENGINE=InnoDB AUTO_INCREMENT=54605 DEFAULT CHARSET=latin1;

CREATE TABLE `interview` (
  `idinterview` bigint(20) NOT NULL AUTO_INCREMENT,
  `module_idNode` bigint(20) DEFAULT NULL,
  `fragment_idNode` bigint(20) DEFAULT NULL,
  `referenceNumber` varchar(255) NOT NULL,
  `idParticipant` bigint(20) NOT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `assessedStatus` varchar(255) DEFAULT '',
  PRIMARY KEY (`idinterview`),
  KEY `FK_srh0vgdnt8f7vvdmj88uxafsi` (`module_idNode`),
  CONSTRAINT `FK_srh0vgdnt8f7vvdmj88uxafsi` FOREIGN KEY (`module_idNode`) REFERENCES `node` (`idNode`)
) ENGINE=InnoDB AUTO_INCREMENT=3908 DEFAULT CHARSET=latin1;

CREATE TABLE `interview_answer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idinterview` bigint(20) NOT NULL,
  `topNodeId` bigint(20) NOT NULL,
  `parentQuestionId` bigint(20) DEFAULT NULL,
  `answerId` bigint(20) DEFAULT NULL,
  `link` bigint(20) DEFAULT NULL,
  `name` varchar(2048) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `answerFreetext` varchar(2048) DEFAULT NULL,
  `isProcessed` tinyint(4) NOT NULL,
  `modCount` int(11) DEFAULT '1',
  `nodeClass` varchar(235) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `deleted` int(11) NOT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `interviewQuestionId` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177136 DEFAULT CHARSET=latin1;

CREATE TABLE `interview_autoassessedrules` (
  `idInterview_AutoAssessedRules` int(11) NOT NULL AUTO_INCREMENT,
  `idinterview` int(11) DEFAULT NULL,
  `idRule` int(11) DEFAULT NULL,
  PRIMARY KEY (`idInterview_AutoAssessedRules`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=latin1;

CREATE TABLE `interview_display` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idinterview` bigint(20) NOT NULL,
  `name` varchar(2048) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `topNodeId` bigint(20) NOT NULL,
  `parentModuleId` bigint(20) NOT NULL,
  `parentAnswerId` bigint(20) DEFAULT NULL,
  `link` bigint(20) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `nodeClass` varchar(255) DEFAULT NULL,
  `sequence` int(11) NOT NULL,
  `header` varchar(50) DEFAULT NULL,
  `deleted` int(11) NOT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `number` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=447 DEFAULT CHARSET=latin1;

CREATE TABLE `interview_displayanswer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `interviewDisplayId` bigint(20) NOT NULL,
  `answerId` bigint(20) NOT NULL,
  `name` varchar(2048) DEFAULT NULL,
  `answerFreetext` varchar(2048) DEFAULT NULL,
  `nodeClass` varchar(235) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `deleted` int(11) NOT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=389 DEFAULT CHARSET=latin1;

CREATE TABLE `interview_firedrules` (
  `idInterview_FiredRules` int(11) NOT NULL AUTO_INCREMENT,
  `idinterview` int(11) DEFAULT NULL,
  `idRule` int(11) DEFAULT NULL,
  PRIMARY KEY (`idInterview_FiredRules`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=latin1;

CREATE TABLE `interview_manualassessedrules` (
  `idInterview_ManualAssessedRules` int(11) NOT NULL AUTO_INCREMENT,
  `idinterview` int(11) DEFAULT NULL,
  `idRule` int(11) DEFAULT NULL,
  PRIMARY KEY (`idInterview_ManualAssessedRules`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `interview_module` (
  `idinterview` bigint(20) NOT NULL,
  `idNode` bigint(20) NOT NULL,
  `topNodeId` bigint(20) NOT NULL,
  `answerNode` bigint(20) NOT NULL,
  `parentNode` bigint(20) NOT NULL,
  `parentAnswerId` bigint(20) DEFAULT NULL,
  `name` varchar(2048) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `deleted` int(11) NOT NULL,
  `linkNum` varchar(255) DEFAULT NULL,
  `count` int(11) NOT NULL,
  `sequence` int(11) NOT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idinterview`,`idNode`,`count`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `interview_question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idinterview` bigint(20) NOT NULL,
  `topNodeId` bigint(20) NOT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `parentModuleId` bigint(20) NOT NULL,
  `parentAnswerId` bigint(20) DEFAULT NULL,
  `modCount` int(11) NOT NULL DEFAULT '0',
  `link` bigint(20) DEFAULT NULL,
  `name` varchar(2048) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `nodeClass` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `intQuestionSequence` int(11) NOT NULL,
  `deleted` int(11) NOT NULL,
  `isProcessed` tinyint(4) NOT NULL DEFAULT '0',
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=215271 DEFAULT CHARSET=latin1;

CREATE TABLE `language` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(20) NOT NULL,
  `description` varchar(2048) NOT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `flag` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

CREATE TABLE `node_language` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `languageId` bigint(20) NOT NULL,
  `word` varchar(2048) DEFAULT NULL,
  `translation` varchar(2048) DEFAULT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_Language` (`languageId`),
  CONSTRAINT `FK_Language` FOREIGN KEY (`languageId`) REFERENCES `language` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=349 DEFAULT CHARSET=utf8;

CREATE TABLE `rule` (
  `idRule` bigint(20) NOT NULL AUTO_INCREMENT,
  `agentId` bigint(20) NOT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `legacyRuleId` bigint(20) DEFAULT NULL,
  `level` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idRule`)
) ENGINE=InnoDB AUTO_INCREMENT=14015 DEFAULT CHARSET=latin1;

CREATE TABLE `rule_additionalfield` (
  `idRuleAdditionalField` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  `idAdditionalField` bigint(20) DEFAULT NULL,
  `idRule` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idRuleAdditionalField`),
  KEY `FK_q3hwcs12of05uekcp2d25dx5d` (`idAdditionalField`),
  KEY `FK_d57n41rgqvuttgn8swte8ciu6` (`idRule`),
  CONSTRAINT `FK_d57n41rgqvuttgn8swte8ciu6` FOREIGN KEY (`idRule`) REFERENCES `rule` (`idRule`),
  CONSTRAINT `FK_q3hwcs12of05uekcp2d25dx5d` FOREIGN KEY (`idAdditionalField`) REFERENCES `additionalfield` (`idadditionalfield`)
) ENGINE=InnoDB AUTO_INCREMENT=5730 DEFAULT CHARSET=latin1;

CREATE TABLE `node_rule` (
  `idRule` bigint(20) NOT NULL,
  `idNode` bigint(20) NOT NULL,
  KEY `FK_2jovkmckdt8xpts3eh0sjfuma` (`idNode`),
  KEY `FK_jj080ddim7h6s3vsdsfy42np7` (`idRule`),
  CONSTRAINT `FK_2jovkmckdt8xpts3eh0sjfuma` FOREIGN KEY (`idNode`) REFERENCES `node` (`idNode`),
  CONSTRAINT `FK_jj080ddim7h6s3vsdsfy42np7` FOREIGN KEY (`idRule`) REFERENCES `rule` (`idRule`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `note` (
  `idNote` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` int(11) DEFAULT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `text` varchar(2048) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `node_idNode` bigint(20) DEFAULT NULL,
  `interviewId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idNote`),
  KEY `FK_kr3wlnbmt6jho41rbiv7hy693` (`node_idNode`),
  CONSTRAINT `FK_kr3wlnbmt6jho41rbiv7hy693` FOREIGN KEY (`node_idNode`) REFERENCES `node` (`idNode`)
) ENGINE=InnoDB AUTO_INCREMENT=3914 DEFAULT CHARSET=latin1;

CREATE TABLE `participant` (
  `idParticipant` int(11) NOT NULL AUTO_INCREMENT,
  `reference` varchar(20) NOT NULL,
  `status` int(11) NOT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idParticipant`)
) ENGINE=InnoDB AUTO_INCREMENT=3291 DEFAULT CHARSET=latin1;

CREATE TABLE `report_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `path` varchar(128) DEFAULT NULL,
  `status` varchar(128) NOT NULL,
  `progress` varchar(128) NOT NULL,
  `requestor` varchar(128) NOT NULL,
  `jsonData` longtext,
  `updatedDt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatedBy` varchar(128) DEFAULT NULL,
  `startDt` timestamp NULL DEFAULT NULL,
  `endDt` timestamp NULL DEFAULT NULL,
  `duration` float DEFAULT NULL,
  `recordCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `studyagents` (
  `idAgent` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `studynodes` (
  `node_discriminator` varchar(31) NOT NULL,
  `idNode` bigint(20) NOT NULL DEFAULT '0',
  `deleted` int(11) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `link` bigint(20) NOT NULL,
  `name` varchar(2048) DEFAULT NULL,
  `nodeclass` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `originalId` bigint(20) NOT NULL,
  `sequence` int(11) NOT NULL,
  `topNodeId` bigint(20) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `parent_idNode` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `value` varchar(128) DEFAULT NULL,
  `updatedDt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updatedBy` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

CREATE TABLE `translate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(20) NOT NULL,
  `description` varchar(2048) NOT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `flag` varchar(2048) DEFAULT NULL,
  `jdoc` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `interviewintromodule_module` AS select concat(`m`.`idNode`,':',`iq`.`id`) AS `primaryKey`,`m`.`idNode` AS `idModule`,`m`.`name` AS `introModuleNodeName`,`iq`.`id` AS `interviewPrimaryKey`,`iq`.`idinterview` AS `interviewId`,`iq`.`name` AS `interviewModuleName` from (`node` `m` join `interview_question` `iq`) where ((`m`.`idNode` = `iq`.`link`) and ((`iq`.`type` = 'Q_linkedmodule') or (`iq`.`type` = 'M_IntroModule')) and (`iq`.`deleted` = 0));
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `assessmentanswersummary` AS select concat(`p`.`idParticipant`,':',`p`.`reference`,':',`i`.`idinterview`,':',`ia`.`answerId`) AS `primaryKey`,`p`.`idParticipant` AS `idParticipant`,`p`.`reference` AS `reference`,`i`.`idinterview` AS `idinterview`,`ia`.`answerId` AS `answerId`,`ia`.`name` AS `name`,`ia`.`answerFreetext` AS `answerFreetext`,`ia`.`type` AS `type`,`p`.`status` AS `status`,`i`.`assessedStatus` AS `assessedStatus`,`im`.`interviewModuleName` AS `interviewModuleName` from (((`interview` `i` join `participant` `p`) join `interview_answer` `ia`) join `interviewintromodule_module` `im`) where ((`i`.`idinterview` = `ia`.`idinterview`) and (`i`.`idinterview` = `im`.`interviewId`) and (`i`.`idParticipant` = `p`.`idParticipant`) and (`p`.`deleted` = 0) and (`ia`.`deleted` = 0) and (`im`.`idModule` <> (select `sys_config`.`value` from `sys_config` where (`sys_config`.`name` = 'activeintro') limit 1)));
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `interviewmodule_fragment` AS select concat(`m`.`idNode`,':',`iq`.`id`) AS `primaryKey`,`m`.`idNode` AS `idFragment`,`m`.`name` AS `fragmentNodeName`,`iq`.`id` AS `interviewPrimaryKey`,`iq`.`idinterview` AS `interviewId`,`iq`.`name` AS `interviewFragmentName` from (`node` `m` join `interview_question` `iq`) where ((`m`.`idNode` = `iq`.`link`) and (`iq`.`type` = 'Q_linkedajsm') and (`iq`.`deleted` = 0));
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `module_fragment` AS select concat(`m`.`idNode`,':',`n`.`idNode`,':',`n`.`number`,':',`n`.`link`) AS `primaryKey`,`m`.`idNode` AS `moduleId`,`m`.`name` AS `moduleName`,`n`.`idNode` AS `idNode`,`n`.`name` AS `fragmentName`,`n`.`number` AS `nodeNumber`,`n`.`link` AS `fragmentId` from (`node` `m` join `node` `n` on((`n`.`topNodeId` = `m`.`idNode`))) where ((`n`.`type` = 'Q_linkedajsm') and (`n`.`deleted` = 0));
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `module_intromodule` AS select concat(`m`.`idNode`,':',`n`.`idNode`,':',`n`.`number`,':',`n`.`link`) AS `primaryKey`,`m`.`idNode` AS `moduleId`,`m`.`name` AS `moduleName`,`n`.`idNode` AS `idNode`,`n`.`name` AS `moduleLinkName`,`n`.`number` AS `nodeNumber`,`n`.`link` AS `moduleLinkId` from (`node` `m` join `node` `n` on((`n`.`topNodeId` = `m`.`idNode`))) where ((`n`.`type` = 'Q_linkedmodule') and (`n`.`deleted` = 0));
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `modulerule` AS select concat(`m`.`idNode`,':',`nr`.`idRule`,':',`r`.`agentId`,':',`n`.`idNode`) AS `primaryKey`,`m`.`idNode` AS `idModule`,`m`.`name` AS `moduleName`,`nr`.`idRule` AS `idRule`,`r`.`level` AS `ruleLevel`,`r`.`agentId` AS `idAgent`,`a`.`name` AS `agentName`,`n`.`idNode` AS `idNode`,`n`.`number` AS `nodeNumber` from ((((`node` `n` join `node` `m` on((`n`.`topNodeId` = `m`.`idNode`))) join `node_rule` `nr` on((`n`.`idNode` = `nr`.`idNode`))) join `rule` `r` on((`nr`.`idRule` = `r`.`idRule`))) join `agentinfo` `a` on((`r`.`agentId` = `a`.`idAgent`))) where ((`m`.`deleted` = 0) and (`n`.`deleted` = 0) and (`r`.`deleted` = 0));
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `nodenodelanguagefrag` AS select concat(`n`.`idNode`,':',`l`.`flag`) AS `primaryKey`,`n`.`idNode` AS `idNode`,`n`.`name` AS `name`,`n`.`topNodeId` AS `topNodeId`,`l`.`flag` AS `flag`,`l`.`description` AS `description`,`nl`.`languageId` AS `languageId`,(select count(distinct `nl1`.`word`) from (`node` `n1` left join `node_language` `nl1` on((convert(`n1`.`name` using utf8) = `nl1`.`word`))) where ((`n1`.`link` = 0) and (not((`n1`.`type` like '%frequency%'))) and (`n1`.`type` <> 'P_freetext') and (`nl1`.`translation` is not null) and (case `n`.`topNodeId` when 0 then (`n1`.`topNodeId` = `n`.`idNode`) else (`n1`.`topNodeId` = `n`.`topNodeId`) end) and (`nl1`.`languageId` = `nl`.`languageId`))) AS `current`,(select count(distinct `n1`.`name`) from `node` `n1` where ((`n1`.`link` = 0) and (not((`n1`.`type` like '%frequency%'))) and (`n1`.`type` <> 'P_freetext') and (`n1`.`deleted` = 0) and (case `n`.`topNodeId` when 0 then (`n1`.`topNodeId` = `n`.`idNode`) else (`n1`.`topNodeId` = `n`.`topNodeId`) end))) AS `total` from ((`node` `n` left join `node_language` `nl` on((convert(`n`.`name` using utf8) = `nl`.`word`))) left join `language` `l` on((`nl`.`languageId` = `l`.`id`))) where ((`n`.`link` = 0) and (not((`n`.`type` like '%frequency%'))) and (`n`.`type` <> 'P_freetext') and (`l`.`flag` is not null) and (`n`.`node_discriminator` = 'F')) group by `n`.`idNode`,`nl`.`languageId`,`l`.`flag`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `nodenodelanguagemod` AS select concat(`n`.`idNode`,':',`l`.`flag`) AS `primaryKey`,`n`.`idNode` AS `idNode`,`n`.`name` AS `name`,`n`.`topNodeId` AS `topNodeId`,`l`.`flag` AS `flag`,`l`.`description` AS `description`,`nl`.`languageId` AS `languageId`,(select count(distinct `nl1`.`word`) from (`node` `n1` left join `node_language` `nl1` on((convert(`n1`.`name` using utf8) = `nl1`.`word`))) where ((`n1`.`link` = 0) and (not((`n1`.`type` like '%frequency%'))) and (`n1`.`type` <> 'P_freetext') and (`nl1`.`translation` is not null) and (case `n`.`topNodeId` when 0 then (`n1`.`topNodeId` = `n`.`idNode`) else (`n1`.`topNodeId` = `n`.`topNodeId`) end) and (`nl1`.`languageId` = `nl`.`languageId`))) AS `current`,(select count(distinct `n1`.`name`) from `node` `n1` where ((`n1`.`link` = 0) and (not((`n1`.`type` like '%frequency%'))) and (`n1`.`type` <> 'P_freetext') and (`n1`.`deleted` = 0) and (case `n`.`topNodeId` when 0 then (`n1`.`topNodeId` = `n`.`idNode`) else (`n1`.`topNodeId` = `n`.`topNodeId`) end))) AS `total` from ((`node` `n` left join `node_language` `nl` on((convert(`n`.`name` using utf8) = `nl`.`word`))) left join `language` `l` on((`nl`.`languageId` = `l`.`id`))) where ((`n`.`link` = 0) and (not((`n`.`type` like '%frequency%'))) and (`n`.`type` <> 'P_freetext') and (`n`.`type` in ('M_Module','M_IntroModule')) and (`l`.`flag` is not null) and (`n`.`node_discriminator` = 'M')) group by `n`.`idNode`,`nl`.`languageId`,`l`.`flag`;




