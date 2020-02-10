/*
SQLyog Professional v12.09 (64 bit)
MySQL - 8.0.11 : Database - chatapp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`chatapp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `chatapp`;

/*Table structure for table `msg` */

DROP TABLE IF EXISTS `msg`;

CREATE TABLE `msg` (
  `content` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `fromUser` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `toUser` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `msg` */

insert  into `msg`(`content`,`time`,`fromUser`,`toUser`) values ('sadasdassadsadsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',1550047640918,'iu','erke'),('sadsadcxzcxzcxz',1550047640917,'erke','iu'),('dsdsfds',1550047640915,'iu','erke'),('asd',1550047640916,'iu','erke'),('你上不上班',1550048492914,'iu','erke'),('对对对',1550048784677,'iu','erke'),('就是的双手合十',1550049291122,'iu','erke'),('aaaaa撒大声地',1550048784678,'erke','iu'),('fdvfdbfgbfgbg示范点',1550048784679,'erke','iu'),('撒打算撒多撒多',1550048784680,'erke','iu'),('是的方法',1550051255502,'iu','erke'),('啊啊啊啊',1550051613349,'erke','iu'),('   你好好',1550051634728,'erke','iu'),('hello iu',1550051700932,'erke','iu'),('hello erke',1550051724710,'iu','erke'),('hello iu',1550051764999,'erke','iu'),('。',1550197560597,'iu','z'),('dsfdsf',1,'z','iu'),('asd',2,'z','iu'),('sadavcvcx',5,'iu','z'),('gfhgf',4,'z','iu'),('大家好啊',1550220177617,'iu','asd'),('大家好啊',1550220177616,'iu','big'),('大家好啊',1550220177616,'iu','big'),('大家好啊',1550220198628,'iu','az'),('大家好啊',1550220198628,'iu','asd'),('大家好啊',1550220198631,'iu','ffgg'),('大家好啊',1550220198629,'iu','big'),('大家好啊',1550220210266,'iu','big'),('大家好啊',1550220210266,'iu','az'),('大家好啊',1550220210270,'iu','ffgg'),('大家好啊',1550220210266,'iu','asd'),('大家好啊a',1550220220643,'iu','asd'),('大家好啊a',1550220220646,'iu','big'),('大家好啊a',1550220220647,'iu','ffgg'),('大家好啊a',1550220220643,'iu','az'),('大家好啊aezclap',1550220232279,'iu','asd'),('大家好啊aezclap',1550220232279,'iu','big'),('大家好啊aezclap',1550220232279,'iu','az'),('大家好啊aezclap',1550220232282,'iu','ffgg'),('合适的好的好的好的喝',1550221641386,'iu','ljr'),('合适的好的好的好的喝',1550221641386,'iu','fgh'),('合适的好的好的好的喝',1550221641375,'iu','l'),('你好吗ezez',1550221695553,'iu','asd'),('你好吗ezez',1550221695553,'iu','fgh'),('你好吗ezez',1550221695554,'iu','ljr'),('ezez',1550222016421,'iu','梁'),('ezez',1550222016420,'iu','asd'),('ezez',1550222016408,'iu','l'),('ezez',1550222016424,'iu','fgh'),('ezez',1550222016424,'iu','ljr'),('一定都不ez',1550222117644,'iu','l'),('一定都不ez',1550222117645,'iu','ljr'),('额三四十个三哥好坏',1550222893475,'iu','l'),('额三四十个三哥好坏',1550222893485,'iu','asd'),('额三四十个三哥好坏',1550222893484,'iu','ljr'),('滚滚滚一月份出发',1550223076743,'iu','ljr'),('滚滚滚一月份出发',1550223076746,'iu','asd'),('滚滚滚一月份出发',1550223076730,'iu','l'),('发发发查干湖胡u哈哈哈',1550223263866,'iu','l'),('发发发查干湖胡u哈哈哈',1550223263873,'iu','ljr'),('发发发查干湖胡u哈哈哈',1550223263876,'iu','fgh'),('发发发查干湖胡u哈哈哈',1550223263876,'iu','asd'),('啊啊啊啊',1550285158762,'erke','iu'),('就是的回到古代喝',1550285226025,'iu','erke'),('还是工作广深高速高速',1550285275344,'iu','erke'),('还是工作广深高速高速',1550285275344,'iu','iuu'),('sda',1,'bb','iu'),('sdac',2,'iu','bb'),('sadfvfv',3,'bb','iu'),('vfdv',4,'iu','bb'),('ds',5,'bb','iu'),('fgd',6,'iu','bb'),('dgfdgfd',7,'bb','iu'),('khjkhkh',8,'iu','bb'),('hgjghjgg',9,'iu','bb'),('dsfdfs',10,'iu','bb'),('saffff',11,'iu','bb');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `username` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`uid`,`username`,`password`) values ('2','asd','456123'),('90980','az','c3be117041a113540deb0ff532b19543'),(NULL,'bb','7694f4a66316e53c8cdd9d9954bd611d'),('82085','big','c3be117041a113540deb0ff532b19543'),(NULL,'bvg','e85dde330c34efb0e526ee3082e4353b'),('uid','dfc','c3be117041a113540deb0ff532b19543'),(NULL,'erke','79333f9a71582fdf7dba0f2bfeca60ff'),('68038','ffgg','b6b577fd12aa7e1df8d60735ef56fc2e'),('4','fgh','456789'),('45168','gshshw','d92f79bb36fe611e87e1ad21223360b3'),('48846','hahshshs','af123e4c5cb98906c30fff8c6e97aeb4'),('16074','hshsdg','f627ea0525d8551761866f27dbe1c6c0'),(NULL,'iu','ee3c00a88a0d2682354563e5cfdffc70'),('34992','iuu','e85dde330c34efb0e526ee3082e4353b'),('71199','l','60b725f10c9c85c70d97880dfe8191b3'),('1','ljr','123456'),('12506','ljra','c3be117041a113540deb0ff532b19543'),(NULL,'mj','c3be117041a113540deb0ff532b19543'),('90297','mm','c3be117041a113540deb0ff532b19543'),('3226','pp','c3be117041a113540deb0ff532b19543'),('98041','qa','c3be117041a113540deb0ff532b19543'),('58850','qwer','c3be117041a113540deb0ff532b19543'),('15221','qwe试试','60b725f10c9c85c70d97880dfe8191b3'),('39513','sd','c3be117041a113540deb0ff532b19543'),('67938','shdhhs就是就是手机','d983805527ca1a4da08eafc82365e305'),('2841','u','7f0c41872bf0f30883a0526141c683b1'),('3','wqe','789456'),('62347','z','60b725f10c9c85c70d97880dfe8191b3'),('uid','zsd','c3be117041a113540deb0ff532b19543'),('94343','zxcvb','3b5d5c3712955042212316173ccf37be'),('11454','多活动活动','dc829bf0d79e690c59cee708b527e6b7'),('85199','好多好多的浩浩荡荡好的哈','f0739e55418eb0a4843016d91bf2cbc3'),('96354','就是睡觉睡觉','60b725f10c9c85c70d97880dfe8191b3'),('69115','来啊','dc829bf0d79e690c59cee708b527e6b7'),('2048','梁','ddd5752b5fe66fd98fb9dcdee48b4473');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
