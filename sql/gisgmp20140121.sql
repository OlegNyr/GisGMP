/*
SQLyog Ultimate v9.50 
MySQL - 5.6.15 : Database - gisgmp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`gisgmp` /*!40100 DEFAULT CHARACTER SET cp1251 COLLATE cp1251_bin */;

USE `gisgmp`;

/*Table structure for table `tb_data` */

DROP TABLE IF EXISTS `tb_data`;

CREATE TABLE `tb_data` (
  `data_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mes_data` blob,
  `file_nm` varchar(100) COLLATE cp1251_bin DEFAULT NULL,
  PRIMARY KEY (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1754 DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `tb_folders` */

DROP TABLE IF EXISTS `tb_folders`;

CREATE TABLE `tb_folders` (
  `folder_kd` varchar(30) COLLATE cp1251_bin NOT NULL,
  `folder_nm` varchar(30) COLLATE cp1251_bin NOT NULL,
  PRIMARY KEY (`folder_kd`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin COMMENT='Папки';

/*Table structure for table `tb_log` */

DROP TABLE IF EXISTS `tb_log`;

CREATE TABLE `tb_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mes_id` bigint(20) DEFAULT NULL COMMENT 'Id сообщеня',
  `log_level` varchar(30) COLLATE cp1251_bin NOT NULL,
  `log_dt` datetime DEFAULT NULL,
  `log_msg` varchar(1024) COLLATE cp1251_bin DEFAULT NULL,
  `log_data` text COLLATE cp1251_bin,
  PRIMARY KEY (`log_id`),
  KEY `FK_Reference_6` (`mes_id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`mes_id`) REFERENCES `tb_messages` (`mes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `tb_mess_addinfo` */

DROP TABLE IF EXISTS `tb_mess_addinfo`;

CREATE TABLE `tb_mess_addinfo` (
  `mess_addinfo_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mes_id` bigint(20) DEFAULT NULL COMMENT 'Id сообщеня',
  `fld_nm` varchar(30) COLLATE cp1251_bin NOT NULL,
  `field_vl` varchar(1024) COLLATE cp1251_bin DEFAULT NULL,
  PRIMARY KEY (`mess_addinfo_id`),
  UNIQUE KEY `unq_addinf` (`mes_id`,`fld_nm`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`mes_id`) REFERENCES `tb_messages` (`mes_id`)
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `tb_messages` */

DROP TABLE IF EXISTS `tb_messages`;

CREATE TABLE `tb_messages` (
  `mes_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Id сообщеня',
  `folder_kd` varchar(30) COLLATE cp1251_bin DEFAULT NULL COMMENT 'Код папки',
  `data_id` bigint(20) DEFAULT NULL,
  `main_mess_id` bigint(20) DEFAULT NULL COMMENT 'Ссылка на головное сообщение',
  `source_data_id` bigint(20) DEFAULT NULL COMMENT 'Исходное сообщение',
  `version_nn` tinyint(4) DEFAULT NULL COMMENT 'Версия',
  `create_dt` datetime DEFAULT NULL COMMENT 'созданно',
  `post_block_id` varchar(50) COLLATE cp1251_bin NOT NULL COMMENT 'идентификатор запроса',
  `post_block_dt` datetime NOT NULL COMMENT 'Время формирования запроса',
  `SupplierBillID` varchar(100) COLLATE cp1251_bin NOT NULL COMMENT 'Уникальны_ идентификатор начисления',
  `SystemIdentifier` varchar(100) COLLATE cp1251_bin DEFAULT NULL COMMENT 'Уникальны_  идентификатор платежа',
  `PayerIdentifier` varchar(100) COLLATE cp1251_bin DEFAULT NULL COMMENT 'Уникальны_ идентификатор плательщика',
  `Bis_ref_nm` varchar(100) COLLATE cp1251_bin DEFAULT NULL COMMENT 'Уникальны_ номер для биса',
  `ChangeStatus` tinyint(4) NOT NULL,
  `type_mess_kd` varchar(30) COLLATE cp1251_bin DEFAULT NULL,
  PRIMARY KEY (`mes_id`),
  KEY `indx_mess_post_id` (`post_block_id`),
  KEY `indx_mess_mess_main_id` (`main_mess_id`),
  KEY `FK_Reference_1` (`folder_kd`),
  KEY `FK_Reference_2` (`data_id`),
  KEY `FK_Reference_8` (`source_data_id`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`folder_kd`) REFERENCES `tb_folders` (`folder_kd`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`data_id`) REFERENCES `tb_data` (`data_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`main_mess_id`) REFERENCES `tb_messages` (`mes_id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`source_data_id`) REFERENCES `tb_data` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=572 DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin COMMENT='Сообщения';

/*Table structure for table `tb_ticket` */

DROP TABLE IF EXISTS `tb_ticket`;

CREATE TABLE `tb_ticket` (
  `ticket_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_id` bigint(20) DEFAULT NULL,
  `mes_id` bigint(20) DEFAULT NULL COMMENT 'Id сообщеня',
  `ticket_dt` datetime DEFAULT NULL,
  `error_kd` varchar(1024) COLLATE cp1251_bin DEFAULT NULL COMMENT 'Код ошибки',
  `error_msg` varchar(1024) COLLATE cp1251_bin DEFAULT NULL COMMENT 'Сообщение об ошибке',
  PRIMARY KEY (`ticket_id`),
  KEY `FK_Reference_3` (`data_id`),
  KEY `FK_Reference_4` (`mes_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`mes_id`) REFERENCES `tb_messages` (`mes_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`data_id`) REFERENCES `tb_data` (`data_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=612 DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `tb_type_mess` */

DROP TABLE IF EXISTS `tb_type_mess`;

CREATE TABLE `tb_type_mess` (
  `type_mess_kd` varchar(90) COLLATE cp1251_bin DEFAULT NULL,
  `type_mess_nm` varchar(450) COLLATE cp1251_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `wp_group` */

DROP TABLE IF EXISTS `wp_group`;

CREATE TABLE `wp_group` (
  `group_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `group_nm` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  `group_dsc` varchar(250) COLLATE cp1251_bin DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `UNQ_GROUP` (`group_nm`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `wp_group_permision` */

DROP TABLE IF EXISTS `wp_group_permision`;

CREATE TABLE `wp_group_permision` (
  `group_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`group_id`,`permission_id`)
) ENGINE=MyISAM DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `wp_permission` */

DROP TABLE IF EXISTS `wp_permission`;

CREATE TABLE `wp_permission` (
  `permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_nm` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  `permission_dsc` varchar(250) COLLATE cp1251_bin DEFAULT NULL,
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `UNQ_PERMISSION` (`permission_nm`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `wp_persistent_logins` */

DROP TABLE IF EXISTS `wp_persistent_logins`;

CREATE TABLE `wp_persistent_logins` (
  `series_str` varchar(64) COLLATE cp1251_bin NOT NULL,
  `token_str` varchar(64) COLLATE cp1251_bin NOT NULL,
  `user_upper_nm` varchar(30) COLLATE cp1251_bin NOT NULL,
  `last_used` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`series_str`)
) ENGINE=MyISAM DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `wp_users` */

DROP TABLE IF EXISTS `wp_users`;

CREATE TABLE `wp_users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_nm` varchar(30) COLLATE cp1251_bin DEFAULT NULL,
  `user_first_nm` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  `user_next_nm` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  `user_family_nm` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  `email_nm` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  `password` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  `phone_nm` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  `user_enabled_pr` tinyint(1) DEFAULT '1',
  `user_upper_nm` varchar(30) COLLATE cp1251_bin DEFAULT NULL,
  `salt` varchar(50) COLLATE cp1251_bin DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UNQ_USERS_USER_UPPER_NM` (`user_upper_nm`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*Table structure for table `wp_users_group` */

DROP TABLE IF EXISTS `wp_users_group`;

CREATE TABLE `wp_users_group` (
  `user_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
