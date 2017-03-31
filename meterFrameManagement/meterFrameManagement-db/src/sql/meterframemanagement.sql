/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : meterframemanagement

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2017-03-31 12:43:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_framekey`
-- ----------------------------
DROP TABLE IF EXISTS `t_framekey`;
CREATE TABLE `t_framekey` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `meterID` varchar(255) NOT NULL,
  `oldKey` varchar(255) NOT NULL,
  `newKey` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_framekey
-- ----------------------------
INSERT INTO `t_framekey` VALUES ('1', '0120151212163', '640836FBC4A6FD68431AE03CF44C0232', '640836FBC4A6FD68431AE03CF44C0232');

-- ----------------------------
-- Table structure for `t_receive`
-- ----------------------------
DROP TABLE IF EXISTS `t_receive`;
CREATE TABLE `t_receive` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `meterID` varchar(255) NOT NULL,
  `receiveFrame` varchar(255) NOT NULL,
  `receiveDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_receive
-- ----------------------------

-- ----------------------------
-- Table structure for `t_send`
-- ----------------------------
DROP TABLE IF EXISTS `t_send`;
CREATE TABLE `t_send` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `meterID` varchar(255) NOT NULL,
  `funCode` int(11) NOT NULL,
  `frameID` int(11) NOT NULL,
  `sendFrame` varchar(255) NOT NULL,
  `sendDate` datetime NOT NULL,
  `sent` bit(1) NOT NULL,
  `sentDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_send
-- ----------------------------
