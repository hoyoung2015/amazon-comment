DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `product_name` varchar(145) DEFAULT NULL,
  `review_votes` int(11) DEFAULT NULL,
  `helpful_votes` int(11) DEFAULT NULL,
  `star` float DEFAULT NULL,
  `review_title` varchar(255) DEFAULT NULL,
  `review_date` date DEFAULT NULL,
  `review_author` varchar(145) DEFAULT NULL,
  `review_author_url` varchar(255) DEFAULT NULL,
  `thread_id` varchar(45) DEFAULT NULL,
  `review_text` text,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;





SELECT * FROM amazon.comments limit 5000,5000;

SELECT * FROM amazon.comments where pid=18;


SELECT count(*) FROM amazon.comments;

SET NAMES 'utf8mb4';


SET NAMES utf8mb4;
ALTER DATABASE amazon CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;