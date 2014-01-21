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

/*Data for the table `tb_folders` */

insert  into `tb_folders`(`folder_kd`,`folder_nm`) values ('delete','Удаленные'),('inbox','Входящие'),('outbox','Исходящие'),('sended','Отправленные');

/*Data for the table `tb_type_mess` */

insert  into `tb_type_mess`(`type_mess_kd`,`type_mess_nm`) values ('CHARGE','Запрос начислений'),('PAYMENT','Экспорт платежей'),('SENDPAY','Отправка платежа');

/*Data for the table `wp_group` */

insert  into `wp_group`(`group_id`,`group_nm`,`group_dsc`) values (1,'Администраторы',NULL),(2,'Пользователь',NULL);

/*Data for the table `wp_group_permision` */

insert  into `wp_group_permision`(`group_id`,`permission_id`) values (1,1),(1,2),(2,1);

/*Data for the table `wp_permission` */

insert  into `wp_permission`(`permission_id`,`permission_nm`,`permission_dsc`) values (1,'ROLE_USER','Дает право законектится'),(2,'ROLE_ADMIN','Дает право на администрирование пользователей');

/*Data for the table `wp_users_group` */

insert  into `wp_users_group`(`user_id`,`group_id`) values (1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
