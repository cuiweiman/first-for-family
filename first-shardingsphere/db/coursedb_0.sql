/*
 Source Schema         : coursedb_0
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_0
-- ----------------------------
DROP TABLE IF EXISTS `course_0`;
CREATE TABLE `course_0`
(
    `cid`      bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '课程 ID',
    `cname`    varchar(64) DEFAULT NULL,
    `user_id`  bigint(20) DEFAULT NULL,
    `c_status` varchar(8)  DEFAULT NULL,
    PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of course_0
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for course_1
-- ----------------------------
DROP TABLE IF EXISTS `course_1`;
CREATE TABLE `course_1`
(
    `cid`      bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '课程 ID',
    `cname`    varchar(64) DEFAULT NULL,
    `user_id`  bigint(20) DEFAULT NULL,
    `c_status` varchar(8)  DEFAULT NULL,
    PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of course_1
-- ----------------------------
BEGIN;
COMMIT;

SET
FOREIGN_KEY_CHECKS = 1;



SELECT COUNT(1)
FROM coursedb_0.course_0;
SELECT COUNT(1)
FROM coursedb_0.course_1;
SELECT COUNT(1)
FROM coursedb_1.course_0;
SELECT COUNT(1)
FROM coursedb_1.course_1;

truncate coursedb_0.course_0;
truncate coursedb_0.course_1;
truncate coursedb_1.course_0;
truncate coursedb_1.course_1;


