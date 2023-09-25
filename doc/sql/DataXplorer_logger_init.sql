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
  `type` int(11) NULL DEFAULT NULL COMMENT '请求访问的类型 ，服务内部调用还是链路调用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '操作的时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作的人',
  `error_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '错误信息',
  `source_type` int(255) NOT NULL COMMENT '数据来源，自定义埋点还是全埋点',
  `insert_time` datetime NULL DEFAULT NULL COMMENT '插入的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;