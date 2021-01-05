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

 Date: 05/01/2021 17:09:00
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
  `start_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `end_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `conference_no` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会议号码',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `rule_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_conference_no`(`conference_no`) USING BTREE,
  INDEX `fk_conference_conference_rule`(`rule_id`) USING BTREE,
  CONSTRAINT `fk_conference_conference_rule` FOREIGN KEY (`rule_id`) REFERENCES `conference_rule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会议预约信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for conference_participant
-- ----------------------------
DROP TABLE IF EXISTS `conference_participant`;
CREATE TABLE `conference_participant`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `conference_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conference表主键',
  `participant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参会者id',
  `status` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_conference_plan_id`(`conference_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会议预约计划参会者信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for conference_rule
-- ----------------------------
DROP TABLE IF EXISTS `conference_rule`;
CREATE TABLE `conference_rule`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `cycle_typle` smallint(6) NULL DEFAULT NULL COMMENT '周期类型：\n0：单次\n1：按天\n2：按周\n30：按月（按天），31：按月（按周）\n40：按年（按天），41：按年（按月）',
  `gap` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `start_day` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `end_day` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '结束日期',
  `create_time` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改时间',
  `week` tinyint(4) NULL DEFAULT NULL COMMENT '一周中的具体日期',
  `day` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '天数',
  `ordinal_week` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `ordinal_month` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '月份序数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
