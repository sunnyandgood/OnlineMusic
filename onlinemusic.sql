/*
MySQL Data Transfer
Source Host: localhost
Source Database: onlinemusic
Target Host: localhost
Target Database: onlinemusic
Date: 2019/1/16 16:20:51
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
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
-- Table structure for file
-- ----------------------------
CREATE TABLE `file` (
  `fileName` varchar(11) COLLATE utf8_bin NOT NULL,
  `fileInfo` longblob NOT NULL,
  PRIMARY KEY (`fileName`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
CREATE TABLE `manager` (
  `manager_id` int(50) NOT NULL AUTO_INCREMENT,
  `manager_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `manager_password` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`manager_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for song
-- ----------------------------
CREATE TABLE `song` (
  `song_id` int(20) NOT NULL AUTO_INCREMENT,
  `song_name` varchar(10) COLLATE utf8_bin NOT NULL,
  `song_singer` varchar(10) COLLATE utf8_bin NOT NULL,
  `type_id` int(20) NOT NULL,
  `song_size` varchar(20) COLLATE utf8_bin NOT NULL,
  `song_url` varchar(500) COLLATE utf8_bin NOT NULL,
  `song_format` varchar(5) COLLATE utf8_bin NOT NULL,
  `song_clicks` int(20) NOT NULL,
  `song_download` int(20) NOT NULL,
  `song_uptime` date NOT NULL,
  `vip_id` int(5) NOT NULL,
  PRIMARY KEY (`song_id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for songtype
-- ----------------------------
CREATE TABLE `songtype` (
  `type_id` int(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for vip
-- ----------------------------
CREATE TABLE `vip` (
  `vip_id` int(5) NOT NULL AUTO_INCREMENT,
  `vip` varchar(10) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`vip_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `clicks` VALUES ('1', '1', '1', '2019-01-09');
INSERT INTO `clicks` VALUES ('2', '2', '1', '2019-01-17');
INSERT INTO `clicks` VALUES ('5', '1', '6', '2019-02-01');
INSERT INTO `clicks` VALUES ('15', '10', '5', '2019-01-16');
INSERT INTO `clicks` VALUES ('12', '1', '7', '2019-01-16');
INSERT INTO `clicks` VALUES ('11', '14', '4', '2019-01-16');
INSERT INTO `clicks` VALUES ('14', '10', '6', '2019-01-16');
INSERT INTO `clicks` VALUES ('13', '13', '5', '2019-01-16');
INSERT INTO `clicks` VALUES ('16', '9', '6', '2019-01-16');
INSERT INTO `clicks` VALUES ('17', '9', '6', '2019-01-16');
INSERT INTO `clicks` VALUES ('18', '9', '5', '2019-01-16');
INSERT INTO `clicks` VALUES ('19', '8', '5', '2019-01-16');
INSERT INTO `download` VALUES ('1', '1', '1', '2019-01-09');
INSERT INTO `file` VALUES ('ppp', 0x5B424033303639386361);
INSERT INTO `manager` VALUES ('1', 'admin', 'admin');
INSERT INTO `manager` VALUES ('2', 'haha', '123456a');
INSERT INTO `manager` VALUES ('3', 'yan1', 'sunnyandgood');
INSERT INTO `manager` VALUES ('11', 'jlkujp89', 'rey');
INSERT INTO `manager` VALUES ('7', '7', '7');
INSERT INTO `manager` VALUES ('8', '8', '8');
INSERT INTO `song` VALUES ('1', '世界第一等', '浪哥', '1', '1.2', '6', 'mp3', '7', '6', '2019-01-15', '3');
INSERT INTO `song` VALUES ('2', '好几款', '6', '2', '6', '6', 'mp3', '6', '6', '2019-02-07', '2');
INSERT INTO `song` VALUES ('3', '啦啦啦', '6', '3', '6', '6', 'mp3', '6', '6', '2019-01-09', '1');
INSERT INTO `song` VALUES ('4', '流浪', '花花', '2', '2.6', '/jdsgf/kdfgtsa/', 'mp3', '3', '0', '2019-01-09', '2');
INSERT INTO `song` VALUES ('5', '闲岁', '刚刚', '1', '6.6', 'D:/studyCodes/idea', 'mp3', '6', '1', '2019-01-09', '3');
INSERT INTO `song` VALUES ('6', '挪威的森林', '伍佰', '2', '3.6', '健康的施工方', 'mp3', '3', '0', '2019-01-09', '2');
INSERT INTO `song` VALUES ('7', '山里山外', '啊啊', '5', '2.1', 'D:sotfWareTomcat 9.0webappsROOT\resourcesupload4eac3f7d-e8f3-4403-989c-489ae3eb8564.xlsx', 'mp3', '1', '0', '2019-01-10', '2');
INSERT INTO `song` VALUES ('8', '任劳调结构', '是大法官的', '1', '1.5', 'D:studyCodeseclipse-photon', 'mp3', '0', '0', '2019-01-10', '1');
INSERT INTO `song` VALUES ('9', '九分裤', 'y7i', '1', '1.6', 'D:sotfWareTomcat 9.0webappsROOT\resourcesupload68c7bee5-885c-4ad8-a996-7b549ee5a364.mp3', 'mp3', '0', '0', '2019-01-11', '1');
INSERT INTO `song` VALUES ('10', '一个', '123', '5', '1.2', 'D:studyCodesideaOnlineMusic	argetcom.edu\resourcesupload3298b4bc-531f-4713-8508-574f46e1d18c.mp3', 'mp3', '0', '0', '2019-01-12', '2');
INSERT INTO `song` VALUES ('11', '刚刚发', '123', '4', '1.2', 'D:studyCodesideaOnlineMusic	argetcom.edu\resourcesupload6e259efa-f49f-4f8f-86d6-05b555c97e3d.mp3', 'mp3', '0', '0', '2019-01-12', '1');
INSERT INTO `song` VALUES ('12', '我到贵州来看桥', 'root', '2', '1.71', 'D:/studyCodes/idea/OnlineMusic/target/com.edu/resources/upload/07718b10-d234-4c8c-8137-348b5639db41.mp3', 'mp3', '0', '0', '2019-01-12', '2');
INSERT INTO `song` VALUES ('21', '啦啦啦', '123', '1', '1.2', 'D:/sotfWare/Tomcat 9.0/webapps/ROOT/resources/upload/15cbd4ca-9856-46ff-8407-fffbb62acc1e.mp3', 'mp3', '0', '0', '2019-01-16', '1');
INSERT INTO `song` VALUES ('22', '播州播州', '123', '2', '1.2', 'D:/sotfWare/Tomcat 9.0/webapps/ROOT/resources/upload/8f4c0a27-7764-425b-b566-58685c45782f.mp3', 'mp3', '0', '0', '2019-01-16', '1');
INSERT INTO `songtype` VALUES ('1', '流行金曲');
INSERT INTO `songtype` VALUES ('2', '经典老歌');
INSERT INTO `songtype` VALUES ('3', '少儿歌曲');
INSERT INTO `songtype` VALUES ('4', '轻音乐');
INSERT INTO `songtype` VALUES ('5', '郭德纲动力入会费');
INSERT INTO `user` VALUES ('1', '123', '123', '2', '2019-02-10', '男', '2');
INSERT INTO `user` VALUES ('2', '刚刚', '456', '2', '2019-02-02', '男', '2');
INSERT INTO `user` VALUES ('3', 'root', 'root', '3', '2019-01-25', '男', '4');
INSERT INTO `user` VALUES ('4', '29', '3', '1', '2018-09-01', '男', '2');
INSERT INTO `user` VALUES ('5', '黑胡椒', '123456', '3', '2018-09-01', '男', '1');
INSERT INTO `user` VALUES ('6', 'ert4r', 'rteert', '1', '2019-01-26', '女', '5');
INSERT INTO `user` VALUES ('7', 'lerihtgior', 'hopiyuh9o', '1', '2019-01-25', '女', '5');
INSERT INTO `user` VALUES ('12', '好好', 'root', '1', '2019-02-08', '男', '1');
INSERT INTO `user` VALUES ('14', 'qq', 'qq', '1', '2019-01-16', '女', '2');
INSERT INTO `vip` VALUES ('1', '普通用户');
INSERT INTO `vip` VALUES ('2', 'vip用户');
INSERT INTO `vip` VALUES ('3', '超级vip用户');
