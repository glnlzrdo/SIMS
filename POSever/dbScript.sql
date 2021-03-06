CREATE DATABASE  IF NOT EXISTS `sims` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sims`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: sims
-- ------------------------------------------------------
-- Server version	5.7.15-log

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `product_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_code` varchar(16) NOT NULL,
  `product_name` varchar(64) NOT NULL,
  `product_unit` varchar(16) NOT NULL,
  `product_description` varchar(64) NOT NULL,
  `product_purprice` double NOT NULL,
  `product_retprice` double NOT NULL,
  `product_quantity` double unsigned NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `idproduct_UNIQUE` (`product_id`),
  UNIQUE KEY `product_code_UNIQUE` (`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'100001','RIDGID HACKSAW BLADE','PC','1/2\"',65,83,480),(2,'200002','YALE DOOR LOCKSET','PC','HEAVY DUTY',33,63,25),(3,'1001','PLYWOOD RICHMONDE','PC','1/8\" X 4\' X 8\'',240,390,99),(4,'1002','PLYWOOD RICHMONDE','PC','1/4\" X 4\' X 8\'',330,480,122),(5,'1003','PLYWOOD RICHMONDE','PC','1/4\" X  4\' X 8\'',380,530,34),(6,'1004','PLYWOOD RICHMONDE','PC','10MM',640,790,46),(7,'1005','PLYWOOD RICHMONDE','PC','3/4\" X 4\' X 8\'',1000,1150,41),(8,'1006','PLYWOOD AGUSAN','PC','3/4\" X 4\' X 8\'',1060,1210,25),(9,'1007','PLYBOARD RICHMONDE','PC','3/4\" X 4\' X 8\'',900,1050,23),(10,'1008','PLYBOARD AGUSAN','PC','3/4\" X 4\' X 8\'',1000,1150,11),(11,'1009','MARINE PLY RICHMONDE','PC','1/4\" X 4\' X 8\'',420,570,20),(12,'1010','MARINE PLY RICHMONDE','PC','10MM X 4\' X 8\'',750,900,10),(13,'1011','MARINE PLY RICHMONDE','PC','3/4\" X 4\' X 8\'',1150,1300,10),(14,'2001','BOYSEN','GALON','ACRYLIC EMULSION',535,565,150),(15,'2002','BOYSEN','GALON','MASONRY PUTTY',255,285,31),(16,'2003','BOYSEN','GALON','PLEXIBOND',698,728,32),(17,'2004','BOYSEN','GALON','GLAZING PUTTY',522,552,28),(18,'2005','BOYSEN','GALON','RED OXIDE',355,385,24),(19,'2006','BOYSEN','GALON','LACQUER FLO',559,589,30),(20,'2007','BOYSEN','GALON','LACQUER THINNER',378,408,55),(21,'2008','BOYSEN','GALON','LACQUER PRIMER',595,625,20),(22,'2009','BOYSEN','GALON','AUTO LACQUER WHITE',778,808,21),(23,'2010','BOYSEN','QTS','LACQUER FLO',148,178,12),(24,'2011','BOYSEN','QTS','MASONRY PUTTY',71,101,12),(25,'2012','BOYSEN','QTS','GLAZING PUTTY',140,170,12),(26,'2013','BOYSEN','QTS','RED OXIDE',99,129,12),(27,'2014','BOYSEN PRIMER','PAIL','#710 GLOSS LATEX',2154,2184,5),(28,'2015','BOYSEN PRIMER','PAIL','#715 SEMIGLOSS LATEX',2154,2184,14),(29,'2016','BOYSEN PRIMER','PAIL','#701 FLAT LATEX',1822,1852,15),(30,'2017','BOYSEN PRIMER','GALON','GLOSS LATEX',541,571,8),(31,'2018','BOYSEN PRIMER','GALON','SEMIGLOSS LATEX',541,571,8),(32,'2019','BOYSEN PRIMER','GALON','FLAT LATEX',458,488,8),(33,'2020','BOYSEN PRIMER','GALON','#75 SILVER FINISH',550,580,4),(34,'2021','BOYSEN PRIMER','QTS','GLOSS LATEX',146,176,12),(35,'2022','BOYSEN PRIMER','QTS','SEMIGLOSS LATEX',146,176,12),(36,'2023','BOYSEN PRIMER','QTS','FLAT LATEX',125,155,12),(37,'2024','BOYSEN PRIMER','QTS','SILVER FINISH',148,178,12),(38,'2025','BOYSEN ROOFGUARD','GALON','BAGUIO GREEN',542,572,4),(39,'2026','BOYSEN ROOFGUARD','GALON','SPANISH RED',477,507,4),(40,'2027','BOYSEN FLATWALL ENAMEL','PAIL','WHITE',2261,2291,4),(41,'2028','BOYSEN FLATWALL ENAMEL','GALON','WHITE',55,85,8),(42,'2029','BOYSEN QUICK-DRY ENAMEL','PAIL','#600 WHITE',2261,2291,19),(43,'2030','BOYSEN QUICK-DRY ENAMEL','GALON','#600 WHITE',569,599,24),(44,'2031','BOYSEN QUICK-DRY ENAMEL','GALON','ROYAL BLUE',473,503,4),(45,'2032','BOYSEN QUICK-DRY ENAMEL','GALON','EMERALD GREEN',460,490,4),(46,'2033','BOYSEN QUICK-DRY ENAMEL','GALON','INTERNATIONAL RED',512,542,5),(47,'2034','BOYSEN QUICK-DRY ENAMEL','GALON','CHOCOLATE BROWN',402,432,4),(48,'2035','BOYSEN QUICK-DRY ENAMEL','GALON','BLACK',400,430,4),(49,'2036','BOYSEN QUICK-DRY ENAMEL','QTS','WHITE',152,182,12),(50,'2037','BOYSEN QUICK-DRY ENAMEL','QTS','ROYAL BLUE',130,160,12),(51,'2038','BOYSEN QUICK-DRY ENAMEL','QTS','EMERALD GREEN',125,155,13),(52,'2039','BOYSEN QUICK-DRY ENAMEL','QTS','INTERNATIONAL RED',138,168,12),(53,'2040','BOYSEN QUICK-DRY ENAMEL','QTS','CHOCOLATE BROWN',110,140,12),(54,'2041','BOYSEN QUICK-DRY ENAMEL','QTS','BLACK',110,140,12),(55,'2042','BOYSEN QUICK-DRY ENAMEL','1/4 LTR','WHITE',48,78,12),(56,'2043','BOYSEN QUICK-DRY ENAMEL','1/4 LTR','ROYAL BLUE',43,73,12),(57,'2044','BOYSEN QUICK-DRY ENAMEL','1/4 LTR','EMERALD GREEN',42,72,13),(58,'2045','BOYSEN QUICK-DRY ENAMEL','1/4 LTR','INTERNATIONAL RED',45,75,12),(59,'2046','BOYSEN QUICK-DRY ENAMEL','1/4 LTR','CHOCOLATE BROWN',38,68,12),(60,'2047','BOYSEN QUICK-DRY ENAMEL','1/4 LTR','BLACK',38,68,12),(61,'2048','BOYSEN OIL WOOD STAIN','GALON','WALNUT',380,410,4),(62,'2049','BOYSEN OIL WOOD STAIN','GALON','MAPLE',390,420,4),(63,'2050','BOYSEN OIL WOOD STAIN','GALON','MAHOGANY',390,420,4),(64,'2051','BOYSEN OIL WOOD STAIN','GALON','OAK',390,420,4),(65,'2052','BOYSEN OIL WOOD STAIN','GALON','DARK OAK',400,430,4),(66,'2053','BOYSEN OIL WOOD STAIN','QTS','WALNUT',105,135,12),(67,'2054','BOYSEN OIL WOOD STAIN','QTS','MAPLE',108,138,13),(68,'2055','BOYSEN OIL WOOD STAIN','QTS','MAHOGANY',108,138,12),(69,'2056','BOYSEN OIL WOOD STAIN','QTS','OAK',108,138,12),(70,'2057','BOYSEN OIL WOOD STAIN','QTS','DARK OAK',110,140,12),(71,'2058','BOYSEN LATEX COLOR','QTS','THALO BLUE',96,126,12),(72,'2059','BOYSEN LATEX COLOR','QTS','THALO GREEN',96,126,12),(73,'2060','BOYSEN LATEX COLOR','QTS','RAW SIENNA',96,126,12),(74,'2061','BOYSEN LATEX COLOR','QTS','TOLUIDINE RED',100,130,12),(75,'2062','BOYSEN LATEX COLOR','QTS','HANZA YELLOW',130,160,12),(76,'2063','BOYSEN LATEX COLOR','QTS','LAMPBLACK',78,108,12),(77,'2064','BOYSEN OIL TINTING COLOR','1/4 LTR','THALO GREEN',80,110,24),(78,'2065','BOYSEN OIL TINTING COLOR','1/4 LTR','THALO BLUE',80,110,24),(79,'2066','BOYSEN OIL TINTING COLOR','1/4 LTR','RAW SIENNA',51,81,34),(80,'2067','BOYSEN OIL TINTING COLOR','1/4 LTR','BULLETIN RED',86,116,24),(81,'2068','BOYSEN OIL TINTING COLOR','1/4 LTR','VENETIAN RED',60,90,24),(82,'2069','BOYSEN OIL TINTING COLOR','1/4 LTR','LAMPBLACK',51,81,24),(83,'2070','BOYSEN OIL TINTING COLOR','1/4 LTR','HANZA YELLOW',87,117,24),(84,'2071','BOYSEN ACRYCOLOR','1/4 LTR','THALO BLUE',33,63,24),(85,'2072','BOYSEN ACRYCOLOR','1/4 LTR','THALO GREEN',33,63,24),(86,'2073','BOYSEN ACRYCOLOR','1/4 LTR','RAW SIENNA',33,63,24),(87,'2074','BOYSEN ACRYCOLOR','1/4 LTR','T. RED',34,64,24),(88,'2075','BOYSEN ACRYCOLOR','1/4 LTR','HANZA YELLOW',40,70,24),(89,'2076','BOYSEN ACRYCOLOR','1/4 LTR','LAMPBLACK',28,58,24),(90,'2077','DAVIES SUN AND RAIN','GALON','601 SMOKEGREY',525,555,4),(91,'2078','DAVIES SUN AND RAIN','GALON','060 TINGE OF GREY',525,555,4),(92,'2079','DAVIES SUN AND RAIN','GALON','913 TASTY TAN',525,555,4),(93,'2080','DAVIES SUN AND RAIN','GALON','013 WHITEWASH',525,555,4),(94,'2081','DAVIES SUN AND RAIN','GALON','902 MOCHA BLANCH',525,555,4),(95,'2082','DAVIES SUN AND RAIN','GALON','318 TEMPTATION',585,615,4),(96,'2083','DAVIES SUN AND RAIN','GALON','418 HAPPY DAYS',525,555,4),(97,'2084','DAVIES SUN AND RAIN','GALON','504 JOLLY ORANGE',1475,1505,5),(98,'2085','DAVIES SUN AND RAIN','GALON','208 CALM SKY',525,555,4),(99,'2086','DAVIES SUN AND RAIN','GALON','214 LIGHT WISTERIA',525,555,4),(100,'3001','PAIL THINNER','PAIL','PAINT THINNER',780,810,5),(101,'3002','GALON THINNER','GALON','PAINT THINNER',205,235,24),(102,'3003','BOTTLE THINNER','BOTTLE','PAINT THINNER',24.6,54.6,48),(103,'3004','PAIL THINNER','PAIL','LACQUER THINNER',640,670,16),(104,'3005','GALON THINNER','GALON','LACQUER THINNER',170,200,6),(105,'3006','BOTTLE THINNER','BOTTLE','LACQUER THINNER',20.2,50.2,24),(106,'4001','#100 SAND PAPER','PC','NUGEN',6,15,100),(107,'4002','#120 SAND PAPER','PC','NUGEN',6,15,300),(108,'4003','#220 SAND PAPER','PC','NUGEN',6,15,100),(109,'4004','#320 SAND PAPER','PC','NUGEN',6,15,100),(110,'4005','#360 SAND PAPER','PC','NUGEN',6,15,100),(111,'4006','#400 SAND PAPER','PC','NUGEN',6,15,100),(112,'4007','\"1/2\"\" MASKING TAPE\"','PC','DECO',14,24,96),(113,'4008','\"3/4\"\" MASKING TAPE\"','PC','DECO',21,31,164),(114,'4009','\"1\"\" MASKING TAPE\"','PC','DECO',27,37,48),(115,'4010','\"2\"\" MASKING TAPE\"','PC','DECO',56,66,24),(116,'4011','\"1/2\"\" PAINT BRUSH\"','PC','DECO',10,20,12),(117,'4012','\"3/4\"\" PAINT BRUSH\"','PC','DECO',13.4,23.4,12),(118,'4013','\"1\"\" PAINT BRUSH\"','PC','DECO',16.7,26.7,1060),(119,'4014','\"1 1/2\"\" PAINT BRUSH\"','PC','DECO',24.2,34.2,60),(120,'4015','\"2\"\" PAINT BRUSH\"','PC','DECO',35.9,45.9,60),(121,'4016','\"3\"\" PAINT BRUSH\"','PC','DECO',45,55,84),(122,'4017','\"4\"\" PAINT BRUSH\"','PC','DECO',71.7,81.7,24),(123,'4018','\"4\"\" ROLLER\"','PC','BABY ROLLER FOAM',30,40,49),(124,'4019','\"4\"\" ROLLER\"','PC','BABY ROLLER COTTON',23,33,48),(125,'4020','\"7\"\" ROLLER\"','PC','ACRYLON W/HOLE',57,67,48),(126,'4021','\"9\"\" ROLLER\"','PC','ACRYLON W/HOLE',70,80,12),(127,'4022','\"7\"\" ROLLER\"','PC','DECO W/HOLE',35,45,24),(128,'4023','\"9\"\" ROLLER\"','PC','DECO W/HOLE',45,55,12),(129,'5001','F1 TILE GROUT','BAG','TMC',59,79,10),(130,'5002','F10 TILE GROUT','BAG','TMC',59,79,10),(131,'5003','F12 TILE GROUT','BAG','TMC',59,79,10),(132,'5004','F14 TILE GROUT','BAG','TMC',49,69,10),(133,'5005','F15 TILE GROUT','BAG','TMC',49,69,20),(134,'5006','F16 TILE GROUT','BAG','TMC',59,79,10),(135,'5007','F20 TILE GROUT','BAG','TMC',59,79,10),(136,'5008','F22 TILE GROUT','BAG','TMC',59,79,10),(137,'5009','F24 TILE GROUT','BAG','TMC',59,79,20),(138,'5010','P8 TILE GROUT','BAG','TMC',115,135,10),(139,'6001','\"1\"\" COMMON NAIL\"','KL','STEELWORLD',34,59,125),(140,'6002','\"1 1/4\"\" COMMON NAIL\"','KL','STEELWORLD',32,57,50),(141,'6003','\"1 1/2\"\" COMMON NAIL\"','KL','STEELWORLD',32,57,125),(142,'6004','2\" COMMON NAIL\"','KL','STEELWORLD',35,55,250),(143,'6005','\"2 1/2\"\" COMMON NAIL\"','KL','STEELWORLD',30,55,250),(144,'6006','3\" COMMON NAIL','KL','STEELWORLD',28,53,251.25),(145,'6007','\"4\"\" COMMON NAIL\"','KL','STEELWORLD',27.2,52.2,250),(146,'6008','\"5\"\" COMMON NAIL\"','KL','STEELWORLD',30,55,25),(147,'6009','\"1\"\" FINISHING NAIL\"','KL','STEELWORLD',36.4,61.4,75),(148,'6010','\"1 1/2\"\" FINISHING NAIL\"','KL','STEELWORLD',34.4,59.4,50),(149,'6011','\"2\"\" FINISHING NAIL\"','KL','STEELWORLD',32.4,57.4,50),(150,'6012','\"2 1/2\"\" FINISHING NAIL\"','KL','STEELWORLD',32.4,57.4,50),(151,'6013','\"3\"\" FINISHING NAIL\"','KL','STEELWORLD',30.4,55.4,50),(152,'6014','\"1\"\" CONCRETE NAIL\"','KL','STEELWORLD',60,85,125),(153,'6015','\"1 1/2\"\" CONCRETE NAIL\"','KL','STEELWORLD',60,85,50),(154,'6016','\"2\"\" CONCRETE NAIL\"','KL','STEELWORLD',60,85,125),(155,'6017','\"3\"\" CONCRETE NAIL\"','KL','STEELWORLD',60,85,125),(156,'6018','\"4\"\" CONCRETE NAIL\"','KL','STEELWORLD',60,85,125),(157,'6019','\"1 1/4\"\" HARDIFLEX NAIL\"','KL','STEELWORLD',60,85,25),(158,'6020','\"1\"\" FLAT HEAD NAIL\"','KL','STEELWORLD',60,85,25),(159,'6021','1 1/2\" FLAT HEAD NAIL','KL','STEELWORLD',60,85,25),(160,'6022','1 1/4\" STAPLE NAIL','KL','STEELWORLD',60,85,25),(161,'7001','2.8MM X 2\" X 2\" X 4\' X 8\' WIRE MESH','PC','STEELWORLD',280,330,20),(162,'7002','3.2MM X 2\" X 2\" X 4\' X 8\' WIRE MESH','PC','STEELWORLD',320,370,20),(163,'7003','3.5MM X 2\" X 2\" X 4\' X 8\' WIRE MESH','PC','STEELWORLD',400,450,20),(164,'7004','#16-35K','KL','STEELWORLD',37,87,312),(165,'7005','#18-35K','KL','STEELWORLD',42,92,295),(166,'7006','1.4 X 6M DEFORMED BAR','PC','STEELWORLD',42,92,100),(167,'7007','1.7 X 6M DEFORMED BAR','PC','STEELWORLD',51,101,100),(168,'7008','2.2 X 6M WHITE DEFORMED BAR','PC','STEELWORLD',66,116,100),(174,'3333','PIONEER','PC','ELASTOSEAL',35.5,65.5,80),(175,'3334','VULCASEAL','QTS','SEALANT',55.75,73.25,40),(177,'1836','JANET','TON','BEAUTY',100,150,1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_transaction`
--

DROP TABLE IF EXISTS `product_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_transaction` (
  `purchase_id` int(10) unsigned DEFAULT NULL,
  `product_id` int(10) unsigned NOT NULL,
  `sales_id` int(10) unsigned DEFAULT NULL,
  `quantity` double unsigned NOT NULL,
  KEY `product_id_idx` (`product_id`),
  KEY `sales_id_idx` (`sales_id`),
  KEY `purchase_id` (`purchase_id`),
  CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_id` FOREIGN KEY (`purchase_id`) REFERENCES `purchase` (`purchase_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sales_id` FOREIGN KEY (`sales_id`) REFERENCES `sales` (`sales_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_transaction`
--

LOCK TABLES `product_transaction` WRITE;
/*!40000 ALTER TABLE `product_transaction` DISABLE KEYS */;
INSERT INTO `product_transaction` VALUES (1,1,NULL,1),(NULL,1,1,1),(2,1,NULL,1),(NULL,3,2,1),(NULL,6,3,1),(3,144,NULL,1.25),(NULL,3,4,1),(4,4,NULL,1),(4,14,NULL,10);
/*!40000 ALTER TABLE `product_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase` (
  `purchase_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `purchase_date` datetime NOT NULL,
  `purchase_client_id` int(10) unsigned NOT NULL,
  `purchase_payment` double unsigned NOT NULL,
  PRIMARY KEY (`purchase_id`),
  UNIQUE KEY `purchase_id_UNIQUE` (`purchase_id`),
  KEY `purchase_client_id_idx` (`purchase_client_id`),
  CONSTRAINT `purchase_client_id` FOREIGN KEY (`purchase_client_id`) REFERENCES `purchase_client` (`purchase_client_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (1,'2017-04-24 01:19:05',1,65),(2,'2017-04-24 01:22:30',3,65),(3,'2017-04-24 01:42:18',3,35),(4,'2017-04-24 18:31:03',3,5680);
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_client`
--

DROP TABLE IF EXISTS `purchase_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_client` (
  `purchase_client_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `address` varchar(86) DEFAULT NULL,
  `telephone` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`purchase_client_id`),
  UNIQUE KEY `sales_client_id_UNIQUE` (`purchase_client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_client`
--

LOCK TABLES `purchase_client` WRITE;
/*!40000 ALTER TABLE `purchase_client` DISABLE KEYS */;
INSERT INTO `purchase_client` VALUES (1,'The Builder','Manila','+6322540056'),(2,'Public Constructor','Quezon City','+6326549870'),(3,'Cash',NULL,NULL);
/*!40000 ALTER TABLE `purchase_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales` (
  `sales_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sales_date` datetime NOT NULL,
  `sales_client_id` int(10) unsigned NOT NULL,
  `sales_payment` double unsigned NOT NULL,
  PRIMARY KEY (`sales_id`),
  UNIQUE KEY `purchase_id_UNIQUE` (`sales_id`),
  KEY `sales_client_id_idx` (`sales_client_id`),
  CONSTRAINT `sales_client_id` FOREIGN KEY (`sales_client_id`) REFERENCES `sales_client` (`sales_client_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (1,'2017-04-24 01:19:06',1,83),(2,'2017-04-24 01:22:48',1,390),(3,'2017-04-24 01:23:58',3,790),(4,'2017-04-24 11:45:56',1,390);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_client`
--

DROP TABLE IF EXISTS `sales_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sales_client` (
  `sales_client_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `address` varchar(86) DEFAULT NULL,
  `telephone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sales_client_id`),
  UNIQUE KEY `sales_client_id_UNIQUE` (`sales_client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_client`
--

LOCK TABLES `sales_client` WRITE;
/*!40000 ALTER TABLE `sales_client` DISABLE KEYS */;
INSERT INTO `sales_client` VALUES (1,'Cash',NULL,NULL),(2,'Geo','Outer Space','+246836948'),(3,'Walk-in',NULL,NULL);
/*!40000 ALTER TABLE `sales_client` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-28  9:32:26
