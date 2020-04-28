/*
 Navicat Premium Data Transfer

 Source Server         : localhost_2020
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : juncheng_mall_tiantu

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 28/04/2020 09:35:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for juncheng_bug
-- ----------------------------
DROP TABLE IF EXISTS `juncheng_bug`;
CREATE TABLE `juncheng_bug` (
  `id` varchar(32) NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '问题标题',
  `type` smallint(1) DEFAULT '0' COMMENT '类型 0后端 1前端2综合',
  `level` smallint(1) DEFAULT '0' COMMENT '优先级 0正常 1低 2高 ',
  `content` text COMMENT '问题内容',
  `create_time` bigint(13) DEFAULT '0' COMMENT '创建时间',
  `status` smallint(2) DEFAULT '0' COMMENT '状态 0待处理 1已处理',
  `handler_user` varchar(32) DEFAULT NULL COMMENT '处理人',
  `handler_time` bigint(13) DEFAULT '0' COMMENT '处理时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '提出人',
  `last_modify_user` varchar(32) DEFAULT NULL COMMENT '最后修改人',
  `last_modify_time` bigint(13) DEFAULT '0' COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for juncheng_bug_log
-- ----------------------------
DROP TABLE IF EXISTS `juncheng_bug_log`;
CREATE TABLE `juncheng_bug_log` (
  `id` varchar(32) NOT NULL,
  `bug_id` varchar(32) NOT NULL,
  `handler_user` varchar(100) NOT NULL COMMENT '处理人',
  `handler_time` bigint(13) DEFAULT '0' COMMENT '处理时间',
  `action` varchar(32) DEFAULT NULL COMMENT '动作',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for juncheng_bug_user
-- ----------------------------
DROP TABLE IF EXISTS `juncheng_bug_user`;
CREATE TABLE `juncheng_bug_user` (
  `id` varchar(32) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `create_time` bigint(13) DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
