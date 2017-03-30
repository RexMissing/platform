/*
Navicat MySQL Data Transfer

Source Server         : DB
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : meterframemanagement

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-03-30 19:37:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_receive
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
-- Table structure for t_send
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
