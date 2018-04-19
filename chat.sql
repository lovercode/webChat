/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : chat

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-01-11 11:34:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `chat_id` char(32) NOT NULL,
  `chat_info` varchar(20000) DEFAULT NULL,
  `chat_from` char(32) DEFAULT NULL,
  `chat_to` char(32) DEFAULT NULL,
  `chat_time` bigint(11) DEFAULT NULL,
  `chat_type` char(32) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chat_room
-- ----------------------------
DROP TABLE IF EXISTS `chat_room`;
CREATE TABLE `chat_room` (
  `room_id` char(32) NOT NULL,
  `room_name` varchar(64) DEFAULT NULL,
  `room_des` varchar(64) DEFAULT NULL,
  `create_user` char(32) DEFAULT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fgroup
-- ----------------------------
DROP TABLE IF EXISTS `fgroup`;
CREATE TABLE `fgroup` (
  `group_id` char(32) NOT NULL,
  `group_name` varchar(64) DEFAULT NULL,
  `user_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `user_id` char(32) NOT NULL,
  `friend_id` char(32) NOT NULL,
  `validate_info` varchar(64) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `group_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`friend_id`,`user_id`),
  KEY `test` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for read_record
-- ----------------------------
DROP TABLE IF EXISTS `read_record`;
CREATE TABLE `read_record` (
  `chat_id` char(32) NOT NULL,
  `user_id` char(32) NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`chat_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for room_user
-- ----------------------------
DROP TABLE IF EXISTS `room_user`;
CREATE TABLE `room_user` (
  `room_user_id` char(32) NOT NULL,
  `user_id` char(32) DEFAULT NULL,
  `user_type` char(32) DEFAULT NULL,
  `room_id` char(32) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`room_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token` (
  `token_id` char(32) NOT NULL,
  `token_name` varchar(64) DEFAULT NULL,
  `token_value` varchar(64) DEFAULT NULL,
  `token_type` varchar(64) DEFAULT NULL,
  `time` bigint(11) DEFAULT NULL,
  `user_id` char(32) DEFAULT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` char(32) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_key` varchar(255) DEFAULT NULL,
  `user_sex` char(4) DEFAULT NULL,
  `user_real_name` varchar(64) DEFAULT NULL,
  `user_age` int(11) DEFAULT NULL,
  `user_qq` varchar(32) DEFAULT NULL,
  `user_wechat` varchar(64) DEFAULT NULL,
  `user_email` varchar(64) DEFAULT NULL,
  `user_img` varchar(255) DEFAULT NULL,
  `user_word` varchar(255) DEFAULT NULL,
  `user_status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` char(32) NOT NULL,
  `user_img` varchar(255) DEFAULT NULL,
  `user_word` varchar(255) DEFAULT NULL,
  `user_status` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
