CREATE DATABASE  IF NOT EXISTS `ScrumTool_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ScrumTool_db`;
-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: ScrumTool_db
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

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
-- Table structure for table `Issue`
--

DROP TABLE IF EXISTS `Issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Issue` (
  `idIssue` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(500) DEFAULT NULL,
  `Task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`idIssue`),
  KEY `fk_Issue_Task1_idx` (`Task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Issue`
--

LOCK TABLES `Issue` WRITE;
/*!40000 ALTER TABLE `Issue` DISABLE KEYS */;
/*!40000 ALTER TABLE `Issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PBI`
--

DROP TABLE IF EXISTS `PBI`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PBI` (
  `idPBI` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `isEpic` tinyint(1) NOT NULL,
  `Project_id` int(11) NOT NULL,
  `Epic_id` int(11) DEFAULT NULL,
  `Sprint_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPBI`,`Project_id`),
  KEY `fk_PBI_Project1_idx` (`Project_id`),
  KEY `fk_PBI_PBI1_idx` (`Epic_id`),
  KEY `fk_PBI_Sprint1_idx` (`Sprint_id`),
  CONSTRAINT `fk_PBI_PBI1` FOREIGN KEY (`Epic_id`) REFERENCES `PBI` (`idPBI`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PBI_Project1` FOREIGN KEY (`Project_id`) REFERENCES `Project` (`idProject`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PBI_Sprint1` FOREIGN KEY (`Sprint_id`) REFERENCES `Sprint` (`idSprint`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PBI`
--

LOCK TABLES `PBI` WRITE;
/*!40000 ALTER TABLE `PBI` DISABLE KEYS */;
INSERT INTO `PBI` VALUES (1,'Communicate with Donald','Constantly inform him about statistics from stolen data',2,1,3,NULL,1),(2,'Stop Telegram Uprise','Make Telegram programmers life difficult to work',1,1,3,NULL,NULL),(3,'Telegram & ISIS','Blame Telegram for permitting Isis soldiers to use their app',3,0,3,2,NULL),(4,'Bad Telegram Reputation','Pay websites to write negative reviews for our enemy',2,0,3,2,NULL);
/*!40000 ALTER TABLE `PBI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `idProject` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `isDone` tinyint(1) NOT NULL,
  `deadlineDate` date DEFAULT NULL,
  PRIMARY KEY (`idProject`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES (1,'Twitter',0,'2019-11-17'),(2,'Scrum',0,'2018-07-15'),(3,'Facebook',0,'2018-09-16'),(4,'Trivaggos',1,'2017-09-30');
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project_User_has_Task`
--

DROP TABLE IF EXISTS `Project_User_has_Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project_User_has_Task` (
  `Project_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  `Task_id` int(11) NOT NULL,
  PRIMARY KEY (`Project_id`,`User_id`,`Task_id`),
  KEY `fk_Project_has_User_has_Task_Project_has_User1_idx` (`Project_id`,`User_id`),
  KEY `fk_Project_has_User_has_Task_Task1_idx` (`Task_id`),
  CONSTRAINT `fk_Project_has_User_has_Task_Project_has_User1` FOREIGN KEY (`Project_id`, `User_id`) REFERENCES `Project_has_User` (`Project_id`, `User_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_has_Task_Task1` FOREIGN KEY (`Task_id`) REFERENCES `Task` (`idTask`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project_User_has_Task`
--

LOCK TABLES `Project_User_has_Task` WRITE;
/*!40000 ALTER TABLE `Project_User_has_Task` DISABLE KEYS */;
/*!40000 ALTER TABLE `Project_User_has_Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project_has_User`
--

DROP TABLE IF EXISTS `Project_has_User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project_has_User` (
  `Project_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`Project_id`,`User_id`),
  KEY `fk_Project_has_User_User1_idx` (`User_id`),
  KEY `fk_Project_has_User_Project_idx` (`Project_id`),
  CONSTRAINT `fk_Project_has_User_Project` FOREIGN KEY (`Project_id`) REFERENCES `Project` (`idProject`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_User1` FOREIGN KEY (`User_id`) REFERENCES `User` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project_has_User`
--

LOCK TABLES `Project_has_User` WRITE;
/*!40000 ALTER TABLE `Project_has_User` DISABLE KEYS */;
INSERT INTO `Project_has_User` VALUES (1,2,'Developer'),(1,3,'Developer'),(2,1,'Developer'),(2,2,'Developer'),(2,3,'Developer'),(3,2,'Developer'),(3,3,'Developer'),(4,1,'Product Owner'),(4,2,'Scrum Master');
/*!40000 ALTER TABLE `Project_has_User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sprint`
--

DROP TABLE IF EXISTS `Sprint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sprint` (
  `idSprint` int(11) NOT NULL AUTO_INCREMENT,
  `deadlineDate` date NOT NULL,
  `goal` varchar(200) DEFAULT NULL,
  `plan` varchar(500) DEFAULT NULL,
  `isCurrent` tinyint(1) NOT NULL,
  `numSprint` int(11) DEFAULT NULL,
  `Project_id` int(11) NOT NULL,
  PRIMARY KEY (`idSprint`,`Project_id`),
  KEY `fk_Sprint_Project1_idx` (`Project_id`),
  CONSTRAINT `fk_Sprint_Project1` FOREIGN KEY (`Project_id`) REFERENCES `Project` (`idProject`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sprint`
--

LOCK TABLES `Sprint` WRITE;
/*!40000 ALTER TABLE `Sprint` DISABLE KEYS */;
INSERT INTO `Sprint` VALUES (1,'2018-02-10','Help Trump become president','Steal Data From Users',0,1,3),(2,'2018-08-08','Recreate Messenger','Copy awesome Telegram',1,2,3),(3,'2018-11-22','Make it more popular','Hack Facebook',1,1,1);
/*!40000 ALTER TABLE `Sprint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Task`
--

DROP TABLE IF EXISTS `Task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Task` (
  `idTask` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(500) NOT NULL,
  `state` int(11) NOT NULL COMMENT '0 is todo\n1 is doing\n2 is done.',
  `PBI_id` int(11) NOT NULL,
  PRIMARY KEY (`idTask`,`PBI_id`),
  KEY `fk_Task_PBI1_idx` (`PBI_id`),
  CONSTRAINT `fk_Task_PBI1` FOREIGN KEY (`PBI_id`) REFERENCES `PBI` (`idPBI`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Task`
--

LOCK TABLES `Task` WRITE;
/*!40000 ALTER TABLE `Task` DISABLE KEYS */;
/*!40000 ALTER TABLE `Task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `password` varchar(300) NOT NULL,
  `country` varchar(45) DEFAULT NULL,
  `job` varchar(45) DEFAULT NULL,
  `company` varchar(45) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `bio` varchar(1000) DEFAULT NULL,
  `photo` varchar(200) DEFAULT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `numProjects` int(11) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `mail_UNIQUE` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'orestis@gmail.com','Orestis','Garmpis','$2a$10$2VqiIm8sO1CvA65JXO9O2OWyXtwJK1Cbzj/CfiE6VmOyor.AKWcAu',NULL,NULL,NULL,NULL,NULL,NULL,0,2),(2,'kostas@gmail.com','Kostas','Kotronis','$2a$10$xgAzw4vx19hQxuw7jjFyku/GCT8izH27zKIdjnVZJhZg4lCh2EH1m',NULL,NULL,NULL,NULL,NULL,NULL,0,4),(3,'markos@gmail.com','Markos','Varvagiannis','$2a$10$FFZIXZWNB5chE3QbidHfXe/W1apJon4lMLSp.zh1YEIg5e6mpBfmC',NULL,NULL,NULL,NULL,NULL,NULL,0,3),(4,'dimitra@gmail.com','Dimitra','Mavroforaki','$2a$10$qQ41sciUTZaEVC1eppy32e2IswHg2013Hb3SKibbw74FrEnLRp2UC',NULL,NULL,NULL,NULL,NULL,NULL,0,0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-31  5:01:15
