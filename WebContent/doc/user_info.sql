/*
Navicat MySQL Data Transfer

Source Server         : 119.23.67.243
Source Server Version : 50725
Source Host           : 119.23.67.243:3306
Source Database       : user_info

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-03-10 02:47:09
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
  `type` int(11) NOT NULL,
  `finish_time` varchar(255) DEFAULT NULL COMMENT '成交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1551168265428', '大师兄', '1', '22', '115546', '1', '', '', '1', '15121420631', '2019-01-26 16:04:25', '6', '1011', '2019-01-26 16:04:25');
INSERT INTO `customer` VALUES ('1551505960954', 's222', '0', '33', '32312312', '1', '', '', '2', '15121420631', '2019-03-02 13:52:40', '6', '1001', '2019-02-26 16:04:25');
INSERT INTO `customer` VALUES ('1551514895765', '韦乾来', '1', '21', '18224995161', '1', '', '', '2', '15121420631', '2019-03-02 16:21:35', '6', '1001', '2019-02-26 16:04:25');
INSERT INTO `customer` VALUES ('1551860832601', 't1', '1', '2', '231231', '1', '', '', '2', '15121420631', '2019-03-06 16:27:13', '6', '1001', '2019-01-26 16:04:25');
INSERT INTO `customer` VALUES ('1551872250243', 't2', '0', '22', '1', '1', '', '', '2', '15121420631', '2019-03-06 19:37:30', '6', '1001', '2019-02-26 16:04:25');
INSERT INTO `customer` VALUES ('1551872251633', 't2', '0', '22', '1', '1', '', '', '2', '15121420631', '2019-03-06 19:37:32', '6', '1001', '2019-03-26 16:04:25');
INSERT INTO `customer` VALUES ('1551872335206', 'xiao2', '1', '12', '15112345687', '0', '12', '12', '12222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222', '15121420631', '2019-03-06 19:38:55', '6', '1002', '2019-04-26 16:04:25');
INSERT INTO `customer` VALUES ('1551872568134', '王驰111', '1', '22', '18956328967', '1', '', '', '2', '15121420631', '2019-03-06 19:42:48', '6', '1001', '2019-02-26 16:04:25');
INSERT INTO `customer` VALUES ('1551958342284', '王力宏', '1', '20', '18286566256', '1', '汉族', '', '爱是不废话巴山回房间八神疾风按时开放你家是暗示法节哀顺变借款方氨基酸部分阿贾克斯不放假卡斯比房间卡萨房间卡书法家卡手机客服哈时间看房尽快把数据开发商就开好房间爱康师傅加快十分骄傲和设计开发卡上房间卡好舒服阿卡就是房间卡好舒服奥斯卡复活驾驶飞机和卡萨房间号发了货款按时付款后按时发货来看按时付款揽胜发生开放灵活暗示法卡死了会发生括号里路口', '15285276736', '2019-03-07 19:32:22', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551959308683', '111', '1', '33', '15286354879', '1', '33', '33', '33', '15286349520', '2019-03-07 19:48:29', '6', '1001', null);
INSERT INTO `customer` VALUES ('1551960182037', 'b1', '1', '11', '21312314', '1', '', '', '', '18084324736', '2019-03-07 20:03:02', '3', '1001', null);
INSERT INTO `customer` VALUES ('1551960193968', 'b2', '0', '11', '4321424321', '1', '', '', '', '18084324736', '2019-03-07 20:03:14', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551960204721', 'b3', '1', '1231', '12311231', '1', '', '', '', '18084324736', '2019-01-07 20:03:25', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551960220770', 'b5', '1', '4', '31231541243', '1', '', '', '', '18084324736', '2019-02-07 20:03:41', '3', '1001', null);
INSERT INTO `customer` VALUES ('1551960240072', 'b4', '1', '4', '32131', '1', '', '', '', '18084324736', '2019-02-07 20:04:00', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551960768003', 'b6', '1', '1', '1231231', '1', '', '', '', '18084324736', '2019-03-07 20:12:48', '6', '1001', null);
INSERT INTO `customer` VALUES ('1551960789621', 'b9', '1', '111', '12312313', '1', '', '', '', '18084324736', '2019-02-07 20:13:10', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551961381340', 'mm', '0', '11', '1', '1', '', '', '', '18084324736', '2019-03-07 20:23:01', '3', '1001', null);
INSERT INTO `customer` VALUES ('1551961526686', 'pp', '1', '1', '1231', '1', '', '', '', '18084324736', '2019-03-07 20:25:27', '3', '1001', null);
INSERT INTO `customer` VALUES ('1551962469311', 't2', '1', '22', '15184564', '1', '', '', '', '18224995161', '2019-03-07 20:41:09', '3', '1001', null);
INSERT INTO `customer` VALUES ('1551962478943', '22', '0', '33', '33', '1', '', '', '', '18224995161', '2019-03-07 20:41:19', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551962486327', '33', '0', '33', '33', '1', '', '', '', '18224995161', '2019-03-07 20:41:26', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551962519495', 't2', '0', '23', '12312', '1', '', '', '', '18224995161', '2019-03-07 20:41:59', '6', '1001', null);
INSERT INTO `customer` VALUES ('1551962528503', '5ttt', '1', '213', '23123123', '1', '', '', '', '18224995161', '2019-03-07 20:42:09', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551962530386', 'ttt', '1', '1', '123', '1', '', '', '', '18084324736', '2019-03-07 20:42:10', '6', '1001', null);
INSERT INTO `customer` VALUES ('1551962585639', 't33', '0', '32', '132123123', '1', '', '', '', '18224995161', '2019-03-07 20:43:06', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551962593191', '5tt', '0', '323', '23123', '1', '', '', '', '18224995161', '2019-03-07 20:43:13', '6', '1001', null);
INSERT INTO `customer` VALUES ('1551962601037', '444', '0', '23123', '3213', '1', '', '', '', '18224995161', '2019-03-07 20:43:21', '6', '1001', null);
INSERT INTO `customer` VALUES ('1551962608207', '123123', '0', '2312', '123123', '1', '', '', '', '18224995161', '2019-03-07 20:43:28', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551962615503', '21312', '0', '123123', '123123', '1', '', '', '', '18224995161', '2019-03-07 20:43:36', '3', '1001', null);
INSERT INTO `customer` VALUES ('1551963490569', 'ttt22', '1', '22', '1155245', '1', '', '', '', '18224995161', '2019-03-07 20:58:11', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551963696646', 'tq1', '0', '22', '213123', '1', '', '', '', '18224995161', '2019-03-07 21:01:37', '1', '1001', null);
INSERT INTO `customer` VALUES ('1551965949852', 'xxxx1234', '0', '333', '15115444444', '1', '3434', '443', '43455555', '15286349520', '2019-03-07 21:39:10', '2', '1008', null);
INSERT INTO `customer` VALUES ('1552013542701', '我问问', '0', '30', '15122221235', '1', '', '', '2222', '15121420631', '2019-03-08 10:52:23', '2', '1001', null);
INSERT INTO `customer` VALUES ('1552027197388', 'wewewe', '1', '12', '15012345678', '0', '', '', '', '18224995161', '2019-03-08 14:39:57', '1', '1001', null);
INSERT INTO `customer` VALUES ('1552027277468', 'qqeeeee', '1', '22', '15087654321', '1', '', '', '', '18224995161', '2019-03-08 14:41:17', '1', '1001', null);
INSERT INTO `customer` VALUES ('1552027357231', '果果', '1', '33', '18012345678', '1', '', '', '知乎', '18224995161', '2019-03-08 14:42:37', '3', '1001', null);
INSERT INTO `customer` VALUES ('1552030593242', 'ggweiqq', '1', '33', '18212345695', '1', '', '', '', '18224995161', '2019-03-08 15:36:33', '1', '1001', null);
INSERT INTO `customer` VALUES ('1552112721000', '呜呜呜', '1', '33', '15012345678', '1', '', '', '我已经跟进了', '18212446530', '2019-03-09 14:25:21', '2', '1001', null);
INSERT INTO `customer` VALUES ('1552113463259', 'qqq', '1', '20', '15185672300', '1', '苗', '0000', '000', '15185672300', '2019-03-09 14:37:43', '1', '1001', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupinfo
-- ----------------------------
INSERT INTO `groupinfo` VALUES ('5', 'qq', 'qq', '1', 'qq', 'qq', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('6', '10', '10', '1', '10', '10', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('7', '11', '11', '1', '11', '1', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('10', '无敌小团队', '18286566256', '1', '无敌', '18224995161', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('11', '123', '18286566256', '1', '123', '李金鹏', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('12', '123456', '18286566256', '1', '123456', '123', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('13', '11', '18286566256', '1', '11', '456', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('15', 'sdfs', '15286349520', '1', 'sdsf', '121', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('16', '无敌', '15285276736', '1', '无敌', '李小菁', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('17', '', '', '', '', '韦乾来', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('18', '张三', '222222222222', '4', '333333333333333', '15185672300', '2019-03-09 00:51:34.351');
INSERT INTO `groupinfo` VALUES ('19', '王小二', 'qqqqq', '1', 'wwww', '0', '2019-03-09 00:57:06.89');
INSERT INTO `groupinfo` VALUES ('20', '王小二', '33', '1', '33', '0', '2019-03-09 00:59:11.375');
INSERT INTO `groupinfo` VALUES ('21', '王小二', '1', '1', '1', '0', '2019-03-09 01:00:16.373');
INSERT INTO `groupinfo` VALUES ('22', '王小二', '3', '1', '3', '0', '2019-03-09 01:00:43.506');
INSERT INTO `groupinfo` VALUES ('28', '张三', 'qqq', '1', 'qqq', '15185672300', '2019-03-09 13:56:16.293');
INSERT INTO `groupinfo` VALUES ('30', 'www', 'www', '1', '00000', '15185672333', '2019-03-09 15:26:02.689');
INSERT INTO `groupinfo` VALUES ('31', '测试', 'eeee', '1', 'eeee', '15185672110', '2019-03-09 15:31:10.059');
INSERT INTO `groupinfo` VALUES ('32', 'aaa', 'aaa', 'aaa', 'aaa', '小米烧', '2019-03-09 15:33:22.189');
INSERT INTO `groupinfo` VALUES ('33', 'zzz', '44', '1', '44444444', '15185672303', '2019-03-09 15:34:29.891');
INSERT INTO `groupinfo` VALUES ('35', '无敌小金刚', '超级强', '5', '不坏之身', '18712345678', '2019-03-09 16:23:27.379');
INSERT INTO `groupinfo` VALUES ('36', '123123', '123123', '1', '123123', '123123', '2019-03-09 20:37:58.727');

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inform
-- ----------------------------
INSERT INTO `inform` VALUES ('1', '2019年02月11日22时07分13秒', '你已于2019年02月11日22时07分13秒加入22团队', '18224995161');
INSERT INTO `inform` VALUES ('2', '2019年02月26日16时03分35秒', '你已于2019年02月26日16时03分35秒加入18224995161团队', '18212446530');
INSERT INTO `inform` VALUES ('3', '2019年02月26日16时37分34秒', '你已于2019年02月26日16时37分34秒加入18224995161团队', '18084324736');
INSERT INTO `inform` VALUES ('4', '2019年03月05日21时19分24秒', '你已于2019年03月05日21时19分24秒加入18224995161团队', '15985385289');
INSERT INTO `inform` VALUES ('5', '2019年03月07日19时49分10秒', '你已于2019年03月07日19时49分10秒加入18224995161团队', '15870365334');
INSERT INTO `inform` VALUES ('6', '2019年03月07日20时52分52秒', '你已于2019年03月07日20时52分52秒加入18224995161团队', '15286349521');
INSERT INTO `inform` VALUES ('7', '2019年03月08日12时01分19秒', '你已于2019年03月08日12时01分19秒加入15121420631团队', '15286349522');
INSERT INTO `inform` VALUES ('8', '2019年03月09日15时42分38秒', '你已于2019年03月09日15时42分38秒加入15185672300团队', '15123456321');

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
  `type` varchar(255) DEFAULT '1' COMMENT '1普通，2管理员',
  `vip_grade` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `groups` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', 'admin', 'admin', '男', 'admin', '2', '0', '1', '0');
INSERT INTO `userinfo` VALUES ('2', 'weiqianlai', '123456', '男', '18212446530', '1', '1', '1', '18224995161');
INSERT INTO `userinfo` VALUES ('3', 'zhang', '000000', '男', '123456789', '1', '1', '已审核', '18224995161');
INSERT INTO `userinfo` VALUES ('4', 'xiao', '123456', '男', '15012345678', '1', '0', '已审核', '18224995161');
INSERT INTO `userinfo` VALUES ('5', '车子', '000000', '男', '123569874', '1', '0', '已审核', '0');
INSERT INTO `userinfo` VALUES ('6', '王菜鸟', '123', '男', '15985385289', '1', '1', '1', '0');
INSERT INTO `userinfo` VALUES ('7', 'guoguo', '123456', '男', '15870365334', '1', '0', '已审核', '18224995161');
INSERT INTO `userinfo` VALUES ('8', '韦乾来', '123456', '男', '18224995161', '1', '1', '已审核', '18224995161');
INSERT INTO `userinfo` VALUES ('9', '李金鹏', '123456', '男', '18286566256', '1', '1', '已审核', '18224995161');
INSERT INTO `userinfo` VALUES ('10', 'loukyminipig', '111111', '男', '18084324736', '1', '0', '已审核', '18224995161');
INSERT INTO `userinfo` VALUES ('11', '2222', '2222', '男', 'qaz', '1', '1', '0', '0');
INSERT INTO `userinfo` VALUES ('12', '0000', '00000', '男', '00000', '1', '0', '未审核', '0');
INSERT INTO `userinfo` VALUES ('13', 'mmmm', 'mmm', '男', 'mmm', '1', '0', '未审核', '0');
INSERT INTO `userinfo` VALUES ('14', 'xiao', '123456', '男', '15286349520', '1', '1', '0', '1');
INSERT INTO `userinfo` VALUES ('15', 'xiaoxiao', '123456', '女', '15286349521', '1', '0', '0', '15185672300');
INSERT INTO `userinfo` VALUES ('16', 'xiaoxx', '123456', '男', '15286349522', '1', '0', '0', '15185672300');
INSERT INTO `userinfo` VALUES ('17', '哈哈哈', '000000', '男', '15185672330', '1', '1', '1', '15185672330');
INSERT INTO `userinfo` VALUES ('18', '李小菁', '123456', '女', '15285276736', '1', '1', '1', '0');
INSERT INTO `userinfo` VALUES ('19', 'xxxddd', '123456', '男', '15212345678', '1', '0', '1', '0');
INSERT INTO `userinfo` VALUES ('20', 'lalalalala', '111111', '男', '1111', '1', '0', '0', '0');
INSERT INTO `userinfo` VALUES ('21', 'lalallaa', '123456', '男', '111', '1', '1', '1', '18224995161');
INSERT INTO `userinfo` VALUES ('22', '凯里人', '123456', '女', '15123456321', '1', '0', '1', '15185672300');
INSERT INTO `userinfo` VALUES ('23', '电饭锅', '      ', '男', '15121452145', '1', '0', '0', '0');
INSERT INTO `userinfo` VALUES ('24', '项规定', '123456', '女', '15121420631', '1', '1', '1', '0');
INSERT INTO `userinfo` VALUES ('25', 'Liang', '123456', '男', '13888888888', '1', '0', '1', '0');
INSERT INTO `userinfo` VALUES ('27', '张三', '000000', '男', '15185672300', '1', '1', '1', '15185672300');
INSERT INTO `userinfo` VALUES ('28', 'www', '000000', '男', '15185672333', '1', '1', '1', '15185672333');
INSERT INTO `userinfo` VALUES ('29', '小米烧', '123456', '男', '18912345678', '1', '1', '1', '18912345678');
INSERT INTO `userinfo` VALUES ('30', '测试', '000000', '男', '15185672110', '1', '1', '1', '15185672110');
INSERT INTO `userinfo` VALUES ('31', 'zzz', '000000', '男', '15185672303', '1', '1', '1', '15185672303');
INSERT INTO `userinfo` VALUES ('32', '张胡玫', '000000', '男', '15185672000', '1', '1', '1', '15185672000');
INSERT INTO `userinfo` VALUES ('33', '无意烦', '123456', '女', '18712345678', '1', '1', '1', '18712345678');
