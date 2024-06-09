CREATE DATABASE  IF NOT EXISTS `clinic` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `clinic`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: clinic
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `Appointment_id` int NOT NULL,
  `A_Date` date DEFAULT NULL,
  `A_time` time DEFAULT NULL,
  `P_id` int DEFAULT NULL,
  `R_id` int DEFAULT NULL,
  `D_id` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`Appointment_id`),
  KEY `P_id` (`P_id`),
  KEY `D_id` (`D_id`),
  KEY `R_id` (`R_id`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`P_id`) REFERENCES `patients` (`P_id`),
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`D_id`) REFERENCES `doctor` (`D_id`),
  CONSTRAINT `appointments_ibfk_3` FOREIGN KEY (`R_id`) REFERENCES `room` (`R_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (0,'2024-06-09','06:10:00',52,2,2,100),(1,'2024-06-10','03:00:00',52,2,2,1000);
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clinic`
--

DROP TABLE IF EXISTS `clinic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clinic` (
  `C_id` int NOT NULL,
  `C_name` varchar(32) NOT NULL,
  `C_phonenumber` varchar(12) DEFAULT NULL,
  `d_id` int DEFAULT NULL,
  PRIMARY KEY (`C_id`),
  KEY `d_id` (`d_id`),
  CONSTRAINT `clinic_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`D_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clinic`
--

LOCK TABLES `clinic` WRITE;
/*!40000 ALTER TABLE `clinic` DISABLE KEYS */;
INSERT INTO `clinic` VALUES (1,'Test','2420506',2);
/*!40000 ALTER TABLE `clinic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diagnosis`
--

DROP TABLE IF EXISTS `diagnosis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnosis` (
  `Did` int NOT NULL,
  `Dtype` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`Did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnosis`
--

LOCK TABLES `diagnosis` WRITE;
/*!40000 ALTER TABLE `diagnosis` DISABLE KEYS */;
INSERT INTO `diagnosis` VALUES (1,'Teeth examination'),(2,'Root canal treatment'),(3,'Retreatment of root canal'),(4,'Composite filling of anDtypeterior or posterior tooth'),(5,'Composite filling of primary tooth'),(6,'Surgical extraction'),(7,'Extraction of permanent tooth'),(8,'Extraction of primary tooth'),(9,'Pulpotomy of permanent tooth'),(10,'Pulpotomy of primary tooth'),(11,'Pulpectomy of primary tooth'),(12,'Metal crown of primary tooth'),(13,'Space maintainer for missing primary tooth'),(14,'Scaling and polishing of teeth'),(15,'Bleaching of teeth (Home bleaching)'),(16,'Bleaching of teeth (Office bleaching)'),(17,'Porcelain crown of one or more tooth'),(18,'Zircon crown of one or more tooth'),(19,'Removable partial denture'),(20,'Upper or lower denture or both'),(21,'Implants for one or more tooth'),(22,'Orthodontic treatment of upper or lower jaw or both'),(23,'Night guard');
/*!40000 ALTER TABLE `diagnosis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diagnosis_patient`
--

DROP TABLE IF EXISTS `diagnosis_patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnosis_patient` (
  `Did` int NOT NULL,
  `P_id` int NOT NULL,
  PRIMARY KEY (`Did`,`P_id`),
  KEY `P_id` (`P_id`),
  CONSTRAINT `diagnosis_patient_ibfk_1` FOREIGN KEY (`P_id`) REFERENCES `patients` (`P_id`),
  CONSTRAINT `diagnosis_patient_ibfk_2` FOREIGN KEY (`Did`) REFERENCES `diagnosis` (`Did`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnosis_patient`
--

LOCK TABLES `diagnosis_patient` WRITE;
/*!40000 ALTER TABLE `diagnosis_patient` DISABLE KEYS */;
/*!40000 ALTER TABLE `diagnosis_patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `D_id` int NOT NULL,
  `D_name` varchar(32) NOT NULL,
  `D_speciality` varchar(32) DEFAULT NULL,
  `D_phonenumber` varchar(12) DEFAULT NULL,
  `C_id` int DEFAULT NULL,
  `d_active` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`D_id`),
  KEY `C_id` (`C_id`),
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`C_id`) REFERENCES `clinic` (`C_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (2,'Alaa ','Surgeon','0595237162',1,'Y'),(3,'Ahmad','Dental','0595874568',1,'N');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `financial`
--

DROP TABLE IF EXISTS `financial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `financial` (
  `F_id` int NOT NULL,
  `amount` int DEFAULT NULL,
  `balance` int DEFAULT NULL,
  `F_type` varchar(32) DEFAULT NULL,
  `P_id` int DEFAULT NULL,
  `f_date` date DEFAULT NULL,
  PRIMARY KEY (`F_id`),
  KEY `P_id` (`P_id`),
  CONSTRAINT `financial_ibfk_1` FOREIGN KEY (`P_id`) REFERENCES `patients` (`P_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `financial`
--

LOCK TABLES `financial` WRITE;
/*!40000 ALTER TABLE `financial` DISABLE KEYS */;
INSERT INTO `financial` VALUES (0,1000,0,'Cash',52,'2024-06-09');
/*!40000 ALTER TABLE `financial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laboratory`
--

DROP TABLE IF EXISTS `laboratory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laboratory` (
  `Lid` int NOT NULL,
  `Cid` int DEFAULT NULL,
  `Lname` varchar(255) DEFAULT NULL,
  `Lprice` decimal(10,2) DEFAULT NULL,
  `statusFlag` char(1) DEFAULT NULL,
  `product` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Lid`),
  KEY `Cid` (`Cid`),
  CONSTRAINT `laboratory_ibfk_1` FOREIGN KEY (`Cid`) REFERENCES `clinic` (`C_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laboratory`
--

LOCK TABLES `laboratory` WRITE;
/*!40000 ALTER TABLE `laboratory` DISABLE KEYS */;
INSERT INTO `laboratory` VALUES (0,1,'bbb',3.00,'B','asd'),(1,1,'bbb',3.00,'B','asd'),(2,1,'Super market',10.00,'B','Coca cola');
/*!40000 ALTER TABLE `laboratory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `P_name` varchar(32) NOT NULL,
  `P_id` int NOT NULL,
  `p_phonenumber` varchar(12) NOT NULL,
  `p_bdate` date NOT NULL,
  `p_age` int DEFAULT NULL,
  PRIMARY KEY (`P_id`),
  UNIQUE KEY `P_name` (`P_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES ('John Doe',1,'0591234567','1998-12-01',25),('Jane Smith',2,'0592345678','2008-01-08',16),('Michael Johnson',3,'0593456789','1955-05-04',69),('Emily Brown',4,'0594567890','2018-08-20',5),('Christopher Davis',5,'0595678901','1989-02-28',35),('Jessica Wilson',6,'0596789012','2011-11-25',12),('Matthew Taylor',7,'0597890123','1998-01-06',26),('Amanda Martinez',8,'0598901234','2004-05-19',19),('James Anderson',9,'0599012345','2005-11-08',18),('Olivia Thomas',10,'0590123456','1994-02-16',30),('Daniel Hernandez',11,'0591234567','2003-01-27',21),('Isabella Moore',12,'0592345678','2010-12-26',13),('David Wilson',13,'0593456789','1951-09-15',72),('Sophia Garcia',14,'0594567890','1991-07-29',32),('Logan Rodriguez',15,'0595678901','2008-09-29',15),('Emma Lopez',16,'0596789012','1975-01-02',49),('Ryan Lee',17,'0597890123','1970-10-16',53),('Mia Gonzalez',18,'0598901234','1978-12-07',45),('Jayden Perez',19,'0599012345','1960-04-17',64),('Ethan Wilson',20,'0590123456','1986-09-01',37),('Ava Rivera',21,'0591234567','1958-06-14',65),('Alexander Evans',22,'0592345678','1954-04-05',70),('Sophia Lopez',23,'0593456789','2017-12-11',6),('Benjamin Thomas',24,'0594567890','1988-12-08',35),('Mia Scott',25,'0595678901','2012-11-06',11),('Daniel Lewis',26,'0596789012','2003-06-09',20),('Emily Perez',27,'0597890123','1956-08-21',67),('Michael Lee',28,'0598901234','1966-11-17',57),('Olivia Brown',29,'0599012345','2014-06-21',9),('Ethan Taylor',30,'0590123456','2005-09-22',18),('Isabella Garcia',31,'0591234567','1963-03-19',61),('William Martinez',32,'0592345678','1992-11-20',31),('Amelia Hernandez',33,'0593456789','1980-10-18',43),('Sophia Smith',34,'0594567890','1975-04-30',49),('James Johnson',35,'0595678901','1984-03-27',40),('Oliver Williams',36,'0596789012','1973-03-17',51),('Emma Brown',37,'0597890123','1963-04-29',61),('William Jones',38,'0598901234','2018-12-29',5),('Emily Miller',39,'0599012345','1966-12-30',57),('Matthew Davis',40,'0590123456','1972-01-01',52),('Olivia Garcia',41,'0591234567','2009-01-06',15),('Jacob Rodriguez',42,'0592345678','1963-01-29',61),('Charlotte Martinez',43,'0593456789','1982-05-04',42),('Benjamin Hernandez',44,'0594567890','2000-06-15',23),('Sophia Lopezos',45,'0595678901','1961-04-06',63),('Lucas Young',46,'0596789012','1998-12-11',25),('Emma Adams',47,'0597890123','2016-12-06',7),('Logan Thompson',48,'0598901234','1993-10-28',30),('Ava Perez',49,'0599012345','1968-04-28',56),('William Wright',50,'0590123456','1982-02-22',42),('Mustafa Altaweel',52,'1221017','2004-07-29',19);
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `P_id` int NOT NULL,
  `L_id` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `p_date` date DEFAULT NULL,
  PRIMARY KEY (`P_id`),
  KEY `L_id` (`L_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (0,0,3,'2024-06-09'),(1,1,3,'2024-06-09'),(2,2,10,'2024-06-09');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `R_id` int NOT NULL,
  `d_id` int DEFAULT NULL,
  PRIMARY KEY (`R_id`),
  KEY `d_id` (`d_id`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`D_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,2),(2,2);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatmenthistory`
--

DROP TABLE IF EXISTS `treatmenthistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatmenthistory` (
  `P_id` int DEFAULT NULL,
  `Ttype` varchar(50) DEFAULT NULL,
  `Tdate` date DEFAULT NULL,
  KEY `P_id` (`P_id`),
  CONSTRAINT `treatmenthistory_ibfk_1` FOREIGN KEY (`P_id`) REFERENCES `patients` (`P_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatmenthistory`
--

LOCK TABLES `treatmenthistory` WRITE;
/*!40000 ALTER TABLE `treatmenthistory` DISABLE KEYS */;
INSERT INTO `treatmenthistory` VALUES (52,'Teeth examination','2024-06-09'),(52,'Root canal treatment','2024-06-10');
/*!40000 ALTER TABLE `treatmenthistory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-09 19:35:58
