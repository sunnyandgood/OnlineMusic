/*
MySQL Data Transfer
Source Host: localhost
Source Database: onlinemusic
Target Host: localhost
Target Database: onlinemusic
Date: 2019/1/10 15:47:37
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for clicks
-- ----------------------------
CREATE TABLE `clicks` (
  `click_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `song_id` int(20) NOT NULL,
  `click_date` date NOT NULL,
  PRIMARY KEY (`click_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for download
-- ----------------------------
CREATE TABLE `download` (
  `download_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `song_id` int(20) NOT NULL,
  `download_date` date NOT NULL,
  PRIMARY KEY (`download_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
CREATE TABLE `manager` (
  `manager_id` int(50) NOT NULL AUTO_INCREMENT,
  `manager_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `manager_password` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`manager_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for song
-- ----------------------------
CREATE TABLE `song` (
  `song_id` int(20) NOT NULL AUTO_INCREMENT,
  `song_name` varchar(10) COLLATE utf8_bin NOT NULL,
  `song_singer` varchar(10) COLLATE utf8_bin NOT NULL,
  `type_id` int(20) NOT NULL,
  `song_size` varchar(20) COLLATE utf8_bin NOT NULL,
  `song_url` varchar(100) COLLATE utf8_bin NOT NULL,
  `song_format` varchar(5) COLLATE utf8_bin NOT NULL,
  `song_clicks` int(20) NOT NULL,
  `song_download` int(20) NOT NULL,
  `song_uptime` date NOT NULL,
  `vip_id` int(5) NOT NULL,
  PRIMARY KEY (`song_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for songtype
-- ----------------------------
CREATE TABLE `songtype` (
  `type_id` int(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(10) COLLATE utf8_bin NOT NULL,
  `user_password` varchar(20) COLLATE utf8_bin NOT NULL,
  `vip_id` int(5) NOT NULL,
  `user_birthday` date DEFAULT NULL,
  `user_gender` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `type_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for vip
-- ----------------------------
CREATE TABLE `vip` (
  `vip_id` int(5) NOT NULL AUTO_INCREMENT,
  `vip` varchar(10) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`vip_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `clicks` VALUES ('1', '1', '1', '2019-01-09');
INSERT INTO `clicks` VALUES ('2', '2', '2', '2019-01-17');
INSERT INTO `clicks` VALUES ('5', '1', '6', '2019-02-01');
INSERT INTO `clicks` VALUES ('6', '2', '7', '2019-02-09');
INSERT INTO `download` VALUES ('1', '1', '1', '2019-01-09');
INSERT INTO `download` VALUES ('3', '1', '5', '2019-01-09');
INSERT INTO `download` VALUES ('5', '1', '8', '2019-01-25');
INSERT INTO `manager` VALUES ('1', 'admin', 'admin');
INSERT INTO `manager` VALUES ('2', 'haha', '123456a');
INSERT INTO `manager` VALUES ('5', 'yan1', 'sunnyandgood');
INSERT INTO `manager` VALUES ('6', 'sunnyandgood', 'wrhsunny@163.com');
INSERT INTO `song` VALUES ('1', '世界第一等', '浪哥', '5', '9.9', '6', 'mp3', '6', '6', '2019-01-31', '3');
INSERT INTO `song` VALUES ('2', '好几款', '6', '2', '6', '6', 'mp3', '6', '6', '2019-02-07', '2');
INSERT INTO `song` VALUES ('6', '哈哈哈哈', '6', '3', '6', '6', 'mp3', '6', '6', '2019-01-09', '1');
INSERT INTO `song` VALUES ('5', '花花', '5', '5', '5', '5', '5', '5', '5', '2019-01-09', '1');
INSERT INTO `song` VALUES ('7', '流浪', '花花', '2', '2.6', '/jdsgf/kdfgtsa/', 'mp3', '0', '0', '2019-01-09', '2');
INSERT INTO `song` VALUES ('8', '闲岁', '刚刚', '1', '6.6', 'D:/studyCodes/idea', 'mp3', '0', '1', '2019-01-09', '3');
INSERT INTO `song` VALUES ('9', '挪威的森林', '伍佰', '2', '3.6', '健康的施工方', 'mp3', '0', '0', '2019-01-09', '2');
INSERT INTO `song` VALUES ('10', '南京', '可可', '2', '2.8', 'D:\\studyCodes\\idea', 'mp3', '0', '0', '2019-01-09', '1');
INSERT INTO `song` VALUES ('11', '胡广生', '等等', '3', '1.2', 'D:/studyCodes/idea', 'mp3 ', '0', '0', '2019-01-09', '1');
INSERT INTO `song` VALUES ('12', '调度', '范儿', '8', '1.1', 'D:/文件', 'mp3', '0', '0', '2019-01-10', '7');
INSERT INTO `songtype` VALUES ('1', '流行金曲');
INSERT INTO `songtype` VALUES ('2', '经典老歌');
INSERT INTO `songtype` VALUES ('4', '欧美金曲');
INSERT INTO `songtype` VALUES ('3', '热舞DJ');
INSERT INTO `songtype` VALUES ('5', '少儿歌曲');
INSERT INTO `songtype` VALUES ('6', '轻音乐');
INSERT INTO `songtype` VALUES ('8', '郭德纲');
INSERT INTO `user` VALUES ('1', '花花', '123', '1', '2019-01-09', '男', '1');
INSERT INTO `user` VALUES ('2', '刚刚', '456', '2', '2019-02-02', '男', '2');
INSERT INTO `vip` VALUES ('1', '普通用户');
INSERT INTO `vip` VALUES ('2', 'vip用户');
INSERT INTO `vip` VALUES ('3', '超级vip用户');
INSERT INTO `vip` VALUES ('7', '分工会');
