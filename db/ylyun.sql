/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : ylyun

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 15/01/2021 15:27:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `staff_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工id',
  `enterprise_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_account_staff`(`staff_id`) USING BTREE,
  INDEX `fk_account_enterprise`(`enterprise_id`) USING BTREE,
  CONSTRAINT `fk_account_enterprise` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_account_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('d3fc87da4f1a11ebb2f7e0d55ed6780a', 'bossOfTest', '123456', 1609826233150, 1609826233171, 'd3feb3724f1a11ebb2f7e0d55ed6780a', NULL);

-- ----------------------------
-- Table structure for conference
-- ----------------------------
DROP TABLE IF EXISTS `conference`;
CREATE TABLE `conference`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `start_time` time(0) NULL DEFAULT NULL,
  `end_time` time(0) NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `conference_no` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会议号码',
  `conference_room` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `rule_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_conference_no`(`conference_no`) USING BTREE,
  INDEX `fk_conference_conference_rule`(`rule_id`) USING BTREE,
  CONSTRAINT `fk_conference_conference_rule` FOREIGN KEY (`rule_id`) REFERENCES `conference_rule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会议预约信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of conference
-- ----------------------------
INSERT INTO `conference` VALUES ('2096082c518011eb9b67e0d55ed6780a', '11:00:00', '13:00:00', '123123', '123123', NULL, 1610089633486, 1610089633486, '2094cc21518011eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('20a2aab6518011eb9b67e0d55ed6780a', '10:00:00', '12:00:00', '123123', '1231234', NULL, 1610089633569, 1610089633569, '20a267d6518011eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('2264a49953a811eba0cce0d55ed6780a', '11:00:00', '13:00:00', '123123', '123123', NULL, 1610326723258, 1610326723258, '2262d12553a811eba0cce0d55ed6780a');
INSERT INTO `conference` VALUES ('2269c30553a811eba0cce0d55ed6780a', '10:00:00', '12:00:00', '123123', '1231234', NULL, 1610326723292, 1610326723292, '226985fd53a811eba0cce0d55ed6780a');
INSERT INTO `conference` VALUES ('4dbc0a2b519611eb9b67e0d55ed6780a', '11:00:00', '13:00:00', '123123', '123123', NULL, 1610099158219, 1610099158219, '4dba97c4519611eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('4dc949c0519611eb9b67e0d55ed6780a', '10:00:00', '12:00:00', '123123', '1231234', NULL, 1610099158305, 1610099158305, '4dc8328d519611eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('a73e0c5d519511eb9b67e0d55ed6780a', '11:00:00', '13:00:00', '123123', '123123', NULL, 1610098878884, 1610098878884, 'a73aeede519511eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('a772852c519511eb9b67e0d55ed6780a', '10:00:00', '12:00:00', '123123', '1231234', NULL, 1610098879228, 1610098879228, 'a77194ca519511eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('ac7b65ff519611eb9b67e0d55ed6780a', '11:00:00', '13:00:00', '123123', '123123', NULL, 1610099317183, 1610099317183, 'ac7a2f8f519611eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('ac8709b8519611eb9b67e0d55ed6780a', '10:00:00', '12:00:00', '123123', '1231234', NULL, 1610099317260, 1610099317260, 'ac86c2cf519611eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('b95735fb516111eb9b67e0d55ed6780a', '11:00:00', '13:00:00', '123123', '123123', NULL, 1610076575271, 1610076575271, 'b9554d1e516111eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('b96273d2516111eb9b67e0d55ed6780a', '10:00:00', '12:00:00', '123123', '1231234', NULL, 1610076575345, 1610076575345, 'b9622ea4516111eb9b67e0d55ed6780a');
INSERT INTO `conference` VALUES ('ce1e97da53dc11eba0cce0d55ed6780a', '11:00:00', '13:00:00', '123123', '123123', NULL, 1610349345176, 1610349345176, 'ce1d4cc353dc11eba0cce0d55ed6780a');
INSERT INTO `conference` VALUES ('ce2711fc53dc11eba0cce0d55ed6780a', '10:00:00', '12:00:00', '123123', '1231234', NULL, 1610349345232, 1610349345232, 'ce26c7ff53dc11eba0cce0d55ed6780a');

-- ----------------------------
-- Table structure for conference_participant
-- ----------------------------
DROP TABLE IF EXISTS `conference_participant`;
CREATE TABLE `conference_participant`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `conference_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conference表主键',
  `participant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参会者id',
  `status` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '0：未知，1：不在会议中，2：在此会议中',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `role` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '0：未知，1：创建者，2：管理员，3：参会人员',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_conference_plan_id`(`conference_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会议预约计划参会者信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for conference_rule
-- ----------------------------
DROP TABLE IF EXISTS `conference_rule`;
CREATE TABLE `conference_rule`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `type` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `gap` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `start_day` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `end_day` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '结束日期',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改时间',
  `week` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `day` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '天数',
  `ordinal_week` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `ordinal_month` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '月份序数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of conference_rule
-- ----------------------------
INSERT INTO `conference_rule` VALUES ('2094cc21518011eb9b67e0d55ed6780a', 1, 1, 1610035200000, 1610899200000, 0, 0, NULL, 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('20a267d6518011eb9b67e0d55ed6780a', 2, 1, 1610035200000, 1612108800000, 0, 0, 'thursday,saturday', 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('2262d12553a811eba0cce0d55ed6780a', 1, 1, 1610035200000, 1610899200000, 0, 0, NULL, 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('226985fd53a811eba0cce0d55ed6780a', 2, 1, 1610035200000, 1612108800000, 0, 0, 'thursday,saturday', 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('4dba97c4519611eb9b67e0d55ed6780a', 1, 1, 1610035200000, 1610899200000, 0, 0, NULL, 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('4dc8328d519611eb9b67e0d55ed6780a', 2, 1, 1610035200000, 1612108800000, 0, 0, 'thursday,saturday', 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('a73aeede519511eb9b67e0d55ed6780a', 1, 1, 1610035200000, 1610899200000, 0, 0, NULL, 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('a77194ca519511eb9b67e0d55ed6780a', 2, 1, 1610035200000, 1612108800000, 0, 0, 'thursday,saturday', 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('ac7a2f8f519611eb9b67e0d55ed6780a', 1, 1, 1610035200000, 1610899200000, 0, 0, NULL, 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('ac86c2cf519611eb9b67e0d55ed6780a', 2, 1, 1610035200000, 1612108800000, 0, 0, 'thursday,saturday', 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('b9554d1e516111eb9b67e0d55ed6780a', 1, 1, 1610035200000, 1610899200000, 0, 0, NULL, 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('b9622ea4516111eb9b67e0d55ed6780a', 2, 1, 1610035200000, 1612108800000, 0, 0, 'thursday,saturday', 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('ce1d4cc353dc11eba0cce0d55ed6780a', 1, 1, 1610035200000, 1610899200000, 0, 0, NULL, 0, 0, 0);
INSERT INTO `conference_rule` VALUES ('ce26c7ff53dc11eba0cce0d55ed6780a', 2, 1, 1610035200000, 1612108800000, 0, 0, 'thursday,saturday', 0, 0, 0);

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `enterprise_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属企业id，enterprise表id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_department_enterprise`(`enterprise_id`) USING BTREE,
  CONSTRAINT `fk_department_enterprise` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织架构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('38119aa84f1e11ebb2f7e0d55ed6780a', 'e389aa504f1b11ebb2f7e0d55ed6780a', 'dep1', 1609827689539, 1609827689539, 'ffa88f814f1b11ebb2f7e0d55ed6780a');
INSERT INTO `department` VALUES ('4fa554d54f1e11ebb2f7e0d55ed6780a', 'e389aa504f1b11ebb2f7e0d55ed6780a', 'dep1.1', 1609827729095, 1609827729095, '38119aa84f1e11ebb2f7e0d55ed6780a');
INSERT INTO `department` VALUES ('6ff4abfa4f1e11ebb2f7e0d55ed6780a', 'e389aa504f1b11ebb2f7e0d55ed6780a', 'dep2', 1609827783302, 1609827783302, 'ffa88f814f1b11ebb2f7e0d55ed6780a');
INSERT INTO `department` VALUES ('ffa88f814f1b11ebb2f7e0d55ed6780a', 'e389aa504f1b11ebb2f7e0d55ed6780a', 'test', 1609826735909, 1609826735909, NULL);

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业名称',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_enterprise_no`(`no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'party子表-企业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enterprise
-- ----------------------------
INSERT INTO `enterprise` VALUES ('e389aa504f1b11ebb2f7e0d55ed6780a', '00001', 'test', 1609826688731, 1609826688731);

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `definition` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1566cd4c48b511eb9d1ae0d55ed6780a', '管理员', '公司的管理员，有更多的权限', 1609122821594, 1609123163162);
INSERT INTO `role` VALUES ('651dc79745af11eb995fe0d55ed6780a', '创建者', '公司的创建者', 1608790520276, 1608790520276);

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `enterprise_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属企业id，enterprise表id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `mobile` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'subject子表-员工信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('054023384f1e11ebb2f7e0d55ed6780a', 'e389aa504f1b11ebb2f7e0d55ed6780a', 'staff2', 1609827604281, 1609827604285, '2', '2@test.com', 1);
INSERT INTO `staff` VALUES ('1236608f4f1e11ebb2f7e0d55ed6780a', 'e389aa504f1b11ebb2f7e0d55ed6780a', 'staff3', 1609827626027, 1609827626032, '2.2', '1.1@test.com', 1);
INSERT INTO `staff` VALUES ('d3feb3724f1a11ebb2f7e0d55ed6780a', 'e389aa504f1b11ebb2f7e0d55ed6780a', 'boss', 1609826233165, 1609827452439, '0', 'boss@test.com', 1);
INSERT INTO `staff` VALUES ('f83063cf4f1d11ebb2f7e0d55ed6780a', 'e389aa504f1b11ebb2f7e0d55ed6780a', 'staff1', 1609827582367, 1609827582373, '1', '1@test.com', 1);

-- ----------------------------
-- Table structure for staff_department_relation
-- ----------------------------
DROP TABLE IF EXISTS `staff_department_relation`;
CREATE TABLE `staff_department_relation`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `staff_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'staff表id',
  `department_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'department表id',
  `position` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_staff_department_relation_staff`(`staff_id`) USING BTREE,
  INDEX `fk_staff_department_relation_department`(`department_id`) USING BTREE,
  CONSTRAINT `fk_staff_department_relation_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_staff_department_relation_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff_department_relation
-- ----------------------------
INSERT INTO `staff_department_relation` VALUES ('cd971af44f1f11ebb2f7e0d55ed6780a', '054023384f1e11ebb2f7e0d55ed6780a', '6ff4abfa4f1e11ebb2f7e0d55ed6780a', 'leader', 1609828369894, 1609828369894);
INSERT INTO `staff_department_relation` VALUES ('f0e2a4774f1f11ebb2f7e0d55ed6780a', 'f83063cf4f1d11ebb2f7e0d55ed6780a', '38119aa84f1e11ebb2f7e0d55ed6780a', 'leader', 1609828429110, 1609828429110);
INSERT INTO `staff_department_relation` VALUES ('ffadcd534f1b11ebb2f7e0d55ed6780a', 'd3feb3724f1a11ebb2f7e0d55ed6780a', 'ffa88f814f1b11ebb2f7e0d55ed6780a', 'Boss', 1609826735944, 1609826735944);

-- ----------------------------
-- Table structure for staff_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `staff_role_relation`;
CREATE TABLE `staff_role_relation`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `staff_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工id，staff表id',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'role表id',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_staff_role_relation_role`(`role_id`) USING BTREE,
  INDEX `fk_staff_role_relation_staff`(`staff_id`) USING BTREE,
  CONSTRAINT `fk_staff_role_relation_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_staff_role_relation_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账号角色映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff_role_relation
-- ----------------------------
INSERT INTO `staff_role_relation` VALUES ('ff98c2ac4f1b11ebb2f7e0d55ed6780a', 'd3feb3724f1a11ebb2f7e0d55ed6780a', '651dc79745af11eb995fe0d55ed6780a', 1609826735806, 1609826735806);

SET FOREIGN_KEY_CHECKS = 1;
