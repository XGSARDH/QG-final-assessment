/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : qg_fund_management_system

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 26/04/2024 23:53:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fund_changes
-- ----------------------------
DROP TABLE IF EXISTS `fund_changes`;
CREATE TABLE `fund_changes`  (
  `fund_change_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '变动ID, 主键',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '操作用户ID, 当时执行操作的用户id',
  `active_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '付款对象类型, 用户  /  群组',
  `active_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '付款对象ID , 用户id /  群组id',
  `passive_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '收款对方类型, 用户  /  群组',
  `passive_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '收款对象ID, 用户id /  群组id',
  `change_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '变动类型, 例如：分配资金、收回资金、成功转出、成功转入、正在转入、正在转出、交易撤回、交易被终止',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '简单描述',
  `amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '变动金额',
  `order_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '对应订单id',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`fund_change_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fund_changes
-- ----------------------------

-- ----------------------------
-- Table structure for group_members
-- ----------------------------
DROP TABLE IF EXISTS `group_members`;
CREATE TABLE `group_members`  (
  `member_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '成员ID，主键',
  `group_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '群组ID',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '用户ID',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '用户在群组中的角色, RBAC',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`member_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10013 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_members
-- ----------------------------
INSERT INTO `group_members` VALUES (10010, 10021, 10023, 'GROUP_HIGHEST', '2024-04-26 23:06:56', '2024-04-26 23:06:56');
INSERT INTO `group_members` VALUES (10011, 10022, 10023, 'GROUP_HIGHEST', '2024-04-26 23:08:36', '2024-04-26 23:08:36');
INSERT INTO `group_members` VALUES (10012, 10023, 10023, 'GROUP_HIGHEST', '2024-04-26 23:13:06', '2024-04-26 23:13:06');

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups`  (
  `group_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '群组ID，主键',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '群组名称，索引',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL COMMENT '描述',
  `is_public` tinyint(1) NULL DEFAULT NULL COMMENT '是否公开，1为公开，0为不公开',
  `created_by` bigint UNSIGNED NULL DEFAULT NULL COMMENT '创建者ID，索引',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`group_id`) USING BTREE,
  INDEX `created_by`(`created_by` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10024 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of groups
-- ----------------------------
INSERT INTO `groups` VALUES (10023, '默认名', NULL, 1, 10023, '2024-04-26 23:13:06', '2024-04-26 23:13:06');

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`  (
  `notification_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '通知ID，主键',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '用户ID (`users.user_id`)',
  `group_id` bigint UNSIGNED NOT NULL COMMENT '群组ID (`groups.group_id`), 可为空',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '通知标题',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL COMMENT '通知内容',
  `read_status` tinyint(1) NULL DEFAULT NULL COMMENT '阅读状态（0未读，1已读）',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`notification_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notifications
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID, 主键',
  `active_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '付款对象类型 ',
  `active_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '付款对象ID',
  `passive_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '收款对方类型',
  `passive_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '收款对象ID',
  `change_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '变动类型, 例如：分配资金、收回资金、成功转出、成功转入、正在转入、正在转出、交易撤回、交易被终止',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '简单描述',
  `amount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '变动金额',
  `gmt_create` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for permission_changes
-- ----------------------------
DROP TABLE IF EXISTS `permission_changes`;
CREATE TABLE `permission_changes`  (
  `audit_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '审计ID，主键',
  `action_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '行动类型（如：加入群组、权限修改、退出群组）',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '行动处理情况',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '操作用户ID(当时执行操作的用户id)',
  `group_id` bigint UNSIGNED NULL DEFAULT 0 COMMENT '发生变动的群组id (创建群组时默认为0)',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL COMMENT '操作描述',
  `active_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '操作对象类型 (`用户 `/ `群组`)',
  `active_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '操作对象ID (`用户id `/ `群组id`)',
  `passive_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '受影响对方类型 (`用户 `/ `群组`)',
  `passive_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '受影响对象ID (`用户id `/ `群组id`)',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`audit_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10029 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_changes
-- ----------------------------
INSERT INTO `permission_changes` VALUES (10028, 'CreateGroup', 1, 10023, 10023, NULL, NULL, 0, NULL, 0, '2024-04-26 23:12:59', '2024-04-26 23:13:06');

-- ----------------------------
-- Table structure for user_funds
-- ----------------------------
DROP TABLE IF EXISTS `user_funds`;
CREATE TABLE `user_funds`  (
  `user_fund_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '账户ID, 主键',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '用户ID,id为0时时代表群组账户资金',
  `user_health` tinyint(1) NOT NULL COMMENT '个人信用等级, 等级为0时不可进行任何资金操作, 等级为1时可以资金操作',
  `group_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '可支配的群组的ID, id为0时代表个人账户资金',
  `total_funds` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '用户所在群组的总资金',
  `available_funds` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '用户所在群组的可用资金',
  `frozen_funds` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '用户所在群组的冻结资金',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_fund_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10019 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_funds
-- ----------------------------
INSERT INTO `user_funds` VALUES (10011, 10023, 1, 0, '10000', '10000', '0', '2024-04-26 23:05:49', '2024-04-26 23:05:49');
INSERT INTO `user_funds` VALUES (10012, 10024, 1, 0, '10000', '10000', '0', '2024-04-26 23:05:55', '2024-04-26 23:05:55');
INSERT INTO `user_funds` VALUES (10013, 10025, 1, 0, '10000', '10000', '0', '2024-04-26 23:06:00', '2024-04-26 23:06:00');
INSERT INTO `user_funds` VALUES (10014, 10026, 1, 0, '10000', '10000', '0', '2024-04-26 23:06:05', '2024-04-26 23:06:05');
INSERT INTO `user_funds` VALUES (10017, 0, 1, 10023, '10000', '10000', '0', '2024-04-26 23:13:06', '2024-04-26 23:13:06');
INSERT INTO `user_funds` VALUES (10018, 10023, 1, 0, '10000', '10000', '0', '2024-04-26 23:13:06', '2024-04-26 23:13:06');

-- ----------------------------
-- Table structure for user_sessions
-- ----------------------------
DROP TABLE IF EXISTS `user_sessions`;
CREATE TABLE `user_sessions`  (
  `session_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '用户ID',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '登录IP地址',
  `device_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '设备信息',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '会话令牌，用于身份验证和会话保持',
  `expires_at` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '会话过期时间',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`session_id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10011 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_sessions
-- ----------------------------
INSERT INTO `user_sessions` VALUES (10007, 10023, NULL, NULL, '', NULL, '2024-04-26 23:05:49', '2024-04-26 23:05:49');
INSERT INTO `user_sessions` VALUES (10008, 10024, NULL, NULL, '', NULL, '2024-04-26 23:05:55', '2024-04-26 23:05:55');
INSERT INTO `user_sessions` VALUES (10009, 10025, NULL, NULL, '', NULL, '2024-04-26 23:06:00', '2024-04-26 23:06:00');
INSERT INTO `user_sessions` VALUES (10010, 10026, NULL, NULL, '', NULL, '2024-04-26 23:06:05', '2024-04-26 23:06:05');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键,  唯一',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '用户名，索引',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '昵称',
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '加密密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '电子邮件地址,',
  `phone_number` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NOT NULL COMMENT '电话号码',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '头像URL',
  `now_create_group` int NULL DEFAULT 0 COMMENT '现在已创建群组数',
  `max_create_group` int NULL DEFAULT 3 COMMENT '最大创建群组数量',
  `gmt_create` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `phone_number`(`phone_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10027 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_as_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '1', '0', '1', '0', '0', '0', 0, 3, '2024-04-26 20:36:48', '2024-04-26 20:36:51');
INSERT INTO `users` VALUES (10023, '1', '1', '1', '1', '1', '0', 1, 3, '2024-04-26 23:05:49', '2024-04-26 23:05:49');
INSERT INTO `users` VALUES (10024, '2', '2', '2', '2', '2', '0', 0, 3, '2024-04-26 23:05:55', '2024-04-26 23:05:55');
INSERT INTO `users` VALUES (10025, '3', '3', '3', '3', '3', '0', 0, 3, '2024-04-26 23:06:00', '2024-04-26 23:06:00');
INSERT INTO `users` VALUES (10026, '4', '4', '4', '4', '4', '0', 0, 3, '2024-04-26 23:06:05', '2024-04-26 23:06:05');

SET FOREIGN_KEY_CHECKS = 1;
