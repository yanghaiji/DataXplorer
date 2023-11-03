SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_xplorer_logger
-- ----------------------------
DROP TABLE IF EXISTS `data_xplorer_logger`;
CREATE TABLE `data_xplorer_logger`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trace_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求的trace id',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作的方法类型',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url',
  `query` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径参数',
  `body` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'body参数',
  `action_time` int(11) NULL DEFAULT NULL COMMENT '操作耗时',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实访问者ip',
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被监听的服务名',
  `request_type` int(11) NULL DEFAULT NULL COMMENT '请求访问的类型 ，服务内部调用还是链路调用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '操作的时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作的人',
  `error_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '错误信息',
  `source_type` int(255) NOT NULL COMMENT '数据来源，自定义埋点还是全埋点',
  `insert_time` datetime NULL DEFAULT NULL COMMENT '插入的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_xplorer_dict_common
-- ----------------------------
DROP TABLE IF EXISTS `data_xplorer_dict_common`;
CREATE TABLE `data_xplorer_dict_common`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `dict_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编码',
  `dict_desc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `category_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类编码',
  `category_desc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类说明',
  `sort_no` int(8) UNSIGNED NOT NULL DEFAULT 999 COMMENT '排序编号',
  `data_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'STRING' COMMENT '数据类型',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附加说明',
  `locate_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检索标识',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '修改人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int(8) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_code_category_code`(`dict_code`, `category_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通用数据字典' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of data_xplorer_dict_common
-- ----------------------------
INSERT INTO `data_xplorer_dict_common` VALUES (1, '0', 'feign', 'request_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'request_type', '0', '0', '2023-09-25 05:14:10', '2023-09-25 05:19:33', 0);
INSERT INTO `data_xplorer_dict_common` VALUES (2, '1', 'interceptor', 'request_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'request_type', '0', '0', '2023-09-25 05:16:13', '2023-09-25 05:19:36', 0);
INSERT INTO `data_xplorer_dict_common` VALUES (4, '0', 'automatic', 'source_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'source_type', '0', '0', '2023-09-25 05:16:13', '2023-09-25 05:16:22', 0);
INSERT INTO `data_xplorer_dict_common` VALUES (5, '1', 'manual', 'source_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'source_type', '0', '0', '2023-09-25 05:16:13', '2023-09-25 05:16:22', 0);


CREATE TABLE data_xplorer_frontend_event_logger (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trace_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `event_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `element_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `element_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `page_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `event_time` datetime(0) NULL DEFAULT NULL,
  `source_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `others_body` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;