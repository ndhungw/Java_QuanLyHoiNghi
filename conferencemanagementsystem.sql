-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: conferencemanagementsystem
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `displayName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` int NOT NULL DEFAULT '1',
  `blocked` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Nguyễn Đông Hưng','ndh1379','$2a$10$uonbyRQgtLOABsyGf62u1.Nb9G7RkrTrEfNHhWc7XUCQTkkNoaJRO',0,0),(2,'Bùi Đỗ Nam','buidohuy','$2a$10$0yQVopePm608QG4gt8t3KO6sQ9eT90BTcczS5HkqZTvXc2.FC6v0K',1,0),(3,'Thi Quốc Hùng','thiquochung','$2a$10$d54rzjzppK0Bak59l8Xlu.wUYBi9S0Lys2x1yCfnT/GIKHhh9MFsC',0,0),(4,'Kỳ Tuấn Khang','kytuankhang','$2a$10$ki1iDSQOhKXYllWUB7ApreO4ePsABgNSDs4sIvyOCD9iYIlcCW7tK',1,0),(5,'Nguyễn Quốc Khánh','ngquockhanh','$2a$10$kJPJwilit8lhiZ3T7hkDPuJ/CJQByS8it/hSKkqM70izGs83BJw76',1,0),(6,'Võ Thiện Tín','vothientin','$2a$10$3KvDB69JDw6aB7yutU4IGu6NMU3jeSaf82wtLK5oDtI8xf0xUaDMm',1,0),(7,'Trần Duy Quang','tranduyquang','$2a$10$hs8rmvKgs4PQe.R/X9oyJep2mjYvny.ySB5wRiYjrOC/R06RbP.sS',0,1),(8,'Ngô Nha Trang','ngonhatrang','$2a$10$f1PHB.DCsWfUaIXTtuuvcugWBT2vqXGz3e.7MogDtF8PfLI/dCGd2',1,0),(9,'Đặng Thị Thúy Uyên','thuyuyen','$2a$10$9b4OsJbS6ddfp7nHWSpUpeOKNbcG9nPJlcMIG2wPMGQiUomiBaW72',1,0),(10,'Trần Quang Huy','tranquanghuy','$2a$10$a/5c9DSNNhCXsNeLl4jOOu2C1l/FLFR7oSd5vZscRq48RZn1Hymj2',1,0),(11,'Đặng Minh Duy','minhduy','$2a$10$AdtuNDqfMfoi2uGSex3ZkepO7UPTnpL2z/U2LFi8Y509MEECB.PSq',1,0);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_conference`
--

DROP TABLE IF EXISTS `account_conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_conference` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_account` int NOT NULL,
  `id_conference` int NOT NULL,
  `status` int NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`),
  KEY `FK_accountconference_account_idx` (`id_account`),
  KEY `FK_accountconference_conference_idx` (`id_conference`),
  CONSTRAINT `FK_accountconference_account` FOREIGN KEY (`id_account`) REFERENCES `account` (`id`),
  CONSTRAINT `FK_accountconference_conference` FOREIGN KEY (`id_conference`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_conference`
--

LOCK TABLES `account_conference` WRITE;
/*!40000 ALTER TABLE `account_conference` DISABLE KEYS */;
INSERT INTO `account_conference` VALUES (1,3,4,1),(2,2,5,2);
/*!40000 ALTER TABLE `account_conference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference`
--

DROP TABLE IF EXISTS `conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conference` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `shortDescription` varchar(100) NOT NULL,
  `detailDescription` varchar(255) NOT NULL,
  `imgUrl` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `location` int NOT NULL,
  `numOfParticipants` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `FK_conference_location_idx` (`location`),
  CONSTRAINT `FK_conference_location` FOREIGN KEY (`location`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference`
--

LOCK TABLES `conference` WRITE;
/*!40000 ALTER TABLE `conference` DISABLE KEYS */;
INSERT INTO `conference` VALUES (2,'Hội nghị doanh nhân 1','Mô tả ngắn gọn của hội nghị doanh nhân 01','Chi tiết về hội nghị doanh nhân 01. Chi tiết về hội nghị doanh nhân 01. Chi tiết về hội nghị doanh nhân 01.','Images\\business-conference-01.jpg','2020-05-12 09:00:00',2,200),(3,'Hội nghị doanh nhân 02','Mô tả ngắn gọn của hội nghị doanh nhân 02','Chi tiết về hội nghị doanh nhân 02. Chi tiết về hội nghị doanh nhân 02. Chi tiết về hội nghị doanh nhân 02.','Images\\business-conference-02.jpg','2020-07-29 09:30:00',1,400),(4,'Hội nghị doanh nhân 03','Mô tả ngắn gọn của hội nghị doanh nhân 03','Chi tiết về hội nghị doanh nhân 03. Chi tiết về hội nghị doanh nhân 03. Chi tiết về hội nghị doanh nhân 03.','Images\\business-conference-03.jpg','2020-08-10 10:00:00',3,100),(5,'Hội nghị khoa học 01','Mô tả ngắn gọn của hội nghị khoa học 01','Chi tiết về hội nghị khoa học 01. Chi tiết về hội nghị khoa học 01. Chi tiết về hội nghị khoa học 01.','Images\\science-conference-01.jpg','2020-12-02 14:30:00',4,50),(6,'Hội nghị khoa học 02','Mô tả ngắn gọn của hội nghị khoa học 02','Chi tiết về hội nghị khoa học 02. Chi tiết về hội nghị khoa học 02. Chi tiết về hội nghị khoa học 02.','Images\\science-conference-02.jpg','2020-09-21 15:00:00',5,70);
/*!40000 ALTER TABLE `conference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `capacity` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Nhà Văn hóa Sinh viên quận Thủ Đức','123 Làng đại học',1000),(2,'Hội trường I, ĐH Khoa học Tự nhiên','227 Nguyễn Văn Cừ',300),(3,'Nhà hát lớn thành phố','72 Nguyễn Văn Linh, Phường 1, Quận 1, TP HCM',500),(4,'Hội trường B, ĐH KHTN CS2','12 Đường trục chính 1H, Đông Hòa, Dĩ An, Bình Dương',200),(5,'Hội trường G, Trung tâm tổ chức hội nghị TQS','33 Nguyễn Thái Học, Phường 5, Quận 5, TP HCM',100);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'conferencemanagementsystem'
--

--
-- Dumping routines for database 'conferencemanagementsystem'
--
/*!50003 DROP PROCEDURE IF EXISTS `getAccount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAccount`(IN username varchar(32), IN hashedPassword varchar(255) )
BEGIN
select *
from account
where (account.username = username) and (account.password = hashedPassword);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getHashedPassword` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getHashedPassword`(IN username varchar(32), OUT hashedPassword varchar(255))
BEGIN
select a.password INTO hashedPassword
from account a
where a.username = username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_checkIfConferenceOccurred` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_checkIfConferenceOccurred`(IN idConference INT, OUT count INT)
BEGIN
-- Occured: 1, Not occurred yet: 0
SELECT COUNT(*) INTO count
FROM conference c
WHERE (c.id = idConference) and (c.time < now());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_checkIfSubscribed` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_checkIfSubscribed`(IN idAccount INT, IN idConference INT, OUT count INT)
BEGIN
select COUNT(*) INTO count
from account_conference ac
where (ac.id_account = idAccount) and (ac.id_conference = idConference);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteRequestOfBlockedUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteRequestOfBlockedUser`(IN idAccount INT)
BEGIN
delete 
from account_conference ac
where ac.id_account = idAccount
and ac.id_conference IN (select c.id from conference c where c.time > now());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_updateConference` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_updateConference`(IN id INT, 
IN name varchar(100), IN shortDescription varchar(100), 
IN detailDescription varchar(255), IN imgUrl varchar(255), 
IN time timestamp, IN location varchar(255), IN capacity INT)
BEGIN
UPDATE conference c
SET c.name = name, c.shortDescription = shortDescription,
c.detailDescription = detailDescription, c.imgURL = imgUrl,
c.time = time, c.location = location, c.capacity = capacity
WHERE c.id = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-31 22:04:45
