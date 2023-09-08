/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 verify_code 用于存储验证码信息，对应于对象 VerifyCode
 *
 * @author 胡海星
 */
CREATE TABLE `verify_code` (
  `id`                    BIGINT        NOT NULL                            COMMENT 'ID',
  `app_id`                BIGINT        NOT NULL                            COMMENT '所属App的ID，关联表app的id字段',
  `mobile`                VARCHAR(128)  DEFAULT NULL                        COMMENT '手机号码',
  `email`                 VARCHAR(512)  DEFAULT NULL                        COMMENT '电子邮件地址',
  `scene`                 VARCHAR(64)   NOT NULL                            COMMENT '验证场景，枚举值',
  `code`                  VARCHAR(64)   COLLATE ASCII_BIN NOT NULL          COMMENT '验证码',
  `message`               TEXT          CHARACTER SET UTF8MB4 NOT NULL      COMMENT '发送的验证码消息',
  `verified`              BIT(1)        NOT NULL DEFAULT 0                  COMMENT '是否已验证过',
  `create_time`           DATETIME      NOT NULL                            COMMENT '创建时间',
  PRIMARY KEY (`id`),
  FOREIGN KEY `app` (`app_id`) REFERENCES `app` (`id`) ON DELETE CASCADE,
  INDEX `mobile` (`mobile`),
  INDEX `email` (`email`),
  INDEX `scene` (`scene`),
  INDEX `verified` (`verified`),
  INDEX `create_time` (`create_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储验证码信息，对应于对象VerifyCode';
