/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : bookstore

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2017-07-21 14:11:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bid` char(32) NOT NULL,
  `bname` varchar(100) DEFAULT NULL,
  `price` decimal(5,1) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `cid` char(32) DEFAULT NULL,
  PRIMARY KEY (`bid`),
  KEY `cid` (`cid`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', 'Java编程思想（第4版）', '75.6', 'qq986945193', 'book_img/9317290-1_l.jpg', '1');
INSERT INTO `book` VALUES ('2', 'Java核心技术卷1', '68.5', 'qq986945193', 'book_img/20285763-1_l.jpg', '1');
INSERT INTO `book` VALUES ('3', 'Java就业培训教程', '39.9', 'qq986945193', 'book_img/8758723-1_l.jpg', '1');
INSERT INTO `book` VALUES ('4', 'Head First java', '47.5', '（美）塞若', 'book_img/9265169-1_l.jpg', '1');
INSERT INTO `book` VALUES ('5', 'JavaWeb开发详解', '83.3', 'qq986945193', 'book_img/22788412-1_l.jpg', '2');
INSERT INTO `book` VALUES ('6', 'Struts2深入详解', '63.2', 'qq986945193', 'book_img/20385925-1_l.jpg', '2');
INSERT INTO `book` VALUES ('7', '精通Hibernate', '30.0', 'qq986945193', 'book_img/8991366-1_l.jpg', '2');
INSERT INTO `book` VALUES ('8', '精通Spring2.x', '63.2', 'qq986945193', 'book_img/20029394-1_l.jpg', '2');
INSERT INTO `book` VALUES ('9', 'Javascript权威指南', '93.6', '（美）弗兰纳根', 'book_img/22722790-1_l.jpg', '3');

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cid` char(32) NOT NULL,
  `cname` varchar(100) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'JavaSE');
INSERT INTO `category` VALUES ('2', 'JavaEE');
INSERT INTO `category` VALUES ('3', 'Javascript');

-- ----------------------------
-- Table structure for `orderitem`
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem` (
  `iid` char(32) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `subtotal` decimal(10,0) DEFAULT NULL,
  `oid` char(32) NOT NULL,
  `bid` char(32) NOT NULL,
  PRIMARY KEY (`iid`),
  KEY `oid` (`oid`),
  KEY `bid` (`bid`),
  CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`),
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('36085A86C3E84C55B5035C1969A5F23B', '1', '76', '9BEC5EF088C847AABA213286D3888807', '1');
INSERT INTO `orderitem` VALUES ('F432EF871E6446619206ABDE69F987C6', '1', '83', '8BEFB3C97FF64A7D92D13935108D08DA', '5');
INSERT INTO `orderitem` VALUES ('F824BF35312746EB9A7871C8BE400595', '1', '83', 'D883DDB1EE7A45B2B91D80D7947384F5', '5');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `oid` char(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` decimal(10,0) DEFAULT NULL,
  `state` smallint(1) DEFAULT NULL,
  `uid` char(32) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `uid` (`uid`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `tb_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('8BEFB3C97FF64A7D92D13935108D08DA', '2017-07-13 16:29:36', '83', '1', 'admin', null);
INSERT INTO `orders` VALUES ('9BEC5EF088C847AABA213286D3888807', '2017-07-13 16:29:09', '76', '1', 'admin', null);
INSERT INTO `orders` VALUES ('9D2E561B57574D068DBF6EB62021D1CA', '2017-07-13 16:24:07', '83', '1', 'admin', null);
INSERT INTO `orders` VALUES ('C24B9A8DF757488781AC49EE1420F70E', '2017-07-13 16:24:19', '76', '1', 'admin', null);
INSERT INTO `orders` VALUES ('D883DDB1EE7A45B2B91D80D7947384F5', '2017-07-13 16:31:00', '83', '1', 'admin', null);

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `uid` char(32) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `code` char(64) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('77EF3DF3DFFA4CD29B9B4A3E97184C6C', '1500', '123456', '15090637650@163.com', '9895D28DAA3245B182A5D1B58A003E3068883EFDE5544CA6875513524704C60D', '1');
INSERT INTO `tb_user` VALUES ('admin', 'admin', 'admin', 'admin@admin.com', 'admin', '1');
