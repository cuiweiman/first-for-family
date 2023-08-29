/*
 Navicat Premium Data Transfer

 Source Server         : master-3306
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 02/08/2023 13:43:35
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`    bigint unsigned NOT NULL AUTO_INCREMENT,
    `uname` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` (`id`, `uname`)
VALUES (1, 'zhangsan');
INSERT INTO `t_user` (`id`, `uname`)
VALUES (2, '@@hostname');
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
