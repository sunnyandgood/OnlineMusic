/*
MySQL Data Transfer
Source Host: localhost
Source Database: onlinemusic
Target Host: localhost
Target Database: onlinemusic
Date: 2019/1/7 15:44:52
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for clicks
-- ----------------------------
CREATE TABLE `clicks` (
  `click_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `song_id` int(20) NOT NULL,
  PRIMARY KEY (`click_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for download
-- ----------------------------
CREATE TABLE `download` (
  `download_id` int(20) NOT NULL,
  `user_id` int(20) NOT NULL,
  `song_id` int(20) NOT NULL,
  `download_times` int(20) NOT NULL,
  PRIMARY KEY (`download_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
CREATE TABLE `manager` (
  `manager_id` int(5) NOT NULL,
  `manager_name` varchar(10) COLLATE utf8_bin NOT NULL,
  `manager_password` varchar(10) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`manager_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for song
-- ----------------------------
CREATE TABLE `song` (
  `song_id` int(20) NOT NULL AUTO_INCREMENT,
  `song_name` varchar(10) COLLATE utf8_bin NOT NULL,
  `song_singer` varchar(10) COLLATE utf8_bin NOT NULL,
  `type_id` int(20) NOT NULL,
  `song_size` double(10,0) NOT NULL,
  `song_url` varchar(20) COLLATE utf8_bin NOT NULL,
  `song_format` varchar(5) COLLATE utf8_bin NOT NULL,
  `song_clicks` int(20) NOT NULL,
  `song_download` int(20) NOT NULL,
  `song_uptime` date NOT NULL,
  `vip_id` int(5) NOT NULL,
  PRIMARY KEY (`song_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for songtype
-- ----------------------------
CREATE TABLE `songtype` (
  `type_id` int(20) NOT NULL,
  `type_name` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `user_id` int(20) NOT NULL,
  `user_name` varchar(10) COLLATE utf8_bin NOT NULL,
  `user_password` varchar(20) COLLATE utf8_bin NOT NULL,
  `vip_id` int(5) NOT NULL,
  `user_birthday` date DEFAULT NULL,
  `user_gender` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `user_like` int(20) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for vip
-- ----------------------------
CREATE TABLE `vip` (
  `vip_id` int(5) NOT NULL,
  `vip` varchar(10) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`vip_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records 
-- ----------------------------
