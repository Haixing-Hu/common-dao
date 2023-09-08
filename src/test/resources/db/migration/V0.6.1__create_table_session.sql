/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 session 用于存储登录会话信息，对应于对象 Session
 *
 * @author 胡海星
 */
CREATE TABLE `session` (
  `id`                      BIGINT        NOT NULL                        COMMENT 'ID',
  `app_id`                  BIGINT        NOT NULL                        COMMENT '会话发起App的ID，关联表app的id字段',
  `user_id`                 BIGINT        DEFAULT NULL                    COMMENT '会话发起用户的ID，关联表user的id字段',
  `token_value`             VARCHAR(128)  COLLATE ASCII_BIN NOT NULL      COMMENT '会话发起用户的访问令牌的值',
  `token_create_time`       DATETIME      NOT NULL                        COMMENT '会话发起用户的访问令牌的创建时间，UTC时区',
  `token_max_age`           BIGINT        DEFAULT NULL                    COMMENT '会话发起用户的访问令牌的最大生存时间，单位为秒',
  `token_previous_value`    VARCHAR(128)  COLLATE ASCII_BIN DEFAULT NULL  COMMENT '会话发起用户的访问令牌上一次过期的值',
  `roles`                   TEXT          DEFAULT NULL                    COMMENT '会话发起用户拥有的权限列表，逗号隔开',
  `privileges`              TEXT          DEFAULT NULL                    COMMENT '会话发起用户拥有的权限列表，逗号隔开',
  `ip`                      VARCHAR(128)  DEFAULT NULL                    COMMENT '客户端IP地址',
  `location_latitude`       DECIMAL(9, 6) DEFAULT NULL                    COMMENT '客户端GPS位置维度',
  `location_longitude`      DECIMAL(9, 6) DEFAULT NULL                    COMMENT '客户端GPS位置经度',
  `platform`                VARCHAR(64)   DEFAULT NULL                    COMMENT '客户端平台，枚举值',
  `udid`                    VARCHAR(64)   COLLATE ascii_bin DEFAULT NULL  COMMENT '客户端UDID',
  `push_token`              VARCHAR(64)   COLLATE ascii_bin DEFAULT NULL  COMMENT '客户端推送通知令牌',
  `last_active_time`        DATETIME      NOT NULL                        COMMENT '上次活动时间',
  `expired_time`            DATETIME      DEFAULT NULL                    COMMENT '会话过期时间',
  `expired_reason`          VARCHAR(64)   DEFAULT NULL                    COMMENT '会话过期原因，枚举值',
  `create_time`             DATETIME      NOT NULL                        COMMENT '会话创建时间，即用户登录时间，UTC时区',
  PRIMARY KEY (`id`),
  FOREIGN KEY `app` (`app_id`) REFERENCES `app` (`id`) ON DELETE CASCADE,
  FOREIGN KEY `user` (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  UNIQUE INDEX `token` (`token_value`),
  INDEX `ip` (`ip`),
  INDEX `latitude` (`location_latitude`),
  INDEX `longitude` (`location_longitude`),
  INDEX `platform` (`platform`),
  INDEX `udid` (`udid`),
  INDEX `expired_time` (`expired_time`),
  INDEX `create_time` (`create_time`)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储登录会话信息，对应于对象Session';
