/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : demo5

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 25/10/2024 22:47:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_contact
-- ----------------------------
DROP TABLE IF EXISTS `tb_contact`;
CREATE TABLE `tb_contact`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系人姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系地址',
  `birthday` timestamp NULL DEFAULT NULL COMMENT '生日',
  `company` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司名称',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注信息',
  `status` int NULL DEFAULT 1 COMMENT '状态：1-正常 0-已删除',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通讯录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_contact
-- ----------------------------
INSERT INTO `tb_contact` VALUES (1, '张志远', 'http://localhost:9655/file/95ad7d5eab8648e789be14f04a73fcf6.png', '13812345678', 'zhang.zy@alibaba.com', '浙江省杭州市西湖区万塘路18号', '1985-06-15 00:00:00', '阿里巴巴集团', '技术总监', '阿里云团队负责人，技术合作伙伴', 1, '2024-10-25 22:40:22', '2024-10-25 22:40:22');
INSERT INTO `tb_contact` VALUES (2, '李梅', 'http://localhost:9655/file/95ad7d5eab8648e789be14f04a73fcf6.png', '13923456789', 'mei.li@163.com', '广东省深圳市南山区科技园南区', '1990-03-22 00:00:00', '腾讯音乐', '产品经理', '音乐版权合作项目联系人', 1, '2024-10-25 22:40:22', '2024-10-25 22:40:42');
INSERT INTO `tb_contact` VALUES (3, '王建国', 'http://localhost:9655/file/95ad7d5eab8648e789be14f04a73fcf6.png', '13567890123', 'wang.jg@hospital.cn', '北京市海淀区学院路23号', '1975-09-08 00:00:00', '北京协和医院', '主任医师', '心内科专家，年度体检联系人', 1, '2024-10-25 22:40:22', '2024-10-25 22:40:42');
INSERT INTO `tb_contact` VALUES (4, '陈晓华', 'http://localhost:9655/file/95ad7d5eab8648e789be14f04a73fcf6.png', '13698765432', 'chen.xh@school.edu.cn', '江苏省南京市鼓楼区汉口路22号', '1988-12-30 00:00:00', '南京师范大学', '教授', '人工智能研究领域专家，学术交流', 1, '2024-10-25 22:40:22', '2024-10-25 22:40:42');
INSERT INTO `tb_contact` VALUES (5, '赵芸', 'http://localhost:9655/file/95ad7d5eab8648e789be14f04a73fcf6.png', '13589765432', 'zhyun@investment.com', '上海市浦东新区陆家嘴环路888号', '1982-04-18 00:00:00', '中信证券', '投资顾问', '理财咨询顾问，私人投资顾问', 1, '2024-10-25 22:40:22', '2024-10-25 22:40:42');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `created_at` date NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录',
  `updatetime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (2, 'admin', '111111', '3213195678@qq.com', 'http://localhost:9655/file/c5ce3771de1f4d88908a1c48ccbf4d53.jpg', '大螺丝 ', '2024-05-16', '2024-10-25 22:45:27', '2024-05-05 15:06:01', 0);

SET FOREIGN_KEY_CHECKS = 1;
