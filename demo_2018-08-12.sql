# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.22)
# Database: demo
# Generation Time: 2018-08-12 13:12:19 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;

INSERT INTO `permission` (`id`, `name`, `url`)
VALUES
	(1,'topic:list','/topic/list'),
	(2,'user:list','/user/list'),
	(3,'user:info','/user/info');

/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `name`)
VALUES
	(1,'admin'),
	(2,'user');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_permissions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_permissions`;

CREATE TABLE `role_permissions` (
  `roles_id` int(11) NOT NULL,
  `permissions_id` int(11) NOT NULL,
  KEY `FKclluu29apreb6osx6ogt4qe16` (`permissions_id`),
  KEY `FKib3eov0359c5o9s0a913mtyvv` (`roles_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;

INSERT INTO `role_permissions` (`roles_id`, `permissions_id`)
VALUES
	(1,1),
	(1,2),
	(1,3),
	(2,1);

/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table topic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `in_time` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK38wu074adxipuj6a9ifd7jv5f` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;

INSERT INTO `topic` (`id`, `content`, `in_time`, `title`, `user_id`)
VALUES
	(1,'话题内容','2018-08-12 21:06:54','话题标题',1);

/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `in_time` datetime DEFAULT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `email`, `in_time`, `last_password_reset_date`, `password`, `username`)
VALUES
	(1,'admin@qq.com','2018-08-10 18:05:22','2018-08-10 18:05:22','$2a$10$65J8V.hAmcNAl/khTUX8lOgRV7FcI1YiZ.heVjClNVG1CWWbMJ0.O','admin'),
	(2,'user1@qq.com','2018-08-12 21:06:19','2018-08-12 21:06:19','$2a$10$ljzArC2DHhxIQmwzpXG8Y.oedjpSWaNDbiMhIHu9POg73ZuRcel0q','user1');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_roles`;

CREATE TABLE `user_roles` (
  `users_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
  KEY `FK7ecyobaa59vxkxckg6t355l86` (`users_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;

INSERT INTO `user_roles` (`users_id`, `roles_id`)
VALUES
	(1,1),
	(2,2);

/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
