/*
Navicat MySQL Data Transfer

Source Server         : 120.79.188.45_3306
Source Server Version : 50505
Source Host           : 120.79.188.45:3306
Source Database       : user_info

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2019-01-18 09:50:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `AdminInfo`
-- ----------------------------
DROP TABLE IF EXISTS `AdminInfo`;
CREATE TABLE `AdminInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(255) DEFAULT NULL,
  `admin_password` varchar(255) DEFAULT NULL,
  `admin_sex` varchar(255) DEFAULT NULL,
  `admin_phone_no` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of AdminInfo
-- ----------------------------

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sex` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `tel_no` varchar(255) NOT NULL,
  `disclose` int(11) NOT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `work_address` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('123456', '赵钱孙李', '1', '22', '123452', '1', '汉族', '凯里学院', '加油', '18224995161', '2019-01-17 21:27:07', '2', '1001');

-- ----------------------------
-- Table structure for `GroupInfo`
-- ----------------------------
DROP TABLE IF EXISTS `GroupInfo`;
CREATE TABLE `GroupInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `captain_name` varchar(255) DEFAULT NULL,
  `group_info` varchar(255) DEFAULT NULL,
  `group_headcount` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `captain_phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of GroupInfo
-- ----------------------------
INSERT INTO `GroupInfo` VALUES ('1', '1111', '11111', '111', '111111', '18224995161');
INSERT INTO `GroupInfo` VALUES ('2', '1', '1', '1', '1', '22');
INSERT INTO `GroupInfo` VALUES ('3', '33', '33aaaaaaaa', '1', '33a', '33');
INSERT INTO `GroupInfo` VALUES ('4', 'a', 'a', 'a', 'ab', '12');
INSERT INTO `GroupInfo` VALUES ('5', 'qq', 'qq', '1', 'qq', 'qq');
INSERT INTO `GroupInfo` VALUES ('6', '10', '10', '1', '10', '10');
INSERT INTO `GroupInfo` VALUES ('7', '11', '11', '1', '11', '11');
INSERT INTO `GroupInfo` VALUES ('8', '111', '111', '111', '111', '123');

-- ----------------------------
-- Table structure for `inform`
-- ----------------------------
DROP TABLE IF EXISTS `inform`;
CREATE TABLE `inform` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inform
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '0');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_sex` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `vip_grade` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `check` varchar(255) DEFAULT NULL,
  `groups` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '韦乾来', '123456', '男', '18224995161', '0', '0', '0', '0', '18224995161');
