/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : comment

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-05-21 19:31:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(100) NOT NULL,
  `bussiness_file` varchar(50) NOT NULL,
  `bussiness_hours` varchar(100) NOT NULL,
  `env` float NOT NULL,
  `env_file` varchar(500) NOT NULL,
  `flavor` float NOT NULL,
  `img_file_name` varchar(500) NOT NULL,
  `percapiita` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `service` float NOT NULL,
  `title` varchar(50) NOT NULL,
  `userid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `pre` int(11) NOT NULL,
  `sum` float NOT NULL,
  `city` varchar(100) DEFAULT NULL,
  `lat` float NOT NULL,
  `lng` float NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=1416 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES ('1414', '钞库街86号', 'da86575e-66c8-4825-8cd3-160f6f503fac.jpg', '1', '4.12', 'edcdc51f-5bfd-419b-a6de-1e7371a2adec.jpg', '3.75', 'd0be2a94-90da-4f23-ac68-2c7f25ddb2c0.jpg', '13', '11313', '4.5', 'sjs1', '15', 'sj', '2', '63', '4.25', '江苏省/南京市/秦淮区', '32.0241', '118.793');
INSERT INTO `business` VALUES ('1415', '水西门大街345号', 'ed1a8923-ac2b-4d9a-9053-0e3fb54b0502.jpg', '全天', '3.88', '5ad68d4b-4287-406d-a3e2-1c7a16bd3302.jpg', '4.62', '12f49ecc-d66c-491e-b38c-931693b5b4b3.jpg', '20', '123456789', '4.38', '小丸子的店', '16', 'user2', '2', '43', '4.38', '江苏省/南京市/建邺区', '32.0392', '118.748');

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bussinessid` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `bussiness_name` varchar(50) DEFAULT NULL,
  `evaluateid` bigint(20) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `bussinessid` (`bussinessid`)
) ENGINE=MyISAM AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES ('22', '1388', '2', '14', '311332212', '271', '31');
INSERT INTO `collection` VALUES ('36', '1414', '1', '14', 'sjs1', '0', null);
INSERT INTO `collection` VALUES ('43', '1414', '2', '14', 'sjs1', '276', 'test');
INSERT INTO `collection` VALUES ('52', '1414', '2', '18', 'sjs1', '283', '非常喜欢，口味非常棒！');
INSERT INTO `collection` VALUES ('24', '1389', '2', '14', '三汊河国家湿地公园', '272', 'test');
INSERT INTO `collection` VALUES ('59', '1414', '2', '1', 'sjs1', '282', '还不错吖');
INSERT INTO `collection` VALUES ('57', '1415', '1', '19', '小丸子的店', '0', null);
INSERT INTO `collection` VALUES ('58', '1415', '2', '19', '小丸子的店', '288', '口味很棒！');
INSERT INTO `collection` VALUES ('49', '1414', '1', '15', 'sjs1', '0', null);
INSERT INTO `collection` VALUES ('40', '1388', '2', '16', '1', '280', '我是小丸子');
INSERT INTO `collection` VALUES ('41', '1388', '2', '16', '1', '271', '31');
INSERT INTO `collection` VALUES ('42', '1388', '1', '16', '1', '0', null);
INSERT INTO `collection` VALUES ('44', '1415', '2', '17', '小丸子的店', '281', '口味很好，服务业很棒！');
INSERT INTO `collection` VALUES ('45', '1415', '1', '17', '小丸子的店', '0', null);
INSERT INTO `collection` VALUES ('46', '1389', '1', '17', '三汊河国家湿地公园', '0', null);
INSERT INTO `collection` VALUES ('47', '1414', '1', '17', 'sjs1', '0', null);
INSERT INTO `collection` VALUES ('48', '1415', '1', '15', '小丸子的店', '0', null);
INSERT INTO `collection` VALUES ('50', '1414', '2', '15', 'sjs1', '283', '非常喜欢，口味非常棒！');
INSERT INTO `collection` VALUES ('53', '1414', '1', '18', 'sjs1', '0', null);
INSERT INTO `collection` VALUES ('54', '1415', '1', '18', '小丸子的店', '0', null);
INSERT INTO `collection` VALUES ('60', '1414', '2', '1', 'sjs1', '283', '非常喜欢，口味非常棒！');
INSERT INTO `collection` VALUES ('62', '1415', '1', '1', '小丸子的店', '0', null);

-- ----------------------------
-- Table structure for evaluate
-- ----------------------------
DROP TABLE IF EXISTS `evaluate`;
CREATE TABLE `evaluate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bussinessid` bigint(20) NOT NULL,
  `env` float NOT NULL,
  `flavor` float NOT NULL,
  `img_file` varchar(500) NOT NULL,
  `message` varchar(500) NOT NULL,
  `overall` float NOT NULL,
  `response` varchar(500) DEFAULT NULL,
  `service` float NOT NULL,
  `start` int(11) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `bussinessid` (`bussinessid`)
) ENGINE=MyISAM AUTO_INCREMENT=291 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of evaluate
-- ----------------------------
INSERT INTO `evaluate` VALUES ('280', '1388', '4', '5', '883da50a-d6d9-459c-9e56-04fdafdf02fa.jpg*672a981c-48e0-4c6b-96a1-5f82a15698b0.jpg', '我是小丸子', '4', null, '4.5', '1', '16', 'user2', '1', '2019-03-19 12:21:21');
INSERT INTO `evaluate` VALUES ('271', '1388', '5', '5', '8a177709-e165-40aa-a109-69946a3fd153.jpg', '31', '5', null, '5', '4', '13', 'shangjia', '1', '2019-03-03 03:41:17');
INSERT INTO `evaluate` VALUES ('281', '1415', '5', '5', '6ad1470d-0bda-42c7-9ea3-e2156b98006b.jpeg', '口味很好，服务业很棒！', '5', null, '4.5', '1', '17', 'user3', '1', '2019-03-22 10:52:08');
INSERT INTO `evaluate` VALUES ('282', '1414', '3.5', '3', 'fc1210a3-f44e-4cd9-93e5-9fdb38794a15.jpg', '还不错吖', '2.5', null, '4', '2', '17', 'user3', '1', '2019-03-26 12:06:33');
INSERT INTO `evaluate` VALUES ('285', '1415', '2.5', '5', '5cc243c7-92e6-4778-b4f5-0fb0643cb6b2.jpg', '口味很好', '4', '谢谢客官~', '3.5', '0', '18', '四月', '1', '2019-04-02 14:44:04');
INSERT INTO `evaluate` VALUES ('286', '1414', '4.5', '5', '1ebc9131-3380-4634-aaee-41f3ab9b7b35.png', '超级喜欢', '5', null, '5', '2', '19', '海宝', '1', '2019-04-02 16:44:12');
INSERT INTO `evaluate` VALUES ('290', '1415', '3.5', '3.5', 'e69fb8b4-b77e-41e5-a6c9-8b48eab9427d.jpg', '口味还行', '3.5', null, '4.5', '0', '1', 'admin', '1', '2019-05-07 08:10:30');
INSERT INTO `evaluate` VALUES ('283', '1414', '5', '4.5', 'ff31fa7e-d971-4a50-ada9-c91c9e814262.jpg', '非常喜欢，口味非常棒！', '5', null, '4.5', '2', '15', 'sj', '1', '2019-03-27 13:12:32');
INSERT INTO `evaluate` VALUES ('284', '1414', '3.5', '2.5', '391168f4-81f6-4140-97b8-d977bfa76096.jpg', '口味一般，有待提高，服务很好', '4.5', '谢谢，下次光临', '4.5', '2', '14', 'test', '1', '2019-04-01 02:40:49');
INSERT INTO `evaluate` VALUES ('269', '1388', '2.5', '1.5', 'ddb4cb29-69e2-4760-8d0d-a2fdafdeb49a.jpg', '线研究所，顾名思义家是专做线。知道这家店也是今年夏天朋友圈家刷屏了，正好今夏从厦上海旅游一圈回来，妹妹好久没了，想要请姐饭，就选了这家离姐近网红店。\n\n火山兔位于夫子庙水游负二楼，BHG超市，大，走道狭长，少位。店内装修是红黑色，墙上多型各异小兔子霓虹灯，还挺引注目。\n\n主营各类线，看了下价格表，大致分为麻辣、酸汤、野生菌菇、番汤、咖喱叻沙、小坛酸菜泰式冬阴功等七种锅，还小食饮料，另外还可以加料，味因而异，顾到能辣能辣顾需求。\n\n妹妹一选了一海鲜咖喱叻沙大本营。端上来点惊到了！记拍频参物，那个装线大真超级大，感跟小锅一拼！满满咖喱，色金，咖喱味扑而来，垂涎止。上还个超级大虾子，虾子真大，差多一个手掌那么长，虾屁股上是开，去除了脏脏虾线，起来虾肉肉质满，开虾肉更加入味。过需要槽是，大虾，一虾肉紧实满，另一感新鲜（详图4），虾肉松散，形成鲜明对。但咖喱汤鲜，要是天热喝了出汗，加上肚皮容量限，真想全喝光。\n\n随餐附送小食味也错，红糖冰粉清凉，上还酸酸甜甜葡萄干。萝也是非，酸酸，开胃', '3.5', '线研究所，顾名思义家是专做线。知道这家店也是今年夏天朋友圈家刷屏了，正好今夏从厦上海旅游一圈回来，妹妹好久没了，想要请姐饭，就选了这家离姐近网红店。  火山兔位于夫子庙水游负二楼，BHG超市，大，走道狭长，少位。店内装修是红黑色，墙上多型各异小兔子霓虹灯，还挺引注目。  主营各类线，看了下价格表，大致分为麻辣、酸汤、野生菌菇、番汤、咖喱叻沙、小坛酸菜泰式冬阴功等七种锅，还小食饮料，另外还可以加料，味因而异，顾到能辣能辣顾需求。  妹妹一选了一海鲜咖喱叻沙大本营。端上来点惊到了！记拍频参物，那个装线大真超级大，感跟小锅一拼！满满咖喱，色金，咖喱味扑而来，垂涎止。上还个超级大虾子，虾子真大，差多一个手掌那么长，虾屁股上是开，去除了脏脏虾线，起来虾肉肉质满，开虾肉更加入味。过需要槽是，大虾，一虾肉紧实满，另一感新鲜（详图4），虾肉松散，形成鲜明对。但咖喱汤鲜，要是天热喝了出汗，加上肚皮容量限，真想全喝光。  随餐附送小食味也错，红糖冰粉清凉，上还酸酸甜甜葡萄干。萝也是非，酸酸，开胃', '2', '1', '13', 'shangjia', '1', '2019-03-03 02:43:54');
INSERT INTO `evaluate` VALUES ('288', '1415', '4.5', '5', '30f282ac-1f07-4c7b-af00-b8b5b945e3de.jpg', '口味很棒！', '5', null, '5', '1', '19', '海宝', '1', '2019-04-03 01:53:09');

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bussinessid` bigint(20) NOT NULL,
  `food` varchar(50) NOT NULL,
  `introduce` varchar(50) NOT NULL,
  `price` float NOT NULL,
  `typeid` bigint(20) NOT NULL,
  `sort` bigint(20) DEFAULT NULL,
  `ifrecommend` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES ('10', '1388', 'c856a739-b275-4bc0-b64a-952182e80d66.jpg', '特色麻辣毛肚火锅米线', '28', '16', '1', '0');
INSERT INTO `food` VALUES ('9', '1388', '47ce04fa-914b-4919-af59-aa95732e11d2.jpg', '红糖冰粉', '22', '16', '2', '1');
INSERT INTO `food` VALUES ('11', '1389', '3786d001-05ec-4cfb-934e-02b0e20c671c.jpg', '安徽省/蚌埠市/淮上区', '32', '15', '31', '1');
INSERT INTO `food` VALUES ('8', '1388', '8f011e07-37a8-43b0-90d6-034b7a422706.jpg', '肥牛番茄火锅米线', '28', '16', '1', '1');
INSERT INTO `food` VALUES ('12', '1415', 'd8c9c190-70cc-47e5-8132-c75d0dae7d23.jpg', '面', '10', '16', '1', '1');
INSERT INTO `food` VALUES ('13', '1415', '44afb208-107a-4404-8c7f-76c13ac88617.jpg', '小吃', '30', '10', '11', '1');
INSERT INTO `food` VALUES ('14', '1415', 'ce287096-a3e5-4909-a600-eac57fa64a55.jpg', '饮品1', '10', '15', '1', '1');
INSERT INTO `food` VALUES ('15', '1415', '86099eda-05f1-4e9b-9b07-4ca5b1ffd906.jpg', '咖啡1', '20', '15', '2', '0');
INSERT INTO `food` VALUES ('16', '1415', '0c66ea4a-55f2-4a74-a183-5cfe1f1bf0dd.jpg', '橙汁1', '12', '15', '1', '0');
INSERT INTO `food` VALUES ('17', '1415', '2b2d99d6-0155-436d-a7a0-8c0dea3aa583.jpg', '饮品2', '7', '15', '2', '0');
INSERT INTO `food` VALUES ('18', '1415', '86524b9a-a385-4288-bb48-768e0d29168b.jpg', '饮品3', '13', '15', '0', '1');
INSERT INTO `food` VALUES ('19', '1415', '1602544a-6818-4d36-b917-6819ce76957f.jpg', '红茶', '9', '15', '2', '0');
INSERT INTO `food` VALUES ('20', '1415', '4ccd7985-ce83-47fb-8f53-1c647b626a0e.jpg', '牛奶', '13', '15', '0', '1');
INSERT INTO `food` VALUES ('23', '1415', 'c1e993c6-d472-4739-aff4-5b3058b7263c.jpg', '饮品1', '14', '15', '1', '1');
INSERT INTO `food` VALUES ('22', '1415', 'cd4e8cca-4265-4c72-98c3-bcd155dbd07e.jpg', '肉1', '23', '16', '1', '1');
INSERT INTO `food` VALUES ('24', '1415', 'ba619a24-eb89-4db2-b142-ea094eddff37.jpg', '饮品5', '166', '15', '0', '1');
INSERT INTO `food` VALUES ('25', '1415', 'c87225c4-6d19-400c-8c97-38caf90c51c5.jpg', '果汁1', '12', '15', '3', '1');
INSERT INTO `food` VALUES ('26', '1415', '71570723-a36f-4df1-81aa-60f235f62b2e.jpg', '红牛', '5', '15', '3', '0');
INSERT INTO `food` VALUES ('27', '1414', 'bfd7976a-c149-42af-96cf-51f4b69be554.jpg', '汤面', '12', '14', '1', '1');
INSERT INTO `food` VALUES ('28', '1414', '81a9a1b0-d54a-4807-97fa-5bca9e13c4dc.jpg', '橙汁', '6', '15', '2', '0');
INSERT INTO `food` VALUES ('29', '1414', '93332597-9553-40e5-a570-1708949cd223.jpg', '西瓜', '5', '13', '1', '1');
INSERT INTO `food` VALUES ('30', '1414', '3f5dcbfe-72f8-4d21-afdc-62e69a41e106.jpg', '肉卷', '11', '10', '1', '1');
INSERT INTO `food` VALUES ('31', '1414', 'dd9438eb-daba-4540-84b6-7522b51a0f29.jpg', '甜饼', '5', '10', '2', '0');
INSERT INTO `food` VALUES ('32', '1414', '1a63511d-e560-49c7-8b55-f87894a48ae8.jpg', '火锅1', '30', '11', '1', '1');
INSERT INTO `food` VALUES ('33', '1414', '51d58e47-3e45-469d-a6a7-fba9100f8827.jpg', '火锅2', '54', '11', '1', '1');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES ('13', '水果生鲜');
INSERT INTO `type` VALUES ('12', '烧烤');
INSERT INTO `type` VALUES ('11', '火锅');
INSERT INTO `type` VALUES ('10', '小吃快餐');
INSERT INTO `type` VALUES ('14', '面馆');
INSERT INTO `type` VALUES ('15', '饮品店');
INSERT INTO `type` VALUES ('16', '本帮江浙菜');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ch_name` varchar(50) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `img` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin管理员', '3', 'admin', '2QeC+6mkJQnVRSh4jUUJSQ==', '13137@qq.com', '364acf84-c34d-4cf8-b463-0c8aeab60ed7.jpg', '18913806573');
INSERT INTO `user` VALUES ('15', '喵宁', '2', 'sj', 'R00xeRzxPfWSzV8g3xpTVQ==', '13317@qq.com', 'e9a9b44b-6049-4e3d-b6ff-d42c6133d1e2.jpg', '15651627213');
INSERT INTO `user` VALUES ('18', '四月', '1', '四月', 'gzSMaGMX3QtT+nq7Fq8FcQ==', '1234567890@qq.com', '6b7c0781-93ef-42f0-a145-3e94b2add4e7.jpg', '13298987890');
INSERT INTO `user` VALUES ('14', '帆帆', '1', 'test', 'g0NGqWzZRTJ11rwXCcAVbA==', '1313@foxmail.com', 'a3c92e32-670c-4a28-af0f-ec51ba070b6f.jpg', '13260906534');
INSERT INTO `user` VALUES ('16', '小丸子', '2', 'user2', '7k0Q4v0danbAWYf0tI1k4Q==', '1234567890@qq.com', '860c7397-a90e-4aff-8c82-6fc49ea954cd.jpg', '13298987890');
INSERT INTO `user` VALUES ('17', '猫', '1', 'user3', 'YwbtSnWpyg4Mba0K881Jmg==', '13317@qq.com', '32068e7b-8fe9-4adf-b014-b87d9784bc8e.jpg', '13245635476');
INSERT INTO `user` VALUES ('19', '海宝', '1', '海宝', 'Nv6VB2B4gYUEMMUiCEPKPw==', '1234567891@qq.com', 'bfdeb0db-fd9c-487f-ac3e-f438dcc2051e.png', '15651627213');
INSERT INTO `user` VALUES ('20', '十三', '1', '十三', 'HZjiP/FWNWf5GPWlZfsV3A==', '1234567901@qq.com', '4c864734-0c53-42cf-899e-5a1c220f9d5d.jpg', '15651627221');

-- ----------------------------
-- Table structure for user_recommend
-- ----------------------------
DROP TABLE IF EXISTS `user_recommend`;
CREATE TABLE `user_recommend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bussinessid` bigint(20) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  `value` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of user_recommend
-- ----------------------------
INSERT INTO `user_recommend` VALUES ('1', '1389', '1', '21', '2019-03-06 01:02:03');
INSERT INTO `user_recommend` VALUES ('2', '1388', '1', '35', '2019-03-06 01:35:05');
INSERT INTO `user_recommend` VALUES ('3', '1414', '14', '49', '2019-03-06 01:35:07');
INSERT INTO `user_recommend` VALUES ('4', '1414', '1', '54', '2019-03-06 13:09:44');
INSERT INTO `user_recommend` VALUES ('5', '1389', '14', '5', '2019-03-13 12:35:26');
INSERT INTO `user_recommend` VALUES ('6', '1388', '16', '47', '2019-03-19 12:20:40');
INSERT INTO `user_recommend` VALUES ('7', '1415', '1', '43', '2019-03-19 12:31:42');
INSERT INTO `user_recommend` VALUES ('8', '1415', '16', '2', '2019-03-19 12:48:49');
INSERT INTO `user_recommend` VALUES ('9', '1392', '14', '-6', '2019-03-19 12:53:53');
INSERT INTO `user_recommend` VALUES ('10', '1415', '17', '32', '2019-03-22 10:46:59');
INSERT INTO `user_recommend` VALUES ('11', '1389', '17', '11', '2019-03-26 12:05:35');
INSERT INTO `user_recommend` VALUES ('12', '1414', '17', '19', '2019-03-26 12:05:50');
INSERT INTO `user_recommend` VALUES ('13', '1388', '14', '2', '2019-03-27 12:50:47');
INSERT INTO `user_recommend` VALUES ('14', '1414', '15', '20', '2019-03-27 13:11:38');
INSERT INTO `user_recommend` VALUES ('15', '1389', '15', '-6', '2019-03-27 13:11:54');
INSERT INTO `user_recommend` VALUES ('16', '1415', '15', '11', '2019-03-27 13:15:04');
INSERT INTO `user_recommend` VALUES ('17', '1415', '18', '22', '2019-04-02 14:43:27');
INSERT INTO `user_recommend` VALUES ('18', '1414', '18', '25', '2019-04-02 15:18:16');
INSERT INTO `user_recommend` VALUES ('19', '1415', '14', '1', '2019-04-02 15:52:33');
INSERT INTO `user_recommend` VALUES ('20', '1414', '19', '24', '2019-04-02 16:43:55');
INSERT INTO `user_recommend` VALUES ('21', '1415', '19', '32', '2019-04-03 01:52:39');
