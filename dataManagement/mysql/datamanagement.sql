/*
Navicat MySQL Data Transfer

Source Server         : dc
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : datamanagement

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-09-23 14:55:20
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
  PRIMARY KEY (`func_role`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
-- Table structure for `tdepartment`
-- ----------------------------
DROP TABLE IF EXISTS `tdepartment`;
CREATE TABLE `tdepartment` (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `FDepartNo` varchar(50) DEFAULT '',
  `FDepartName` varchar(50) DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tdepartment
-- ----------------------------
INSERT INTO `tdepartment` VALUES ('1', '001', '维修部', '0');
INSERT INTO `tdepartment` VALUES ('2', '002', '外检部', '0');

-- ----------------------------
-- Table structure for `tenumeration`
-- ----------------------------
DROP TABLE IF EXISTS `tenumeration`;
CREATE TABLE `tenumeration` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FEnumType` varchar(50) DEFAULT NULL,
  `FEnumName` varchar(50) DEFAULT '',
  `FEnumValue` int(200) DEFAULT NULL,
  `FDescription` varchar(200) DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tenumeration
-- ----------------------------
INSERT INTO `tenumeration` VALUES ('1', '故障类型', '无显', '1', '0', '0');
INSERT INTO `tenumeration` VALUES ('2', '故障类型', '整机故障', '2', '0', '0');
INSERT INTO `tenumeration` VALUES ('3', '市场', '武汉天然气', '1', '0', '0');
INSERT INTO `tenumeration` VALUES ('4', '市场', '宜昌', '2', '0', '0');
INSERT INTO `tenumeration` VALUES ('5', '故障详情', '测控板坏', '1', '无显', '0');
INSERT INTO `tenumeration` VALUES ('6', '故障详情', '电源插头线反', '2', '无显', '0');
INSERT INTO `tenumeration` VALUES ('7', '上电显示状态', '有显', '1', '0', '0');
INSERT INTO `tenumeration` VALUES ('8', '上电显示状态', '无显', '2', '0', '0');
INSERT INTO `tenumeration` VALUES ('9', '表具类型', '4-3BC', '1', '0', '0');
INSERT INTO `tenumeration` VALUES ('10', '表具类型', '3BC(优化)', '2', '0', '0');

-- ----------------------------
-- Table structure for `tmeteranalysisinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tmeteranalysisinfo`;
CREATE TABLE `tmeteranalysisinfo` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FMeterCode` varchar(14) DEFAULT '',
  `FMeterName` varchar(50) DEFAULT NULL,
  `FValveName` varchar(14) DEFAULT NULL,
  `FDepartment` varchar(50) DEFAULT NULL,
  `FAnalysor` varchar(50) DEFAULT NULL,
  `FCustomer` varchar(50) DEFAULT NULL,
  `FReportMisFune` varchar(50) DEFAULT NULL,
  `FConfirmMisFune` varchar(50) DEFAULT NULL,
  `FMisFuneDescrib` varchar(100) DEFAULT NULL,
  `FMeterReading` varchar(50) DEFAULT NULL,
  `FElecDisplay` varchar(50) DEFAULT NULL,
  `FDateTime` date DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tmeteranalysisinfo
-- ----------------------------
INSERT INTO `tmeteranalysisinfo` VALUES ('6', '1234567', '4-3BC', '1234567', '维修部', 'xiaozhujun', '宜昌', '无', '无显', '测控板坏', '23', '有显', '2017-09-22', '0');
INSERT INTO `tmeteranalysisinfo` VALUES ('7', '12345678', 'QK', '12345678', '维修部', 'xiaozhujun', '武汉天然气', '无显', '无显', '电源插头线反', '12334567', '有显', '2017-09-22', '0');
INSERT INTO `tmeteranalysisinfo` VALUES ('8', '123456', '4-3BC', '123456', '维修部', 'xiaozhujun', '武汉天然气', '无', '无显', '测控板坏', '12', '有显', '2017-09-23', '0');
INSERT INTO `tmeteranalysisinfo` VALUES ('9', '123456', '4-3BC', '123456', '外检部', 'test', '武汉天然气', '无', '无显', '测控板坏', '123', '有显', '2017-09-23', '1');
INSERT INTO `tmeteranalysisinfo` VALUES ('10', '123456', '4-3BC', '123456', '外检部', 'test', '武汉天然气', '无', '无显', '测控板坏', '123', '有显', '2017-09-23', '1');
INSERT INTO `tmeteranalysisinfo` VALUES ('11', '1234567', '4-3BC', '1234567', '外检部', 'test', '宜昌', '无', '无显', '测控板坏', '123', '无显', '2017-09-23', '0');

-- ----------------------------
-- Table structure for `treturnmeter`
-- ----------------------------
DROP TABLE IF EXISTS `treturnmeter`;
CREATE TABLE `treturnmeter` (
  `ID` bigint(50) NOT NULL AUTO_INCREMENT,
  `FReturnBatch` varchar(20) DEFAULT NULL,
  `FMeterCode` varchar(14) DEFAULT NULL,
  `FValveCode` varchar(14) DEFAULT NULL,
  `FCustomer` varchar(50) DEFAULT NULL,
  `FMeterName` varchar(50) DEFAULT NULL,
  `FMeterDirection` varchar(50) DEFAULT NULL,
  `FDateTime` date DEFAULT NULL,
  `FOperator` varchar(50) DEFAULT NULL,
  `FReportMisFune` varchar(50) DEFAULT NULL,
  `FProduceTime` date DEFAULT NULL,
  `FSaleTime` date DEFAULT NULL,
  `FPreMaintain` varchar(20) DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of treturnmeter
-- ----------------------------
INSERT INTO `treturnmeter` VALUES ('1', 'f2017092101', '1234567', '1234567', '宜昌', '4-3BC', '左', '2017-09-21', 'lilang', '无', '2015-08-01', '2016-09-01', null, '0');
INSERT INTO `treturnmeter` VALUES ('2', 'f2017092201', '12345678', '12345678', '武汉天然气', 'QK', '右', '2017-09-22', 'dengchao', '无', '2016-07-11', '2017-03-10', null, '0');
INSERT INTO `treturnmeter` VALUES ('3', 'f2017092301', '123456', '123456', '武汉天然气', '4-3BC', '左', '2017-09-23', 'dengchao', '无', '2015-11-12', '2015-12-10', null, '0');

-- ----------------------------
-- Table structure for `updaterecord`
-- ----------------------------
DROP TABLE IF EXISTS `updaterecord`;
CREATE TABLE `updaterecord` (
  `ID` int(50) NOT NULL AUTO_INCREMENT,
  `DataName` varchar(200) DEFAULT NULL,
  `UpdateDate` date DEFAULT NULL,
  `UpdateOperator` varchar(50) DEFAULT NULL,
  `UpdateName` varchar(50) DEFAULT NULL,
  `UpdateKey` varchar(100) DEFAULT NULL,
  `UpdateBefore` varchar(300) DEFAULT NULL,
  `UpdateAfter` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=940 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of updaterecord
-- ----------------------------
INSERT INTO `updaterecord` VALUES ('926', '上电显示状态', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '有显', '无显');
INSERT INTO `updaterecord` VALUES ('927', '故障详情', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '电源插头线反', '测控板坏');
INSERT INTO `updaterecord` VALUES ('928', '表具止码', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '1233', '123345');
INSERT INTO `updaterecord` VALUES ('929', '报修故障', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '无', '无显');
INSERT INTO `updaterecord` VALUES ('930', '表具编号', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '12345678', '1234567');
INSERT INTO `updaterecord` VALUES ('931', '上电显示状态', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '无显', '有显');
INSERT INTO `updaterecord` VALUES ('932', '市场', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '武汉天然气', '宜昌');
INSERT INTO `updaterecord` VALUES ('933', '表具止码', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '123345', '12334567');
INSERT INTO `updaterecord` VALUES ('934', '市场', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '宜昌', '武汉天然气');
INSERT INTO `updaterecord` VALUES ('935', '实查故障', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '无显', '整机故障');
INSERT INTO `updaterecord` VALUES ('936', '实查故障', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '整机故障', '无显');
INSERT INTO `updaterecord` VALUES ('937', '故障详情', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '测控板坏', '电源插头线反');
INSERT INTO `updaterecord` VALUES ('938', '表具编号', '2017-09-23', 'xiaozhujun', '维修分析表', '7', '1234567', '12345678');
INSERT INTO `updaterecord` VALUES ('939', '上电显示状态', '2017-09-23', 'xiaozhujun', '维修分析表', '11', '有显', '无显');

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'xiaozhujun', 'e10adc3949ba59abbe56e057f20f883e', '男', 'ROLE_USER;ROLE_ADMIN;ROLE_ANONYMOUS', '1', '启用', null, '1', '001', '小朱军');
INSERT INTO `user` VALUES ('2', 'zhangsan', '202cb962ac59075b964b07152d234b70', '男', 'ROLE_USER;ROLE_ANONYMOUS', '1', '启用', null, '2', '002', '张三');
INSERT INTO `user` VALUES ('3', 'sunhui', '202cb962ac59075b964b07152d234b70', '男', 'ROLE_USER', '1', '启用', null, '2', '001', '孙辉');
INSERT INTO `user` VALUES ('4', 'liujinxia', '202cb962ac59075b964b07152d234b70', '女', 'ROLE_ADMIN', '1', '启用', null, '3', '002', '刘金霞');
INSERT INTO `user` VALUES ('5', 'lilang', '202cb962ac59075b964b07152d234b70', '男', 'ROLE_USER', '1', '启用', null, '1', '003', 'li');
INSERT INTO `user` VALUES ('6', 'test', '202cb962ac59075b964b07152d234b70', '男', 'ROLE_USER', '1', '启用', null, '4', '002', 'sss');

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
INSERT INTO `user_authority` VALUES ('9', '6', '1', 'test', 'ROLE_USER');
