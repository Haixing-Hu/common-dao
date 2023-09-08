/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 app 用于存储应用信息，对应于对象 App
 *
 * @author 胡海星
 */
CREATE TABLE `app` (
  `id`                          BIGINT        NOT NULL                            COMMENT 'ID',
  `code`                        VARCHAR(64)   NOT NULL                            COMMENT '代码，全局不重复',
  `name`                        VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL      COMMENT '名称，同一机构下不重复',
  `organization_id`             BIGINT        NOT NULL                            COMMENT '所属机构ID，关联表organization的id字段',
  `category_id`                 BIGINT        DEFAULT NULL                        COMMENT '所属类别ID，关联表category的id字段',
  `state`                       VARCHAR(64)   NOT NULL                            COMMENT '状态，枚举值',
  `icon`                        VARCHAR(512)  DEFAULT NULL                        COMMENT '图标',
  `url`                         VARCHAR(512)  DEFAULT NULL                        COMMENT '网址',
  `description`                 TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '描述',
  `comment`                     TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '备注',
  `security_key`                VARCHAR(4096) COLLATE ASCII_BIN NOT NULL          COMMENT '安全秘钥，存储加盐后的哈希值',
  `token_value`                 VARCHAR(128)  COLLATE ASCII_BIN DEFAULT NULL      COMMENT '存取令牌的值',
  `token_create_time`           DATETIME      DEFAULT NULL                        COMMENT '存取令牌的创建时间，UTC时区',
  `token_max_age`               BIGINT        DEFAULT NULL                        COMMENT '存取令牌的最大生存时间，单位为秒',
  `token_previous_value`        VARCHAR(128)  COLLATE ASCII_BIN DEFAULT NULL      COMMENT '上一次过期的存取令牌的值',
  `last_authorize_failures`     INT           NOT NULL DEFAULT 0                  COMMENT '上次获取授权重试错误次数',
  `last_authorize_time`         DATETIME      DEFAULT NULL                        COMMENT '上次获取授权时间，UTC时区',
  `predefined`                  BIT(1)        NOT NULL DEFAULT 0                  COMMENT '是否预定义数据',
  `create_time`                 DATETIME      NOT NULL                            COMMENT '创建时间，UTC时区',
  `modify_time`                 DATETIME      DEFAULT NULL                        COMMENT '最后一次修改时间，UTC时区',
  `delete_time`                 DATETIME      DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code` (`code`),
  UNIQUE INDEX `name` (`organization_id`, `name`),
  FOREIGN KEY `organization` (`organization_id`) REFERENCES `organization` (`id`) ON DELETE CASCADE,
  FOREIGN KEY `category` (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  INDEX `state` (`state`),
  UNIQUE INDEX `token` (`token_value`),
  INDEX `token_create_time` (`token_create_time` DESC),
  INDEX `token_max_age` (`token_max_age` DESC),
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储应用信息，对应于对象App';
