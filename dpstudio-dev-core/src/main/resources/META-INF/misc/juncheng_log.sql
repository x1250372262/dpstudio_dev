/*
 Navicat Premium Data Transfer

 Source Server         : localhost_2020
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : juncheng_hglw

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 26/05/2020 09:33:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for juncheng_log
-- ----------------------------
DROP TABLE IF EXISTS `juncheng_log`;
CREATE TABLE `juncheng_log` (
  `id` varchar(32) NOT NULL,
  `module_type` int(4) NOT NULL DEFAULT '0' COMMENT '模块类型',
  `module_name` varchar(32) NOT NULL COMMENT '模块名称',
  `type` int(4) NOT NULL DEFAULT '0' COMMENT '日志类型',
  `action` varchar(32) NOT NULL COMMENT '日志类型字符串',
  `resource_id` varchar(32) NOT NULL COMMENT '日志对象id',
  `create_user` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `content` text NOT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
