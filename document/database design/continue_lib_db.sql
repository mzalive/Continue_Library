-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: continue_lib_db
-- ------------------------------------------------------
-- Server version	5.6.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `book_Id` int(9) NOT NULL AUTO_INCREMENT,
  `book_Title` varchar(32) NOT NULL,
  `book_Subtitle` varchar(32) NOT NULL,
  `book_Isbn` bigint(20) NOT NULL,
  `book_Publisher` varchar(64) NOT NULL,
  `book_ImageUrl` varchar(128) NOT NULL,
  `book_Summary` text NOT NULL,
  `book_Amount_Total` int(2) NOT NULL,
  `book_PublishDate` date NOT NULL,
  PRIMARY KEY (`book_Id`),
  UNIQUE KEY `book_Id_UNIQUE` (`book_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(2,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(3,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(4,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(5,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(6,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(7,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(8,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(9,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01'),(10,'小工工','',9787505715660,'中国友谊出版公司','http://img1.douban.com/lpic/s1001902.jpg','小工工驾到！大家好，我是小工工，生活在故宫.',1,'2000-09-01');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_amount`
--

DROP TABLE IF EXISTS `book_amount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_amount` (
  `book_Amount_Id` int(9) NOT NULL AUTO_INCREMENT,
  `book_Id` int(9) NOT NULL,
  `book_Amount_Available` int(2) NOT NULL,
  PRIMARY KEY (`book_Amount_Id`),
  UNIQUE KEY `book_amount_id_UNIQUE` (`book_Amount_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_amount`
--

LOCK TABLES `book_amount` WRITE;
/*!40000 ALTER TABLE `book_amount` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_amount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_author`
--

DROP TABLE IF EXISTS `book_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book_author` (
  `book_Author_Id` int(9) NOT NULL AUTO_INCREMENT,
  `book_Id` int(9) NOT NULL,
  `author` varchar(64) NOT NULL,
  PRIMARY KEY (`book_Author_Id`),
  UNIQUE KEY `book_Author_Id_UNIQUE` (`book_Author_Id`),
  KEY `book_Id_idx` (`book_Id`),
  CONSTRAINT `book_Id` FOREIGN KEY (`book_Id`) REFERENCES `book` (`book_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_author`
--

LOCK TABLES `book_author` WRITE;
/*!40000 ALTER TABLE `book_author` DISABLE KEYS */;
INSERT INTO `book_author` VALUES (1,1,'叶虹江'),(2,2,'叶虹江'),(3,3,'叶虹江'),(4,4,'叶虹江'),(5,5,'叶虹江'),(6,6,'叶虹江'),(7,7,'叶虹江'),(8,8,'叶虹江'),(9,9,'叶虹江'),(10,10,'叶虹江');
/*!40000 ALTER TABLE `book_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowlist`
--

DROP TABLE IF EXISTS `borrowlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrowlist` (
  `borrow_Id` int(9) NOT NULL AUTO_INCREMENT,
  `book_Id` int(9) NOT NULL,
  `user_Id` int(9) NOT NULL,
  PRIMARY KEY (`borrow_Id`),
  UNIQUE KEY `borrow_Id_UNIQUE` (`borrow_Id`),
  KEY `book_Id_idx` (`book_Id`),
  KEY `FK_user_Id_idx` (`user_Id`),
  CONSTRAINT `FK_Borrow_Book_Id` FOREIGN KEY (`book_Id`) REFERENCES `book` (`book_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Borrow_User_Id` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowlist`
--

LOCK TABLES `borrowlist` WRITE;
/*!40000 ALTER TABLE `borrowlist` DISABLE KEYS */;
INSERT INTO `borrowlist` VALUES (1,1,1);
/*!40000 ALTER TABLE `borrowlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_book`
--

DROP TABLE IF EXISTS `tag_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_book` (
  `tag_Book_Id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `tag_Id` int(9) NOT NULL,
  `book_Id` int(9) NOT NULL,
  PRIMARY KEY (`tag_Book_Id`),
  UNIQUE KEY `tag_Id_UNIQUE` (`tag_Book_Id`),
  KEY `FK_book_Id_idx` (`book_Id`),
  KEY `FK_Tag_Book_Index_Id_idx` (`tag_Id`),
  CONSTRAINT `FK_Tag_Book_Book_Id` FOREIGN KEY (`book_Id`) REFERENCES `book` (`book_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Tag_Book_Index_Id` FOREIGN KEY (`tag_Id`) REFERENCES `tag_index` (`tag_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_book`
--

LOCK TABLES `tag_book` WRITE;
/*!40000 ALTER TABLE `tag_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_index`
--

DROP TABLE IF EXISTS `tag_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_index` (
  `tag_Id` int(9) NOT NULL AUTO_INCREMENT,
  `tag_Content` varchar(64) NOT NULL,
  PRIMARY KEY (`tag_Id`),
  UNIQUE KEY `tag_Id_UNIQUE` (`tag_Id`),
  KEY `FK_Index_Tag_Book_Id_idx` (`tag_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_index`
--

LOCK TABLES `tag_index` WRITE;
/*!40000 ALTER TABLE `tag_index` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_index` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_wish`
--

DROP TABLE IF EXISTS `tag_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_wish` (
  `tag_Wish_Id` int(9) NOT NULL AUTO_INCREMENT,
  `tag_Id` int(9) NOT NULL,
  `wish_Id` int(9) NOT NULL,
  PRIMARY KEY (`tag_Wish_Id`),
  UNIQUE KEY `tag_wish_UNIQUE` (`tag_Wish_Id`),
  KEY `FK_Tag_Wish_Wish_Id_idx` (`wish_Id`),
  KEY `FK_Tag_Wish_Index_Id_idx` (`tag_Id`),
  CONSTRAINT `FK_Tag_Wish_Index_Id` FOREIGN KEY (`tag_Id`) REFERENCES `tag_index` (`tag_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Tag_Wish_Wish_Id` FOREIGN KEY (`wish_Id`) REFERENCES `wish` (`wish_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_wish`
--

LOCK TABLES `tag_wish` WRITE;
/*!40000 ALTER TABLE `tag_wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_Id` int(9) NOT NULL AUTO_INCREMENT,
  `user_Name` varchar(64) NOT NULL,
  `user_Password` varchar(64) NOT NULL,
  `user_Avator` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`user_Id`),
  UNIQUE KEY `user_Id_UNIQUE` (`user_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'叶虹江','123456',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_borrowlist`
--

DROP TABLE IF EXISTS `user_borrowlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_borrowlist` (
  `uborrow_Id` int(9) NOT NULL AUTO_INCREMENT,
  `user_Id` int(9) NOT NULL,
  `book_Id` int(9) NOT NULL,
  PRIMARY KEY (`uborrow_Id`),
  UNIQUE KEY `uborrow_Id_UNIQUE` (`uborrow_Id`),
  KEY `FK_User_Borrowlist_User_Id_idx` (`user_Id`),
  KEY `FK_User_Book_Id_idx` (`book_Id`),
  CONSTRAINT `FK_User_Book_Id` FOREIGN KEY (`book_Id`) REFERENCES `book` (`book_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_User_Borrowlist_User_Id` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_borrowlist`
--

LOCK TABLES `user_borrowlist` WRITE;
/*!40000 ALTER TABLE `user_borrowlist` DISABLE KEYS */;
INSERT INTO `user_borrowlist` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10);
/*!40000 ALTER TABLE `user_borrowlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_wishlist`
--

DROP TABLE IF EXISTS `user_wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_wishlist` (
  `uwish_Id` int(9) NOT NULL AUTO_INCREMENT,
  `user_Id` int(9) NOT NULL,
  `wish_Id` int(9) NOT NULL,
  PRIMARY KEY (`uwish_Id`),
  UNIQUE KEY `uborrow_Id_UNIQUE` (`uwish_Id`),
  KEY `FK_User_Wishlist_User_Id_idx` (`user_Id`),
  KEY `FK_User_Wishlist_Wish_Id_idx` (`wish_Id`),
  CONSTRAINT `FK_User_Wishlist_User_Id` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_User_Wishlist_Wish_Id` FOREIGN KEY (`wish_Id`) REFERENCES `wish` (`wish_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_wishlist`
--

LOCK TABLES `user_wishlist` WRITE;
/*!40000 ALTER TABLE `user_wishlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_wishlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish`
--

DROP TABLE IF EXISTS `wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wish` (
  `wish_Id` int(9) NOT NULL AUTO_INCREMENT,
  `wish_Title` varchar(32) NOT NULL,
  `wish_Subtitle` varchar(32) NOT NULL,
  `wish_Isbn` bigint(20) NOT NULL,
  `wish_Publisher` varchar(64) NOT NULL,
  `wish_PublishDate` date NOT NULL,
  `wish_ImageUrl` varchar(128) NOT NULL,
  `wish_Summary` text NOT NULL,
  PRIMARY KEY (`wish_Id`),
  UNIQUE KEY `wish_Id_UNIQUE` (`wish_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish`
--

LOCK TABLES `wish` WRITE;
/*!40000 ALTER TABLE `wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish_author`
--

DROP TABLE IF EXISTS `wish_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wish_author` (
  `wish_Author_Id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `wish_Id` int(9) NOT NULL,
  `author` varchar(64) NOT NULL,
  PRIMARY KEY (`wish_Author_Id`),
  KEY `wish_Id_idx` (`wish_Id`),
  CONSTRAINT `wish_Id` FOREIGN KEY (`wish_Id`) REFERENCES `wish` (`wish_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish_author`
--

LOCK TABLES `wish_author` WRITE;
/*!40000 ALTER TABLE `wish_author` DISABLE KEYS */;
/*!40000 ALTER TABLE `wish_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish_heat`
--

DROP TABLE IF EXISTS `wish_heat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wish_heat` (
  `wish_Heat_Id` int(9) NOT NULL AUTO_INCREMENT,
  `wish_Id` int(9) NOT NULL,
  `wish_Heat` int(6) NOT NULL,
  PRIMARY KEY (`wish_Heat_Id`),
  UNIQUE KEY `wish_Heat_Id_UNIQUE` (`wish_Heat_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish_heat`
--

LOCK TABLES `wish_heat` WRITE;
/*!40000 ALTER TABLE `wish_heat` DISABLE KEYS */;
/*!40000 ALTER TABLE `wish_heat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-27 11:34:21
