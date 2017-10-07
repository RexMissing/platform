/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50173
Source Host           : 127.0.0.1:3306
Source Database       : datamanagement

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-09-28 21:44:16
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
INSERT INTO `tdepartment` VALUES ('2', '002', '生产部', '0');

-- ----------------------------
-- Table structure for `tenumeration`
-- ----------------------------
DROP TABLE IF EXISTS `tenumeration`;
CREATE TABLE `tenumeration` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FEnumType` varchar(50) DEFAULT NULL,
  `FEnumName` varchar(50) DEFAULT '',
  `FEnumValue` varchar(200) DEFAULT NULL,
  `FDescription` varchar(200) DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tenumeration
-- ----------------------------
INSERT INTO `tenumeration` VALUES ('1', '故障类型', '无显', '1', '0', '0');
INSERT INTO `tenumeration` VALUES ('2', '故障类型', '整机故障', '2', '0', '0');
INSERT INTO `tenumeration` VALUES ('3', '市场', '武汉天然气', '1', '0', '0');
INSERT INTO `tenumeration` VALUES ('4', '市场', '宜昌', '2', '0', '0');
INSERT INTO `tenumeration` VALUES ('5', '故障详情', '电路板烧坏', '1', '无显', '0');
INSERT INTO `tenumeration` VALUES ('6', '故障详情', '芯片损坏', '2', '无显', '0');
INSERT INTO `tenumeration` VALUES ('7', '上电显示状态', '显示', '1', '0', '0');
INSERT INTO `tenumeration` VALUES ('8', '上电显示状态', '无显示', '2', '0', '0');
INSERT INTO `tenumeration` VALUES ('9', '表具类型', '4-3BC', '1', '0', '0');
INSERT INTO `tenumeration` VALUES ('10', '表具类型', '4-2QK', '2', '0', '0');
INSERT INTO `tenumeration` VALUES ('11', '市场', '武钢江南燃气热力有限责任公司', '3', '0', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tmeteranalysisinfo
-- ----------------------------
INSERT INTO `tmeteranalysisinfo` VALUES ('1', '133140901754', '4-3B(C)(Y普外)', 'K111100000', '维修部', 'xiaozhujun', '武钢江南燃气热力有限责任公司', '无显', '无显', '电路板烧坏', '233', '显示', '2017-09-23', '0');
INSERT INTO `tmeteranalysisinfo` VALUES ('2', '133140901780', '4-3B(C)(Y普外)', 'K115022057', '生产部', 'liujinxia', '武钢江南燃气热力有限责任公司', '整机故障', '无显', '电路板烧坏', '222', '显示', '2017-09-23', '0');
INSERT INTO `tmeteranalysisinfo` VALUES ('3', '129150813532', '4-3B(C)(YII)', 'K115063256', '生产部', 'liujinxia', '武汉天然气', '无显', '无显', '电路板烧坏', '123', '显示', '2017-09-27', '0');

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
  `FMeterReading` varchar(20) DEFAULT NULL,
  `FProduceTime` date DEFAULT NULL,
  `FSaleTime` date DEFAULT NULL,
  `FPreMaintain` varchar(20) DEFAULT NULL,
  `IsDelete` int(20) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of treturnmeter
-- ----------------------------
INSERT INTO `treturnmeter` VALUES ('1', 'f2017092402', '133140901754', 'K111100000', '武钢江南燃气热力有限责任公司', '4-3B(C)(Y普外)', '左', '2017-09-06', 'da', '无显', null, '2017-09-12', '2017-09-21', 'yalong', '0');
INSERT INTO `treturnmeter` VALUES ('2', 'f2017092401', '129150813224', 'K115063362', '天燃气有限公司', '4-3B(C)(YII)', '右', '2017-09-22', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', 'xiaoqiang', '0');
INSERT INTO `treturnmeter` VALUES ('3', 'f2017092401', '129150811090', 'K115061383', '天燃气有限公司', '4-3B(C)(YII)', '右', '2017-09-22', 'xiaozhujun', '整机故障', null, '2015-08-01', '2017-09-20', 'liudeihua', '0');
INSERT INTO `treturnmeter` VALUES ('4', 'f2017092401', '133140901780', 'K115022057', '武钢江南燃气热力有限责任公司', '4-3B(C)(Y普外)', '左', '2017-09-22', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', 'xiaohua', '0');
INSERT INTO `treturnmeter` VALUES ('5', 'f2017092401', '133140901782', 'K115067446', '武钢江南燃气热力有限责任公司', '4-3B(C)(Y普外)', '左', '2017-09-22', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', 'lilang', '0');
INSERT INTO `treturnmeter` VALUES ('7', 'f2017092401', '136150700402', 'K115049053', '佛山市顺德区港华燃气有限公司', '4-3B(C)(YT1Q)', '右', '2017-09-23', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', 'lilang', '0');
INSERT INTO `treturnmeter` VALUES ('8', 'f2017092402', '129150813532', 'K115063256', '武汉天然气', '4-3B(C)(YII)', '右', '2017-09-23', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', 'lilang', '0');
INSERT INTO `treturnmeter` VALUES ('9', 'f2017092402', '129150813531', 'K115067550', '宜昌', '4-3B(C)(YII)', '右', '2017-09-24', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', 'lilang', '0');
INSERT INTO `treturnmeter` VALUES ('10', 'f2017092402', '136150700004', 'K115007398', '宜昌', '4-3B(C)(YT1Q)', '右', '2017-09-24', 'xiaozhujun', '无显', null, '2015-07-01', '2017-09-20', 'lilang', '0');
INSERT INTO `treturnmeter` VALUES ('11', 'f2017092402', '136150700375', 'K115008178', '武汉天然气', '4-3B(C)(YT1Q)', '右', '2017-09-24', 'xiaozhujun', '整机故障', null, '2015-07-01', '2017-09-20', 'lilang', '1');
INSERT INTO `treturnmeter` VALUES ('12', 'f2017092402', '136150700306', 'K115061544', '武钢江南燃气热力有限责任公司', '4-3B(C)(YT1Q)', '右', '2017-09-24', 'xiaozhujun', '无显', null, '2015-07-01', '2017-09-20', 'lilang', '1');
INSERT INTO `treturnmeter` VALUES ('13', 'f2017092402', '123213541', '13412341234', '宜昌', '4-3B(C)(YII)', '左', '2017-09-25', 'dfa', 'ww', null, null, null, null, '0');
INSERT INTO `treturnmeter` VALUES ('14', 'f2017092701', '129150802887', 'K115065953', '宜昌', '4-3B(C)(YII)', '右', '2017-09-27', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', 'lilang', '0');
INSERT INTO `treturnmeter` VALUES ('15', 'f2017092702', '136150701829', 'K115007519', '宜昌', '4-3B(C)(YT1Q)', '右', '2017-09-27', 'xiaozhujun', '无显', null, '2015-07-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('16', 'f2017092702', '129150819333', 'K115049896', '武汉天然气', '4-3B(C)(YII)', '右', '2017-09-27', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('17', 'f2017092703', '129150819333', 'K115049896', '武汉天然气', '4-3B(C)(YII)', '右', '2017-09-27', 'xiaozhujun', '无', null, '2015-08-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('18', 'f2017092703', '136150701829', 'K115007519', '武钢江南燃气热力有限责任公司', '4-3B(C)(YT1Q)', '右', '2017-09-27', 'xiaozhujun', '无显', null, '2015-07-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('19', 'f2017092703', '129150819405', 'K115061250', '宜昌', '4-3B(C)(YII)', '右', '2017-09-27', 'xiaozhujun', '整机故障', null, '2015-08-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('20', 'f2017092704', '129150819405', 'K115061250', '武汉天然气', '4-3B(C)(YII)', '右', '2017-09-27', 'xiaozhujun', '无显', null, '2015-08-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('21', 'f2017092801', '136150701829', 'K115007519', '武汉天然气', '4-3B(C)(YT1Q)', '右', '2017-09-28', 'xiaozhujun', '无', null, '2015-07-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('22', 'f2017092801', '136150701829', 'K115007519', '武汉天然气', '4-3B(C)(YT1Q)', '右', '2017-09-28', 'xiaozhujun', '无显', null, '2015-07-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('23', 'f2017092801', '136150701829', 'K115007519', '武汉天然气', '4-3B(C)(YT1Q)', '右', '2017-09-28', 'xiaozhujun', '无显', null, '2015-07-01', '2017-09-20', null, '0');
INSERT INTO `treturnmeter` VALUES ('24', 'f2017092802', '129150819333', 'K115049896', '武汉天然气', '4-3B(C)(YII)', '右', '2017-09-28', 'xiaozhujun', '整机故障', '333', '2015-08-01', '2017-09-20', '小明', '0');
INSERT INTO `treturnmeter` VALUES ('25', 'f2017092802', '133140901754', 'K014073258', '武钢江南燃气热力有限责任公司', '4-3B(C)(Y普外)', '左', '2017-09-28', 'xiaozhujun', '整机故障', '222', '2014-09-01', '2017-09-20', '小花', '0');
INSERT INTO `treturnmeter` VALUES ('26', 'f2017092803', '136150701829', 'K115007519', '宜昌', '4-3B(C)(YT1Q)', '右', '2017-09-28', 'xiaozhujun', '无显', '555', '2015-07-01', '2017-09-20', '小牛', '0');
INSERT INTO `treturnmeter` VALUES ('27', 'f2017092804', '133140901754', 'K014073258', '武钢江南燃气热力有限责任公司', '4-3B(C)(Y普外)', '左', '2017-09-28', 'xiaozhujun', '整机故障', '222', '2014-09-01', '2014-10-09', '小花', '0');
INSERT INTO `treturnmeter` VALUES ('28', 'f2017092805', '129150819333', 'K115049896', '宜昌', '4-3B(C)(YII)', '右', '2017-09-28', 'xiaozhujun', '整机故障', '333', '2015-08-01', '2015-09-28', '小明', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of updaterecord
-- ----------------------------
INSERT INTO `updaterecord` VALUES ('1', '枚举值', '2017-08-21', '李浪', '枚举表', '3-2B', '4', '1');
INSERT INTO `updaterecord` VALUES ('3', '表具止码', '2017-08-21', '李浪', '维修分析表', '1', '323', '325');
INSERT INTO `updaterecord` VALUES ('4', '录入人员', '2017-09-12', 'lilang', '公司返修表', '2', '李四', 'zhangsan');
INSERT INTO `updaterecord` VALUES ('5', '分析人员', '2017-09-17', '李浪', '维修分析表', '3', 'wer', '小花');
INSERT INTO `updaterecord` VALUES ('6', '录入时间', '2017-09-11', '小哈', '公司返修表', '6', '2016-4-5', '2017-09-16T16:00:00.000Z');
INSERT INTO `updaterecord` VALUES ('7', null, '2017-09-22', 'xiaozhujun', '返修表录入表', '5', null, null);
INSERT INTO `updaterecord` VALUES ('8', null, '2017-09-22', 'xiaozhujun', '返修表录入表', '4', null, null);
INSERT INTO `updaterecord` VALUES ('9', null, '2017-09-22', 'xiaozhujun', '返修表录入表', '5', null, null);
INSERT INTO `updaterecord` VALUES ('10', null, '2017-09-22', 'xiaozhujun', '返修表录入表', '5', null, null);
INSERT INTO `updaterecord` VALUES ('11', null, '2017-09-22', 'xiaozhujun', '返修表录入表', '5', null, null);
INSERT INTO `updaterecord` VALUES ('12', '现场维修员', '2017-09-22', 'xiaozhujun', '返修表录入表', '5', 'dev', 'de');
INSERT INTO `updaterecord` VALUES ('13', '表具编号', '2017-09-23', 'xiaozhujun', '返修表录入表', '5', '129150819379', '133140901782');
INSERT INTO `updaterecord` VALUES ('14', '表具编号', '2017-09-23', 'xiaozhujun', '返修表录入表', '5', '129150819379', '133140901782');
INSERT INTO `updaterecord` VALUES ('15', '表具编号', '2017-09-23', 'xiaozhujun', '返修表录入表', '5', '129150819379', '133140901782');
INSERT INTO `updaterecord` VALUES ('16', '表具编号', '2017-09-23', 'xiaozhujun', '返修表录入表', '5', '129150819379', '133140901782');
INSERT INTO `updaterecord` VALUES ('17', '表具编号', '2017-09-23', 'xiaozhujun', '返修表录入表', '5', '129150819379', '133140901782');
INSERT INTO `updaterecord` VALUES ('18', '表具编号', '2017-09-23', 'xiaozhujun', '返修表录入表', '4', '129150810905', '133140901780');
INSERT INTO `updaterecord` VALUES ('19', '现场维修员', null, 'xiaozhujun', '返修表录入表', '5', 'de', 'lilang');
INSERT INTO `updaterecord` VALUES ('20', '报修故障', null, 'xiaozhujun', '返修表录入表', '5', '整机故障', '无显');
INSERT INTO `updaterecord` VALUES ('21', '表具编号', null, 'xiaozhujun', '返修表录入表', '1', '111111111', '133140901754');
INSERT INTO `updaterecord` VALUES ('22', '现场维修员', null, 'xiaozhujun', '返修表录入表', '2', 'lilang', 'xiaoqiang');
INSERT INTO `updaterecord` VALUES ('23', '现场维修员', '2017-09-23', 'xiaozhujun', '返修表录入表', '3', 'lilang', 'liudeihua');
INSERT INTO `updaterecord` VALUES ('24', '部门名称', '2017-09-23', 'xiaozhujun', '部门管理表', '2', '外检部', '生产部');
INSERT INTO `updaterecord` VALUES ('25', '枚举描述', '2017-09-23', 'xiaozhujun', '枚举表', '6', '整机故障', '无显');
INSERT INTO `updaterecord` VALUES ('26', '表具编号', '2017-09-23', 'xiaozhujun', '返修表录入表', '7', '129150813529', '136150700402');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

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
