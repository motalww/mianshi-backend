DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
                         `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
                         `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签列表（json 数组）',
                         `thumbNum` int NOT NULL DEFAULT 0 COMMENT '点赞数',
                         `favourNum` int NOT NULL DEFAULT 0 COMMENT '收藏数',
                         `userId` bigint NOT NULL COMMENT '创建用户 id',
                         `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '帖子' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post
-- ----------------------------

-- ----------------------------
-- Table structure for post_favour
-- ----------------------------
DROP TABLE IF EXISTS `post_favour`;
CREATE TABLE `post_favour`  (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `postId` bigint NOT NULL COMMENT '帖子 id',
                                `userId` bigint NOT NULL COMMENT '创建用户 id',
                                `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `idx_postId`(`postId` ASC) USING BTREE,
                                INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子收藏' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post_favour
-- ----------------------------

-- ----------------------------
-- Table structure for post_thumb
-- ----------------------------
DROP TABLE IF EXISTS `post_thumb`;
CREATE TABLE `post_thumb`  (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `postId` bigint NOT NULL COMMENT '帖子 id',
                               `userId` bigint NOT NULL COMMENT '创建用户 id',
                               `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `idx_postId`(`postId` ASC) USING BTREE,
                               INDEX `idx_userId`(`userId` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子点赞' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post_thumb
-- ----------------------------

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
                             `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
                             `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签列表（json 数组）',
                             `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '推荐答案',
                             `userId` bigint NOT NULL COMMENT '创建用户 id',
                             `editTime` datetime NULL DEFAULT NULL COMMENT '编辑时间',
                             `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `needVip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '仅会员可见，0:否 1:是',
                             `isDelete` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '是否删除,0:否，1:是',
                             `viewNum` int NULL DEFAULT NULL COMMENT '浏览量',
                             `thumbNum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '点赞数',
                             `favourNum` int NULL DEFAULT NULL COMMENT '收藏数',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1873658647531057155 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1872896013844189185, 'final关键字的作用', '', '[\"Java\"]', '1. 定义常量：在变量声明前加上 final 关键字，可以将变量定义为常量。常量是不可变的，⼀旦初始化后其值不能再被改变。例如，final int MAX_NUM = 100; 将 MAX_NUM 定义为⼀个常量，其值不能再被改变。\n 2. 防⽌⽅法被重写：在⽅法声明前加上 final 关键字，可以防⽌该⽅法被⼦类重写。例如，final void doSomething() { ... } 将 doSomething ⽅法定义为⼀个不可重写的⽅法。\n 3. 防⽌类被继承：在类声明前加上 final 关键字，可以防⽌该类被继承。例如，final class MyClass { ... } 将MyClass 类定义为⼀个不可继承的类。\n 4. 确保对象引⽤不可变：在对象引⽤声明前加上 final 关键字，可以确保该引⽤指向的对象不能被改变，但是该对象的内容可以被改变。例如，final MyClass obj = new MyClass(); 将 obj 声明为⼀个不可变引⽤，指向⼀个可变的 MyClass 对象。', 1872892092979449858, NULL, NULL, NULL, '0', '0', NULL, NULL, NULL);
INSERT INTO `question` VALUES (1873658647531057154, '什么是“脏读” 、“幻读” 、 “不可重复读”', '', '[\"Mysql\"]', '脏读(Dirty Reads)：事务A读取了事务B中未提交的数据。如果B执行回滚操作，那么A读取到的数据是脏数据。\n 幻读（Phantom Reads)：一个事务A读取了几行数据，接着另一个并发事务B 插入 了一些数据时。在随后的查询中，事务A就会发现多了一些原本不存在的记录，就好像发生了幻觉一样，所以称为幻读。\n 不可重复读（Non-Repeatable Reads)：事务 A 多次读取同一数据，事务 B 在事务A多次读取的过程中，对数据作了 更新 并提交，导致事务A多次读取同一数据时，结果不一致。', 1872892092979449858, NULL, NULL, NULL, '0', '0', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for question_bank
-- ----------------------------
DROP TABLE IF EXISTS `question_bank`;
CREATE TABLE `question_bank`  (
                                  `id` bigint NOT NULL COMMENT 'id',
                                  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
                                  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
                                  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
                                  `userId` bigint NULL DEFAULT NULL COMMENT '创建用户 id',
                                  `editTime` datetime NULL DEFAULT NULL COMMENT '编辑时间',
                                  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  `isDelete` int NOT NULL DEFAULT 0 COMMENT '是否删除',
                                  `priority` int NULL DEFAULT NULL COMMENT '优先级',
                                  `viewNum` int NULL DEFAULT NULL COMMENT '浏览量',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_bank
-- ----------------------------
INSERT INTO `question_bank` VALUES (1872896417051021314, 'Java基础', 'Java基础', '', 1872892092979449858, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `question_bank` VALUES (1873655474426834946, 'Mysql', '', '', 1872892092979449858, NULL, NULL, NULL, 0, NULL, NULL);

-- ----------------------------
-- Table structure for question_bank_question
-- ----------------------------
DROP TABLE IF EXISTS `question_bank_question`;
CREATE TABLE `question_bank_question`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                           `questionBankId` bigint NULL DEFAULT NULL COMMENT '题库 id',
                                           `questionId` bigint NULL DEFAULT NULL COMMENT '题目 id',
                                           `userId` bigint NULL DEFAULT NULL COMMENT '创建用户 id',
                                           `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                           `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                           `questionOrder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目顺序（题号）',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1872900092943241218 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_bank_question
-- ----------------------------
INSERT INTO `question_bank_question` VALUES (1872900092943241217, 1872896417051021314, 1872896013844189185, 1872892092979449858, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
                         `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                         `unionId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
                         `mpOpenId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公众号openId',
                         `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
                         `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
                         `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
                         `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
                         `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         `editTime` datetime NULL DEFAULT NULL COMMENT '编辑时间',
                         `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
                         `vipExpireTime` datetime NULL DEFAULT NULL COMMENT '会员过期时间',
                         `vipCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员兑换码',
                         `vipNumber` int NULL DEFAULT NULL COMMENT '会员编号',
                         PRIMARY KEY (`id`) USING BTREE,
                         INDEX `idx_unionId`(`unionId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1872892092979449859 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1872892092979449858, 'super', '264b62e995454af3a74d14fcd3a14d22', NULL, NULL, NULL, NULL, NULL, 'admin', '2024-12-28 14:27:25', '2024-12-28 14:28:20', NULL, 0, NULL, NULL, NULL);
