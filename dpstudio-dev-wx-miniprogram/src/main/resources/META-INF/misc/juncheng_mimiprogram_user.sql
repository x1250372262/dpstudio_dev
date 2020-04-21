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

 Date: 20/04/2020 13:44:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for juncheng_mimiprogram_user
-- ----------------------------
DROP TABLE IF EXISTS `juncheng_mimiprogram_user`;
CREATE TABLE `juncheng_mimiprogram_user` (
  `id` varchar(32) NOT NULL,
  `open_id` varchar(256) NOT NULL,
  `union_id` varchar(32) DEFAULT NULL,
  `nick_name` varchar(32) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `gender` smallint(1) unsigned DEFAULT '0' COMMENT '性别',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `country` varchar(32) DEFAULT NULL COMMENT '国家',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `city` varchar(32) DEFAULT NULL COMMENT '城市',
  `create_time` bigint(13) NOT NULL DEFAULT '0',
  `last_modify_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
