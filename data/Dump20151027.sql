-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: amazon
-- ------------------------------------------------------
-- Server version	5.6.26

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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `detail_url` varchar(255) DEFAULT NULL,
  `comment_url` varchar(255) DEFAULT NULL,
  `total_comment` int(11) DEFAULT NULL,
  `avg_star` float DEFAULT NULL,
  `star1` int(11) DEFAULT NULL,
  `star2` int(11) DEFAULT NULL,
  `star3` int(11) DEFAULT NULL,
  `star4` int(11) DEFAULT NULL,
  `star5` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'自控力(斯坦福大学很受欢迎的心理学课程)','http://www.amazon.cn/%E8%87%AA%E6%8E%A7%E5%8A%9B-%E5%87%AF%E5%88%A9%E2%80%A2%E9%BA%A6%E6%A0%BC%E5%B0%BC%E6%A0%BC%E5%B0%94/dp/B008AGHPM2/ref=zg_bs_books_4/477-4184450-6459515','http://www.amazon.cn/product-reviews/B008AGHPM2/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',5917,4.4,182,202,588,1268,3677,1,'2015-10-27 21:42:37'),(2,'一个人的朝圣','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00EC7DOT6/ref=zg_bs_books_5/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00EC7DOT6/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',1834,4.6,27,28,113,361,1305,1,'2015-10-27 21:42:37'),(3,'DK儿童动物百科全书(第2版)','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00KD93EYW/ref=zg_bs_books_6/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00KD93EYW/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',35,4.9,0,0,0,3,32,1,'2015-10-27 21:42:37'),(4,'我的第一本专注力训练书（专注的孩子更聪明）','http://www.amazon.cn/%E6%88%91%E7%9A%84%E7%AC%AC%E4%B8%80%E6%9C%AC%E4%B8%93%E6%B3%A8%E5%8A%9B%E8%AE%AD%E7%BB%83%E4%B9%A6-%E7%BE%8E%E5%9B%BD%E8%BF%AA%E5%A3%AB%E5%B0%BC%E5%85%AC%E5%8F%B8/dp/B0083DP0CY/ref=zg_bs_books_7/477-4184450-6459515','http://www.amazon.cn/product-reviews/B0083DP0CY/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',3874,4.7,25,39,166,551,3093,1,'2015-10-27 21:42:37'),(5,'小王子(精美典藏版)(随机赠送独家定制别册)','http://www.amazon.cn/%E5%B0%8F%E7%8E%8B%E5%AD%90-%E5%AE%89%E6%89%98%E4%B8%87%E2%80%A2%E5%BE%B7%E2%80%A2%E5%9C%A3%E5%9F%83%E5%85%8B%E8%8B%8F%E4%BD%A9%E9%87%8C/dp/B00ANFMU8W/ref=zg_bs_books_8/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00ANFMU8W/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',1484,4.7,24,13,73,197,1177,1,'2015-10-27 21:42:37'),(6,'恋爱口语:我们到底要跟男人聊什么?','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B015E1SUS8/ref=zg_bs_books_9/477-4184450-6459515','http://www.amazon.cn/product-reviews/B015E1SUS8/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',1,5,0,0,0,0,1,1,'2015-10-27 21:42:37'),(7,'郑玉巧育儿经•婴儿卷(全新修订彩色版)','http://www.amazon.cn/%E9%83%91%E7%8E%89%E5%B7%A7%E8%82%B2%E5%84%BF%E7%BB%8F%E2%80%A2%E5%A9%B4%E5%84%BF%E5%8D%B7-%E9%83%91%E7%8E%89%E5%B7%A7/dp/B00A0ETJM6/ref=zg_bs_books_10/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00A0ETJM6/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',1368,4.6,21,14,72,249,1012,1,'2015-10-27 21:42:37'),(8,'宝宝第一套好性格养成书:皮特猫(套装共6册)','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00QLEU0YA/ref=zg_bs_books_11/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00QLEU0YA/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',478,4.6,8,10,27,55,378,1,'2015-10-27 21:42:37'),(9,'三体(1-3)(套装共3册)','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00OB3SNMY/ref=zg_bs_books_12/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00OB3SNMY/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',1273,4.4,73,38,70,167,925,1,'2015-10-27 21:42:37'),(10,'棚车少年·第3辑(9-12)(中英双语)(套装共8册)(附CD)(当孩子遇到挫折，这套书能让他们笑对人生)','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00W8Y1410/ref=zg_bs_books_13/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00W8Y1410/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',52,4.8,0,0,3,5,44,1,'2015-10-27 21:42:37'),(11,'从0到1:开启商业与未来的秘密','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00RWP6BOU/ref=zg_bs_books_14/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00RWP6BOU/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',507,4.4,15,20,46,89,337,1,'2015-10-27 21:42:37'),(12,'无声告白','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00ZHXYT7U/ref=zg_bs_books_15/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00ZHXYT7U/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',747,4.7,10,16,33,104,584,1,'2015-10-27 21:42:37'),(13,'(2016)肖秀荣考研政治命题人1000题(试题分册+解析分册)(套装共2册)','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00YGL7A2U/ref=zg_bs_books_16/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00YGL7A2U/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',198,4.4,15,3,11,31,138,1,'2015-10-27 21:42:37'),(14,'阿弥陀佛么么哒(亚马逊独家大冰彩页版本)','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B0126T6CI6/ref=zg_bs_books_17/477-4184450-6459515','http://www.amazon.cn/product-reviews/B0126T6CI6/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',401,4.7,8,8,19,29,337,1,'2015-10-27 21:42:37'),(15,'追风筝的人','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00DFYLXN6/ref=zg_bs_books_18/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00DFYLXN6/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',11468,4.6,139,148,559,2087,8535,1,'2015-10-27 21:42:37'),(16,'琅琊榜(修订版)(典藏版)(套装共3册)','http://www.amazon.cn/%E5%9B%BE%E4%B9%A6/dp/B00KKGP4H8/ref=zg_bs_books_19/477-4184450-6459515','http://www.amazon.cn/product-reviews/B00KKGP4H8/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',157,4.6,3,6,8,22,118,1,'2015-10-27 21:42:37'),(17,'托马斯和朋友一定有办法(套装共10册)','http://www.amazon.cn/%E6%89%98%E9%A9%AC%E6%96%AF%E5%92%8C%E6%9C%8B%E5%8F%8B%E4%B8%80%E5%AE%9A%E6%9C%89%E5%8A%9E%E6%B3%95-%E8%89%BE%E9%98%81%E8%90%8C-%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8/dp/B0087Z470O/ref=zg_bs_books_20/477-4184450-6459515','http://www.amazon.cn/product-reviews/B0087Z470O/ref=acr_zeitgeist_text/477-4184450-6459515?ie=UTF8&showViewpoints=1',226,4.7,2,1,14,24,185,1,'2015-10-27 21:42:37');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-27 23:32:46
