/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50173
Source Host           : 127.0.0.1:3306
Source Database       : datamanagement

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-08-01 23:06:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `app`
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `createtime` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app
-- ----------------------------
INSERT INTO `app` VALUES ('1', '企业1', '1', '启用', '2014-05-28');

-- ----------------------------
-- Table structure for `authority`
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', 'ROLE_USER', '用户', '1');
INSERT INTO `authority` VALUES ('2', 'ROLE_ADMIN', '管理员', '1');
INSERT INTO `authority` VALUES ('3', 'ROLE_ANONYMOUS', '游客', '1');

-- ----------------------------
-- Table structure for `authority_power`
-- ----------------------------
DROP TABLE IF EXISTS `authority_power`;
CREATE TABLE `authority_power` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authorityId` bigint(20) DEFAULT NULL,
  `powerId` bigint(20) DEFAULT NULL,
  `powerResource` varchar(255) DEFAULT NULL,
  `authorityName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority_power
-- ----------------------------
INSERT INTO `authority_power` VALUES ('1', '1', '1', '/rs/**', 'ROLE_USER');
INSERT INTO `authority_power` VALUES ('2', '1', '4', '/index.jsp', 'ROLE_USER');
INSERT INTO `authority_power` VALUES ('4', '2', '1', '/rs/**', 'ROLE_ADMIN');
INSERT INTO `authority_power` VALUES ('5', '2', '2', '/user.html', 'ROLE_ADMIN');
INSERT INTO `authority_power` VALUES ('6', '2', '3', '/admin.html', 'ROLE_ADMIN');
INSERT INTO `authority_power` VALUES ('7', '2', '4', '/index.jsp', 'ROLE_ADMIN');
INSERT INTO `authority_power` VALUES ('8', '2', '15', 'cas/**', 'ROLE_ADMIN');
INSERT INTO `authority_power` VALUES ('9', '3', '5', '/rs/anonymous/**', 'ROLE_ANONYMOUS');
INSERT INTO `authority_power` VALUES ('10', '3', '6', '/rs/anonymousUser/**', 'ROLE_ANONYMOUS');

-- ----------------------------
-- Table structure for `function_power`
-- ----------------------------
DROP TABLE IF EXISTS `function_power`;
CREATE TABLE `function_power` (
  `func_role` int(10) NOT NULL AUTO_INCREMENT,
  `func_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`func_role`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of function_power
-- ----------------------------
INSERT INTO `function_power` VALUES ('1', '系统管理员');
INSERT INTO `function_power` VALUES ('2', '部门管理员');
INSERT INTO `function_power` VALUES ('3', '数据分析员');
INSERT INTO `function_power` VALUES ('4', '数据录入员');

-- ----------------------------
-- Table structure for `power`
-- ----------------------------
DROP TABLE IF EXISTS `power`;
CREATE TABLE `power` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of power
-- ----------------------------
INSERT INTO `power` VALUES ('1', '/rs/**', 'resource', '所有rest服务');
INSERT INTO `power` VALUES ('2', '/user.html', 'url', null);
INSERT INTO `power` VALUES ('3', '/admin.html', 'url', null);
INSERT INTO `power` VALUES ('4', '/index.jsp', 'url', null);
INSERT INTO `power` VALUES ('5', '/rs/anonymous/**', 'resource', '匿名rest服务');
INSERT INTO `power` VALUES ('6', '/rs/anonymousUser/**', 'resource', '匿名rest服务');
INSERT INTO `power` VALUES ('15', 'cas/**', 'service', 'cas client test from android');

-- ----------------------------
-- Table structure for `tanalysor`
-- ----------------------------
DROP TABLE IF EXISTS `tanalysor`;
CREATE TABLE `tanalysor` (
  `FAnaNo` varchar(50) NOT NULL DEFAULT '',
  `FAnaName` varchar(50) DEFAULT NULL,
  `FRole` varchar(20) DEFAULT NULL,
  `FPassword` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FAnaNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tanalysor
-- ----------------------------
INSERT INTO `tanalysor` VALUES ('001', '张攀', 'ROLE_USER', '110');

-- ----------------------------
-- Table structure for `tdepartment`
-- ----------------------------
DROP TABLE IF EXISTS `tdepartment`;
CREATE TABLE `tdepartment` (
  `FDepartNo` varchar(50) NOT NULL DEFAULT '',
  `FDepartName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FDepartNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tdepartment
-- ----------------------------
INSERT INTO `tdepartment` VALUES ('001', '维修部');
INSERT INTO `tdepartment` VALUES ('002', '外检部');
INSERT INTO `tdepartment` VALUES ('003', '003');

-- ----------------------------
-- Table structure for `tenumeration`
-- ----------------------------
DROP TABLE IF EXISTS `tenumeration`;
CREATE TABLE `tenumeration` (
  `FEnumType` varchar(50) NOT NULL,
  `FEnumName` varchar(50) NOT NULL DEFAULT '',
  `FEnumValue` int(200) DEFAULT NULL,
  PRIMARY KEY (`FEnumName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tenumeration
-- ----------------------------
INSERT INTO `tenumeration` VALUES ('表具类型', '3-2B', '1');
INSERT INTO `tenumeration` VALUES ('表具类型', '4-3C', '2');
INSERT INTO `tenumeration` VALUES ('表具类型', '4-5C', '3');
INSERT INTO `tenumeration` VALUES ('故障', '不走字', '1');
INSERT INTO `tenumeration` VALUES ('故障', '整机故障', '4');
INSERT INTO `tenumeration` VALUES ('故障', '无显示', '2');
INSERT INTO `tenumeration` VALUES ('故障', '阀门关闭', '3');

-- ----------------------------
-- Table structure for `tmeteranalysisinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tmeteranalysisinfo`;
CREATE TABLE `tmeteranalysisinfo` (
  `FMeterCode` varchar(14) NOT NULL DEFAULT '',
  `FMeterName` varchar(50) DEFAULT NULL,
  `FValveName` varchar(14) DEFAULT NULL,
  `FDepartment` varchar(50) DEFAULT NULL,
  `FAnalysor` varchar(50) DEFAULT NULL,
  `FCustomer` varchar(50) DEFAULT NULL,
  `FReportMisFune` int(50) DEFAULT NULL,
  `FConfirmMisFune` int(50) DEFAULT NULL,
  `FMeterReading` varchar(50) DEFAULT NULL,
  `FDateTime` date DEFAULT NULL,
  PRIMARY KEY (`FMeterCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tmeteranalysisinfo
-- ----------------------------
INSERT INTO `tmeteranalysisinfo` VALUES ('151150700757', '4-3B(QK1)(江南)', 'Z015012193', '分析部', '叶文佩', '宜昌焦化煤气公司', '1', '2', '323', '2017-07-20');
INSERT INTO `tmeteranalysisinfo` VALUES ('23411', 'wer', '324', 'wer', 'wre', 'wer', '1', '3', '234', '2017-08-22');

-- ----------------------------
-- Table structure for `treturnmeter`
-- ----------------------------
DROP TABLE IF EXISTS `treturnmeter`;
CREATE TABLE `treturnmeter` (
  `FMeterCode` varchar(14) NOT NULL,
  `FCustomer` varchar(50) DEFAULT NULL,
  `FMeterName` varchar(50) DEFAULT NULL,
  `FQuantity` int(200) DEFAULT NULL,
  `FRInvoNo` varchar(50) DEFAULT NULL,
  `FDateTime` date DEFAULT NULL,
  `FOperator` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FMeterCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treturnmeter
-- ----------------------------
INSERT INTO `treturnmeter` VALUES ('123124234', 'wer', '为', '5', '2345555', '2017-02-24', '李浪');
INSERT INTO `treturnmeter` VALUES ('151150700757', '宜昌焦化煤气公司', '4-3B(QK1)(江南)', '4', '10001', '2017-07-18', '邓超');

-- ----------------------------
-- Table structure for `treturnmeterinfo`
-- ----------------------------
DROP TABLE IF EXISTS `treturnmeterinfo`;
CREATE TABLE `treturnmeterinfo` (
  `FMeterCode` varchar(14) NOT NULL,
  `FCustomer` varchar(50) DEFAULT NULL,
  `FMeterName` varchar(50) DEFAULT NULL,
  `FQuantity` int(200) DEFAULT NULL,
  `FDateTime` date DEFAULT NULL,
  `FOperator` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`FMeterCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treturnmeterinfo
-- ----------------------------
INSERT INTO `treturnmeterinfo` VALUES ('15110700859', '武汉天然气', '4-3B(QK2)', '4', '2017-05-20', '李浪');
INSERT INTO `treturnmeterinfo` VALUES ('151150700757', '宜昌焦化煤气公司', '4-3B(QK1)(江南)', '4', '2017-07-05', '邓超');
INSERT INTO `treturnmeterinfo` VALUES ('151160700758', '深圳天然气', '4-2B', '6', '2016-03-14', '吕亚龙');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `appId` bigint(20) DEFAULT '1',
  `status` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `func_role` int(10) DEFAULT NULL,
  `departNo` varchar(50) DEFAULT NULL,
  `fname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'xiaozhujun', 'e10adc3949ba59abbe56e057f20f883e', '男', 'ROLE_USER;ROLE_ADMIN;ROLE_ANONYMOUS', '1', '启用', null, '1', '001', '小朱军');
INSERT INTO `user` VALUES ('2', 'zhangsan', '202cb962ac59075b964b07152d234b70', '男', 'ROLE_USER;ROLE_ANONYMOUS', '1', '启用', null, '2', '002', '张三');
INSERT INTO `user` VALUES ('3', 'sunhui', 'e68fa2bc61b75b8a06766e25905052c7', '男', 'ROLE_USER', '1', '启用', null, '2', '001', '孙辉');
INSERT INTO `user` VALUES ('4', 'liujinxia', 'c99c1cbefe13019978d90cb442cb8f78', '女', 'ROLE_ADMIN', '1', '启用', null, '4', '002', '刘金霞');
INSERT INTO `user` VALUES ('5', 'lilang', '202cb962ac59075b964b07152d234b70', '男', 'ROLE_USER', '1', '启用', null, '1', '003', 'li');

-- ----------------------------
-- Table structure for `user_authority`
-- ----------------------------
DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE `user_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `authorityId` bigint(20) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `authorityName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_authority
-- ----------------------------
INSERT INTO `user_authority` VALUES ('1', '1', '1', 'xiaozhujun', 'ROLE_USER');
INSERT INTO `user_authority` VALUES ('2', '1', '2', 'xiaozhujun', 'ROLE_ADMIN');
INSERT INTO `user_authority` VALUES ('3', '1', '3', 'xiaozhujun', 'ROLE_ANONYMOUS');
INSERT INTO `user_authority` VALUES ('4', '2', '3', 'zhangsan', 'ROLE_ANONYMOUS');
INSERT INTO `user_authority` VALUES ('5', '2', '1', 'zhangsan', 'ROLE_USER');
INSERT INTO `user_authority` VALUES ('6', '3', '1', 'sunhui', 'ROLE_USER');
INSERT INTO `user_authority` VALUES ('7', '4', '2', 'liujinxia', 'ROLE_ADMIN');
INSERT INTO `user_authority` VALUES ('8', '5', '1', 'lilang', 'ROLE_USER');
