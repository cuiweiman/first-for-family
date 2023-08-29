/*
 Navicat Premium Data Transfer

 Source Server         : Mac本地
 Source Server Type    : MySQL
 Source Server Version : 50731 (5.7.31)

 Target Server Type    : MySQL
 Target Server Version : 50731 (5.7.31)
 File Encoding         : 65001

 Date: 02/08/2023 16:54:05
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `order_no` varchar(64)    DEFAULT NULL,
    `user_id`  bigint(20) DEFAULT NULL,
    `amount`   decimal(12, 2) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`
(
    `id`       bigint(20) NOT NULL,
    `order_no` varchar(32)    DEFAULT NULL,
    `user_id`  bigint(20) DEFAULT NULL,
    `price`    decimal(12, 2) DEFAULT NULL,
    `number`   int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
-- ----------------------------
-- Records of t_order_item
-- ----------------------------


BEGIN;
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;
