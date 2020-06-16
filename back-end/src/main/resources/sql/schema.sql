CREATE DATABASE  IF NOT EXISTS `scrumtool` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `scrumtool`;
-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: localhost    Database: scrumtool
-- ------------------------------------------------------
-- Server version	5.7.30-0ubuntu0.18.04.1

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
  `state` int(11) DEFAULT NULL,
  `Task_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`idIssue`),
  KEY `fk_Issue_Task1_idx` (`Task_id`),
  CONSTRAINT `fk_Issue_Task` FOREIGN KEY (`Task_id`) REFERENCES `Task` (`idTask`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Notification`
--

DROP TABLE IF EXISTS `Notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Notification` (
  `idNotification` int(11) NOT NULL AUTO_INCREMENT,
  `Project_id` int(11) DEFAULT NULL,
  `projectTitle` varchar(45) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `FromUsername` varchar(45) DEFAULT NULL,
  `ToUserEmail` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `text` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`idNotification`),
  KEY `fk_Notifications_1_idx` (`FromUsername`),
  KEY `fk_Notifications_1_idx1` (`ToUserEmail`),
  KEY `idProject_idx` (`Project_id`),
  CONSTRAINT `idProject` FOREIGN KEY (`Project_id`) REFERENCES `Project` (`idProject`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mail` FOREIGN KEY (`ToUserEmail`) REFERENCES `User` (`mail`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `username` FOREIGN KEY (`FromUsername`) REFERENCES `User` (`username`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  CONSTRAINT `fk_PBI_PBI1` FOREIGN KEY (`Epic_id`) REFERENCES `PBI` (`idPBI`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_PBI_Project1` FOREIGN KEY (`Project_id`) REFERENCES `Project` (`idProject`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PBI_Sprint1` FOREIGN KEY (`Sprint_id`) REFERENCES `Sprint` (`idSprint`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
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
  UNIQUE KEY `mail_UNIQUE` (`mail`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-16  6:31:01
