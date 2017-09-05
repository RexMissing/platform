/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50173
Source Host           : 127.0.0.1:3306
Source Database       : datamanagement

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-09-05 20:46:19
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
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `FDepartNo` varchar(50) DEFAULT '',
  `FDepartName` varchar(50) DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tdepartment
-- ----------------------------
INSERT INTO `tdepartment` VALUES ('1', '001', '维修部', '0');
INSERT INTO `tdepartment` VALUES ('2', '002', '外检部', '0');
INSERT INTO `tdepartment` VALUES ('3', '003', '003', '0');
INSERT INTO `tdepartment` VALUES ('4', '004', 'swe', '0');
INSERT INTO `tdepartment` VALUES ('5', '005', 'SK', '0');
INSERT INTO `tdepartment` VALUES ('6', '006', 'ASDA', '0');
INSERT INTO `tdepartment` VALUES ('7', '007', 'WE', '0');
INSERT INTO `tdepartment` VALUES ('8', '008', 'ASD', '0');
INSERT INTO `tdepartment` VALUES ('9', '009', 'ER', '0');
INSERT INTO `tdepartment` VALUES ('10', '010', 'QWE', '0');
INSERT INTO `tdepartment` VALUES ('11', '011', 'SAD', '0');

-- ----------------------------
-- Table structure for `tenumeration`
-- ----------------------------
DROP TABLE IF EXISTS `tenumeration`;
CREATE TABLE `tenumeration` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FEnumType` varchar(50) DEFAULT NULL,
  `FEnumName` varchar(50) DEFAULT '',
  `FEnumValue` int(200) DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tenumeration
-- ----------------------------
INSERT INTO `tenumeration` VALUES ('1', '表具类型', '3-2B', '1', '0');
INSERT INTO `tenumeration` VALUES ('2', '表具类型', '4-3C', '2', '0');
INSERT INTO `tenumeration` VALUES ('3', '表具类型', '4-5C', '3', '1');
INSERT INTO `tenumeration` VALUES ('4', '故障', '不走字', '1', '0');
INSERT INTO `tenumeration` VALUES ('5', '故障', '整机故障', '4', '0');
INSERT INTO `tenumeration` VALUES ('6', '故障', '无显示', '2', '0');
INSERT INTO `tenumeration` VALUES ('7', '故障', '阀门关闭', '3', '0');
INSERT INTO `tenumeration` VALUES ('9', '表具类型', '4-2F', '3', '0');
INSERT INTO `tenumeration` VALUES ('10', '内漏检测故障', '内漏', '1', '1');
INSERT INTO `tenumeration` VALUES ('11', '整机装配故障', '外壳损坏', '1', '0');

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
  `FReportMisFune` int(50) DEFAULT NULL,
  `FConfirmMisFune` int(50) DEFAULT NULL,
  `FMeterReading` varchar(50) DEFAULT NULL,
  `FDateTime` date DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tmeteranalysisinfo
-- ----------------------------
INSERT INTO `tmeteranalysisinfo` VALUES ('1', '151150700757', '4-3B(QK1)(江南)', 'Z015012193', '001', '吕亚龙', '宜昌焦化煤气公司', '4', '2', '344', '2017-07-20', '0');
INSERT INTO `tmeteranalysisinfo` VALUES ('2', '23411', 'wer', '324', '002', 'wre', 'wer', '1', '3', '236', '2017-08-22', '1');
INSERT INTO `tmeteranalysisinfo` VALUES ('3', '13212', 'ff', '22343', '001', 'er', 'w', '4', '1', '23', '2017-09-12', '0');
INSERT INTO `tmeteranalysisinfo` VALUES ('4', '123123', 'qwe', '21314', '002', 'wer', 'fwd', '4', '1', '123', '2017-09-03', '0');
INSERT INTO `tmeteranalysisinfo` VALUES ('5', '129150813223', '4-3B(C)(YII)', 'K115061365', '001', '李浪', '武汉天然气', '4', '2', '122', '2017-09-25', '0');

-- ----------------------------
-- Table structure for `treturnmeter`
-- ----------------------------
DROP TABLE IF EXISTS `treturnmeter`;
CREATE TABLE `treturnmeter` (
  `ID` bigint(50) NOT NULL AUTO_INCREMENT,
  `FMeterCode` varchar(14) DEFAULT NULL,
  `FCustomer` varchar(50) DEFAULT NULL,
  `FMeterName` varchar(50) DEFAULT NULL,
  `FQuantity` int(200) DEFAULT NULL,
  `FRInvoNo` varchar(50) DEFAULT '0',
  `FDateTime` date DEFAULT NULL,
  `FOperator` varchar(50) DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treturnmeter
-- ----------------------------
INSERT INTO `treturnmeter` VALUES ('1', '123124234', 'wer', '4-3C', '4', '2345555', '2017-02-24', '张三', '0');
INSERT INTO `treturnmeter` VALUES ('2', '15110700859', '武汉天然气', '4-3B(QK2)', '4', '0', '2017-05-20', '李四', '0');
INSERT INTO `treturnmeter` VALUES ('3', '151150700757', '宜昌焦化煤气公司', '4-3B(QK1)(江南)', '4', '10001', '2017-07-18', '邓超', '0');
INSERT INTO `treturnmeter` VALUES ('4', '157170103750', 'sd', 'fsd', '4', '10002', '2017-08-01', '亚龙', '1');
INSERT INTO `treturnmeter` VALUES ('5', '157170103751', '深圳天然气', '5-2D', '4', '0', '2017-07-05', '李浪', '0');
INSERT INTO `treturnmeter` VALUES ('6', '123124342', 'ewr', '424', '3', '0', '2017-05-21', 'lilangh', '0');
INSERT INTO `treturnmeter` VALUES ('12', '157170103507', '天燃气有限公司', '4-3B(C3)', '2', '2314214', '2017-08-30', 'reewr', '1');
INSERT INTO `treturnmeter` VALUES ('13', '157170103507', '天燃气有限公司', '4-3B(C3)', '4', '46789', '2017-08-21', 'ggj', '1');
INSERT INTO `treturnmeter` VALUES ('14', '157170103507', '天燃气有限公司', '4-3B(C3)', '4', '46789', '2017-08-21', 'ggj', '1');
INSERT INTO `treturnmeter` VALUES ('15', '157170103507', '天燃气有限公司', '4-3B(C3)', '5', '12344', '2017-08-28', 'lgsv', '1');
INSERT INTO `treturnmeter` VALUES ('16', '157170103506', '天燃气有限公司', null, '2', '1234', '2017-08-29', 'lilang', '0');
INSERT INTO `treturnmeter` VALUES ('17', '157170103507', '天燃气有限公司', '4-3B(C3)', '4', '887799', '2017-08-29', 'adas', '0');
INSERT INTO `treturnmeter` VALUES ('18', '157170103400', '天燃气有限公司', null, '6', '99999', '2017-08-15', 'rrr', '0');
INSERT INTO `treturnmeter` VALUES ('19', '157170102246', '天燃气有限公司', null, '5', '21334', '2017-08-28', 'qeq', '0');
INSERT INTO `treturnmeter` VALUES ('20', '157170103391', '天燃气有限公司', null, '4', '0', '2017-08-14', 'qew', '0');
INSERT INTO `treturnmeter` VALUES ('21', '157170102132', '天燃气有限公司', null, '1', '6666', '2017-08-28', 'qwe', '0');

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
INSERT INTO `treturnmeterinfo` VALUES ('15110700859', '武汉天然气', '4-3B(QK2)', '4', '2017-05-20', '李四');
INSERT INTO `treturnmeterinfo` VALUES ('151150700757', '宜昌焦化煤气公司', '4-3B(QK1)(江南)', '4', '2017-07-05', '邓超');
INSERT INTO `treturnmeterinfo` VALUES ('157170103750', '深圳天然气', '4-2B', '6', '2016-03-14', '吕亚龙');

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
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of updaterecord
-- ----------------------------
INSERT INTO `updaterecord` VALUES ('1', '枚举值', '2017-08-21', '李浪', '枚举值列表', '3-2B', '4', '1');
INSERT INTO `updaterecord` VALUES ('3', '表具止码', '2017-08-21', '李浪', '表具维修分析', '15110700757', '323', '325');

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
INSERT INTO `user` VALUES ('3', 'sunhui', '202cb962ac59075b964b07152d234b70', '男', 'ROLE_USER', '1', '启用', null, '2', '001', '孙辉');
INSERT INTO `user` VALUES ('4', 'liujinxia', '202cb962ac59075b964b07152d234b70', '女', 'ROLE_ADMIN', '1', '启用', null, '3', '002', '刘金霞');
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
