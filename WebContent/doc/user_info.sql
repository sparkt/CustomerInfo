/*
Navicat MySQL Data Transfer

Source Server         : 119.23.67.243
Source Server Version : 50725
Source Host           : 119.23.67.243:3306
Source Database       : user_info

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-03-11 00:10:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sex` int(11) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `tel_no` varchar(255) NOT NULL,
  `disclose` int(11) NOT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `work_address` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `finish_time` varchar(255) DEFAULT NULL COMMENT '成交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1552210856757', '李四A', '1', '22', '15012345671', '1', '', '', '', '15112345672', '2019-03-10 17:40:57', '1', '1001', null);
INSERT INTO `customer` VALUES ('1552210879140', '李四B', '0', '33', '15012345672', '1', '', '', '', '15112345672', '2019-03-10 17:41:19', '1', '1001', null);
INSERT INTO `customer` VALUES ('1552210890186', 'kka', '0', '21', '15112345699', '1', '21', '21', '2222222', '15112345687', '2019-03-10 17:41:30', '6', '1001', '2019-03-10 18:32:27.781');
INSERT INTO `customer` VALUES ('1552210897484', '李四C', '0', '22', '15012345673', '1', '', '', '', '15112345672', '2019-03-10 17:41:37', '1', '1001', null);
INSERT INTO `customer` VALUES ('1552210926740', '麻子A', '1', '11', '15012345674', '1', '', '', '', '15112345672', '2019-03-10 17:42:07', '1', '1002', null);
INSERT INTO `customer` VALUES ('1552210930566', 'kkb', '0', '21', '15112345617', '1', 'kkb', 'kkb', 'kkb44', '15112345687', '2019-03-10 17:42:11', '6', '1001', '2019-03-10 18:35:48.708');
INSERT INTO `customer` VALUES ('1552210946761', '麻子B', '0', '22', '15012345675', '1', '', '', '', '15112345672', '2019-03-10 17:42:27', '1', '1002', null);
INSERT INTO `customer` VALUES ('1552210971179', 'kkc', '0', '22', '15112345616', '0', 'kkc', 'kkc', 'kkc修改', '15112345687', '2019-03-10 17:42:51', '6', '1002', '2019-03-10 18:38:08.476');
INSERT INTO `customer` VALUES ('1552210988286', '阿音A', '0', '11', '15012345676', '1', '', '', '', '15112345672', '2019-03-10 17:43:08', '1', '1003', null);
INSERT INTO `customer` VALUES ('1552211006125', '阿音B', '1', '33', '15012345677', '1', '', '', '', '15112345672', '2019-03-10 17:43:26', '6', '1003', '2019-03-10 23:29:26.021');
INSERT INTO `customer` VALUES ('1552211031787', '阿音C', '0', '44', '15012345678', '1', '', '', '', '15112345672', '2019-03-10 17:43:52', '3', '1003', null);
INSERT INTO `customer` VALUES ('1552211037086', 'kkd', '0', '31', '15112345615', '1', 'kkd', 'kkd', 'kkd修改', '15112345687', '2019-03-10 17:43:57', '6', '1002', '2019-03-10 18:38:32.756');
INSERT INTO `customer` VALUES ('1552211050490', '阿音D', '1', '55', '15012345679', '1', '', '', '', '15112345672', '2019-03-10 17:44:10', '6', '1003', '2019-03-10 20:55:02.559');
INSERT INTO `customer` VALUES ('1552211160874', '阿猫A', '0', '11', '15012345670', '1', '', '', '', '15112345672', '2019-03-10 17:46:01', '3', '1004', null);
INSERT INTO `customer` VALUES ('1552211165399', 'kkf', '0', '22', '15112345613', '0', 'kkf', 'kkf', 'kkf', '15112345687', '2019-03-10 17:46:05', '3', '1009', null);
INSERT INTO `customer` VALUES ('1552211195891', '阿霞A', '1', '11', '18012345671', '1', '', '', '', '15112345672', '2019-03-10 17:46:36', '3', '1005', null);
INSERT INTO `customer` VALUES ('1552211198649', 'kkg', '1', '22', '15112345612', '1', '11', '11', '1133333', '15112345687', '2019-03-10 17:46:39', '6', '1011', '2019-03-10 19:17:09.856');
INSERT INTO `customer` VALUES ('1552211232109', '阿霞B', '1', '22', '18012345672', '1', '', '', '', '15112345672', '2019-03-10 17:47:12', '1', '1005', null);
INSERT INTO `customer` VALUES ('1552211280805', '周杰伦A', '1', '11', '18012345674', '1', '', '', '', '15112345672', '2019-03-10 17:48:01', '1', '1006', null);
INSERT INTO `customer` VALUES ('1552211295148', '周杰伦B', '0', '22', '18012345675', '1', '', '', '', '15112345672', '2019-03-10 17:48:15', '1', '1006', null);
INSERT INTO `customer` VALUES ('1552211462099', '无意烦A', '0', '11', '18012345675', '1', '', '', '', '15112345672', '2019-03-10 17:51:02', '1', '1007', null);
INSERT INTO `customer` VALUES ('1552212468283', 'kkh', '1', '11', '15112345611', '0', 'kkh', 'kkh', 'kkh333', '15112345687', '2019-03-10 18:07:48', '6', '1008', '2019-03-10 19:05:04.09');
INSERT INTO `customer` VALUES ('1552212491332', 'kki', '0', '22', '15112345610', '0', 'kki', 'kki', 'kki333', '15112345687', '2019-03-10 18:08:11', '6', '1008', '2019-03-10 19:06:18.949');
INSERT INTO `customer` VALUES ('1552212518403', 'kkj', '0', '11', '15112345609', '0', '15112345609', '15112345609', '15112345609333', '15112345687', '2019-03-10 18:08:38', '6', '1010', '2019-03-10 19:44:18.539');
INSERT INTO `customer` VALUES ('1552212546197', 'kkk', '0', '22', '15112345608', '0', '2', '2', '233', '15112345687', '2019-03-10 18:09:06', '6', '1004', '2019-03-10 19:09:21.604');
INSERT INTO `customer` VALUES ('1552212580634', 'kkl', '0', '22', '15112345607', '0', 'kkl', 'kkl', 'kkl33', '15112345687', '2019-03-10 18:09:41', '6', '1004', '2019-03-10 19:12:45.899');
INSERT INTO `customer` VALUES ('1552216545760', 'kku', '0', '22', '15112345622', '1', 'kku22', 'kku', 'kku', '15112345687', '2019-03-10 19:15:46', '1', '1006', null);
INSERT INTO `customer` VALUES ('1552218066263', 'kkq', '0', '22', '15112345602', '1', 'kkq', 'kkq', 'kkq333', '15112345687', '2019-03-10 19:41:06', '6', '1006', '2019-03-10 19:42:10.257');
INSERT INTO `customer` VALUES ('1552219678046', 'aaa', '0', '11', '15012345678', '1', '', '', 'aaa', '15185672300', '2019-03-10 20:07:58', '2', '1001', null);

-- ----------------------------
-- Table structure for `groupinfo`
-- ----------------------------
DROP TABLE IF EXISTS `groupinfo`;
CREATE TABLE `groupinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `captain_name` varchar(255) DEFAULT NULL,
  `group_info` varchar(255) DEFAULT NULL,
  `group_headcount` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `captain_phone` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupinfo
-- ----------------------------
INSERT INTO `groupinfo` VALUES ('37', '张三', '最牛的团队', '1', '牛逼团队', '15185672300', '2019-03-10 19:13:19.709');
INSERT INTO `groupinfo` VALUES ('38', 'yyb', '肖测试', '2', '肖测试', '15112345687', '2019-03-10 19:50:05.237');

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inform
-- ----------------------------
INSERT INTO `inform` VALUES ('9', '2019年03月10日19时50分36秒', '你已于2019年03月10日19时50分36秒加入15112345687团队', '15112345688');

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
  `type` int(255) DEFAULT '1' COMMENT '1普通，2管理员',
  `vip_grade` varchar(255) DEFAULT NULL,
  `status` int(255) DEFAULT NULL,
  `groups` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', 'admin', 'admin', '男', 'admin', '2', '0', '1', '0');
INSERT INTO `userinfo` VALUES ('34', '张三A', '123456', '男', '15112345671', '1', '0', '1', '0');
INSERT INTO `userinfo` VALUES ('35', 'yya', '123456', '男', '15112345688', '1', '0', '1', '15112345687');
INSERT INTO `userinfo` VALUES ('36', 'yyb', '123456', '女', '15112345687', '1', '1', '1', '15112345687');
INSERT INTO `userinfo` VALUES ('37', 'yyc', '123456', '男', '15112345686', '1', '0', '1', '0');
INSERT INTO `userinfo` VALUES ('38', '张三B', '123456', '女', '15112345672', '1', '0', '1', '0');
INSERT INTO `userinfo` VALUES ('39', '张三C', '123456', '男', '15112345673', '1', '0', '0', '0');
INSERT INTO `userinfo` VALUES ('40', 'yyd', '123456', '女', '15112345685', '1', '0', '0', '0');
INSERT INTO `userinfo` VALUES ('41', '张三D', '123456', '男', '15112345674', '1', '0', '0', '0');
INSERT INTO `userinfo` VALUES ('42', 'yye', '123456', '女', '15112345684', '1', '0', '0', '0');
INSERT INTO `userinfo` VALUES ('43', '张三E', '123456', '男', '15112345675', '1', '0', '0', '0');
INSERT INTO `userinfo` VALUES ('44', '张三', '000000', '男', '15185672300', '1', '1', '1', '15185672300');
