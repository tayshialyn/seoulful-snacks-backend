CREATE DATABASE  IF NOT EXISTS `snackdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `snackdb`;
-- MySQL dump 10.13  Distrib 8.0.29, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: snackdb
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` longtext NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `product_chk_1` CHECK ((`price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Gim (Roasted Seaweed Sheets), Korean Rice Crackers (Variety Pack), Korean Candies (Yuja, Green Tea Candy, Rice Candy Bar), Korean Soda (Melon or Strawberry)\n','/product_photos/3f502abd-c882-4696-a532-62a5bf55da30_korean_snack_box.png','Petite K-Treat Taster',11.00),(2,'Pre-made Kimchi Jeon (Kimchi Pancake), Korean Dried Fruits & Seaweed Snacks (Variety Pack), Korean Noodle Salad (DIY), Korean Yogurt Parfait (Fruits & Yogurt), Korean Milk (Banana, Melon, Strawberry)','/product_photos/4d0868e0-3c18-42a9-ba46-4fdfc1deb9a6_premium_snack_box.png','K-Treat Discovery Box\n',20.00),(3,'Korean Kimbap Bowls (Deconstructed, Smoked Salmon), Korean Fruit Salad (Premium Fruits), Korean Rice Crackers & Dips, (Gourmet Crackers, Kimchi Cream Cheese, Truffle Gochujang Mayo), Korean Banchan (Seasoned Seaweed Salad, Marinated Edamame, Spicy Cucumber Kimchi), Korean Tea (Ginseng, Oolong) or Makgeolli (Rice Wine)','/product_photos/7e388945-ea5b-41ed-9cd9-eec2e8182ee1_mystery_snack_box.png','K-Treat Treasure Chest',30.00);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expired_on` datetime(6) NOT NULL,
  `mailing_address` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `subscribed_on` datetime(6) NOT NULL,
  `product_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_on` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg40gg635cui0m07vh433dri4x` (`product_id`),
  KEY `FK8l1goo02px4ye49xd7wgogxg6` (`user_id`),
  CONSTRAINT `FK8l1goo02px4ye49xd7wgogxg6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKg40gg635cui0m07vh433dri4x` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (1,'2024-06-28 12:49:30.829125','\"10 Scotts Road\"',10,'2024-05-29 12:49:30.829125',1,1,'2024-05-29 12:49:30.838046'),(2,'2024-06-28 12:50:09.534125','\"10 Scotts Road\"',10,'2024-05-29 12:50:09.534125',1,2,'2024-05-29 12:50:09.535248');
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `billing_address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('USER','ADMIN') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'francis2@gmail.com',NULL,'$2a$10$9VmUPp3jqcDXw6AKec0TMeoLCW/43mDc9csI3U30EUNFNDYplqXN6','ADMIN'),(2,NULL,'francis@gmail.com',NULL,'$2a$10$JmCT9sx2aNxXFx9EruiMwO4xUcu2BcNwvzcA96ll9NB/jWiuZvc1m','USER'),(3,NULL,'tanna@gmail.com',NULL,'$2a$10$wDRlKxIBjQQ46Uw.J60Ho.EFI8ylxOd/r0KHpYgbjDq165440fgTW','ADMIN'),(5,NULL,'tanna2@gmail.com',NULL,'$2a$10$GkvHJXCMycNZjD6Ular8L.xTtrB8Z8QbZMAzxUqW8jzIduZ4wC.e.','ADMIN');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_subscription`
--

DROP TABLE IF EXISTS `user_subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_subscription` (
  `user_id` bigint NOT NULL,
  `subscription_id` bigint NOT NULL,
  UNIQUE KEY `UK_f0dcjs55auqkgcbex3am6f4b7` (`subscription_id`),
  KEY `FKpsiiu2nyr0cbxeluuouw474s9` (`user_id`),
  CONSTRAINT `FKhaqil7thjcrmntsjy8er8akjy` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`),
  CONSTRAINT `FKpsiiu2nyr0cbxeluuouw474s9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_subscription`
--

LOCK TABLES `user_subscription` WRITE;
/*!40000 ALTER TABLE `user_subscription` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_subscription` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-31 20:59:49
