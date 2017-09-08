/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : bookstore

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2017-09-08 10:41:06
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
  `del` int(5) NOT NULL,
  PRIMARY KEY (`bid`),
  KEY `cid` (`cid`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', 'Java编程思想（第4版）', '75.6', 'qq986945193', 'book_img/9317290-1_l.jpg', '1', '0');
INSERT INTO `book` VALUES ('1491AFF9110447D39A3D5673513747CB', 'ad', '1123.0', 'af', 'book_img/D840740E7BC540C7B0F538FFF0C3B14A_adv_point.png', '2', '1');
INSERT INTO `book` VALUES ('2', 'Java核心技术卷1', '68.5', 'qq986945193', 'book_img/20285763-1_l.jpg', '1', '0');
INSERT INTO `book` VALUES ('3', 'Java就业培训教程', '39.9', 'qq986945193', 'book_img/8758723-1_l.jpg', '1', '0');
INSERT INTO `book` VALUES ('4', 'Head First java', '47.5', '（美）塞若', 'book_img/9265169-1_l.jpg', '1', '0');
INSERT INTO `book` VALUES ('5', 'JavaWeb开发详解', '83.3', 'qq986945193', 'book_img/22788412-1_l.jpg', '2', '0');
INSERT INTO `book` VALUES ('6', 'Struts2深入详解', '63.2', 'qq986945193', 'book_img/20385925-1_l.jpg', '2', '0');
INSERT INTO `book` VALUES ('7', '精通Hibernate', '30.0', 'qq986945193', 'book_img/8991366-1_l.jpg', '2', '0');
INSERT INTO `book` VALUES ('8', '精通Spring2.x', '63.2', 'qq986945193', 'book_img/20029394-1_l.jpg', '2', '0');
INSERT INTO `book` VALUES ('9', 'Javascript权威指南', '93.6', '（美）弗兰纳根', 'book_img/22722790-1_l.jpg', '3', '0');
INSERT INTO `book` VALUES ('DCDC1967C8F743F18AFA0253D110F8BF', 'aa', '0.0', '', 'book_img/4B71173254DC4784BE7E7FBE8BFBB3F7_add_info_tip_pass.png', '2', '1');

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
INSERT INTO `category` VALUES ('3401357772504224B9A24551BFD01FC6', 'linux');
INSERT INTO `category` VALUES ('6D85669091BE44F2A4BC57BFB3550CFC', '1234567');

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
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`),
  CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('36085A86C3E84C55B5035C1969A5F23B', '1', '76', '9BEC5EF088C847AABA213286D3888807', '1');
INSERT INTO `orderitem` VALUES ('5A7EB747D1ED401DB9179641C7C351CC', '1', '76', '6959F28DCA204C8092D04E6C6E17CA37', '1');
INSERT INTO `orderitem` VALUES ('631B69CBFFA147059E2342122E965CCE', '1', '76', '7DCE98F850E545DDA8C87E81354A36D1', '1');
INSERT INTO `orderitem` VALUES ('6C562001A33A4383B9928506542C08BE', '1', '83', '7DCE98F850E545DDA8C87E81354A36D1', '5');
INSERT INTO `orderitem` VALUES ('79E69782A1A94CCCB95A3CDFF957CB89', '1', '76', '9F9A7F72D5FD4F1596DC30E2DFA748E8', '1');
INSERT INTO `orderitem` VALUES ('7E512AE7B8DE46A98A84F5E52C439EB3', '1', '69', '9F9A7F72D5FD4F1596DC30E2DFA748E8', '2');
INSERT INTO `orderitem` VALUES ('92436B6AF5B74BC59057D79987E7EE78', '1', '94', '9F9A7F72D5FD4F1596DC30E2DFA748E8', '9');
INSERT INTO `orderitem` VALUES ('AF9B770C407241A1B2DE088230D4F271', '1', '76', 'B0452EDDB6B543C4BF9B9825CE244275', '1');
INSERT INTO `orderitem` VALUES ('D97527E9BF8849FC839AE2DC3CBE67A5', '1', '76', '68FF31AEBDF2492A97830116EC6E11B3', '1');
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
INSERT INTO `orders` VALUES ('68FF31AEBDF2492A97830116EC6E11B3', '2017-08-25 13:52:40', '76', '4', 'admin', null);
INSERT INTO `orders` VALUES ('6959F28DCA204C8092D04E6C6E17CA37', '2017-09-08 10:39:08', '76', '1', 'admin', null);
INSERT INTO `orders` VALUES ('7DCE98F850E545DDA8C87E81354A36D1', '2017-08-25 13:52:59', '159', '4', 'admin', null);
INSERT INTO `orders` VALUES ('8BEFB3C97FF64A7D92D13935108D08DA', '2017-07-13 16:29:36', '83', '4', 'admin', null);
INSERT INTO `orders` VALUES ('9BEC5EF088C847AABA213286D3888807', '2017-07-13 16:29:09', '76', '4', 'admin', null);
INSERT INTO `orders` VALUES ('9D2E561B57574D068DBF6EB62021D1CA', '2017-07-13 16:24:07', '83', '3', 'admin', null);
INSERT INTO `orders` VALUES ('9F9A7F72D5FD4F1596DC30E2DFA748E8', '2017-08-25 15:05:32', '238', '4', 'admin', null);
INSERT INTO `orders` VALUES ('B0452EDDB6B543C4BF9B9825CE244275', '2017-08-25 14:37:11', '76', '2', 'admin', null);
INSERT INTO `orders` VALUES ('C24B9A8DF757488781AC49EE1420F70E', '2017-07-13 16:24:19', '76', '1', 'admin', null);
INSERT INTO `orders` VALUES ('D883DDB1EE7A45B2B91D80D7947384F5', '2017-07-13 16:31:00', '83', '4', 'admin', null);

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
INSERT INTO `tb_user` VALUES ('77EF3DF3DFFA4CD29B9B4A3E97184C6C', '1500', '123456', 'admin@163.com', '9895D28DAA3245B182A5D1B58A003E3068883EFDE5544CA6875513524704C60D', '1');
INSERT INTO `tb_user` VALUES ('admin', 'admin', 'admin', 'admin@admin.com', 'admin', '1');
