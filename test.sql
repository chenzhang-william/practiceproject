/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 11/12/2020 14:06:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `accountid` int(10) UNSIGNED NOT NULL COMMENT '账号id',
  `password` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '账号密码',
  `accountname` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '账号昵称',
  `accountno` int(11) NULL DEFAULT NULL COMMENT '账号名',
  `mid` int(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`accountid`) USING BTREE,
  INDEX `fk_account_meeting`(`mid`) USING BTREE,
  CONSTRAINT `fk_account_meeting` FOREIGN KEY (`mid`) REFERENCES `meeting` (`mid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '账号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for companys
-- ----------------------------
DROP TABLE IF EXISTS `companys`;
CREATE TABLE `companys`  (
  `cid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cno` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '企业编号',
  `adminid` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`cid`) USING BTREE,
  INDEX `fk_companys_employees`(`adminid`) USING BTREE,
  CONSTRAINT `fk_companys_employees` FOREIGN KEY (`adminid`) REFERENCES `employees` (`eid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '企业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments`  (
  `dname` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '部门名称',
  `did` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pdid` int(10) UNSIGNED NOT NULL,
  `cid` int(10) UNSIGNED NOT NULL COMMENT '企业id',
  PRIMARY KEY (`did`) USING BTREE,
  INDEX `fk_departments_departments`(`pdid`) USING BTREE,
  INDEX `fk_departments_companys`(`cid`) USING BTREE,
  CONSTRAINT `fk_departments_companys` FOREIGN KEY (`cid`) REFERENCES `companys` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_departments_departments` FOREIGN KEY (`pdid`) REFERENCES `departments` (`did`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
  `eno` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `ename` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '员工姓名',
  `accountid` int(10) UNSIGNED NULL DEFAULT NULL,
  `cid` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '企业id',
  `did` int(10) UNSIGNED NULL DEFAULT NULL,
  `eid` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`eid`) USING BTREE,
  INDEX `fk_employees_departments`(`did`) USING BTREE,
  INDEX `fk_employees_account`(`accountid`) USING BTREE,
  INDEX `fk_employees_companys`(`cid`) USING BTREE,
  CONSTRAINT `fk_employees_account` FOREIGN KEY (`accountid`) REFERENCES `account` (`accountid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_companys` FOREIGN KEY (`cid`) REFERENCES `companys` (`cid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_employees_departments` FOREIGN KEY (`did`) REFERENCES `departments` (`did`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '员工表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for meeting
-- ----------------------------
DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting`  (
  `mno` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `mid` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `adminid` int(10) UNSIGNED NOT NULL COMMENT '管理者账号',
  `starttime` date NOT NULL COMMENT '开始时间',
  `endtime` date NOT NULL COMMENT '结束时间',
  `relatedaccount` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '相关账号',
  `inmaccount` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '会议中账号',
  PRIMARY KEY (`mid`) USING BTREE,
  INDEX `fk_meeting_account`(`adminid`) USING BTREE,
  CONSTRAINT `fk_meeting_account` FOREIGN KEY (`adminid`) REFERENCES `account` (`accountid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '会议' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
