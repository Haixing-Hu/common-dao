/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 user 用于存储用户信息，对应于对象 User
 *
 * @author 胡海星
 */
CREATE TABLE `user` (
  `id`                    BIGINT        NOT NULL                            COMMENT 'ID',
  `username`              VARCHAR(64)   NOT NULL                            COMMENT '用户名，全局不可重复',
  `password`              VARCHAR(64)   COLLATE ascii_bin NOT NULL          COMMENT '密码，存储加盐后的哈希值，可以为空，为空则需采用其他登录方式',
  `name`                  VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '姓名',
  `nickname`              VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '昵称',
  `gender`                VARCHAR(64)   DEFAULT NULL                        COMMENT '性别，枚举值',
  `mobile`                VARCHAR(128)  DEFAULT NULL                        COMMENT '移动电话号码，全局不可重复',
  `mobile_verified`       VARCHAR(64)   DEFAULT NULL                        COMMENT '移动电话验证状态，枚举值',
  `email`                 VARCHAR(512)  DEFAULT NULL                        COMMENT '电子邮件地址，全局不可重复',
  `email_verified`        VARCHAR(64)   DEFAULT NULL                        COMMENT '电子邮件地址验证状态，枚举值',
  `avatar`                VARCHAR(512)  DEFAULT NULL                        COMMENT '头像',
  `url`                   VARCHAR(512)  DEFAULT NULL                        COMMENT '网址',
  `description`           TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '描述，个人介绍',
  `organization_id`       BIGINT        DEFAULT NULL                        COMMENT '所属机构ID，关联表organization的id字段',
  `state`                 VARCHAR(64)   NOT NULL                            COMMENT '用户状态，枚举值',
  `last_login_failures`   INT           NOT NULL DEFAULT 0                  COMMENT '上次登录重试错误次数',
  `last_login_time`       DATETIME      DEFAULT NULL                        COMMENT '上次登录时间，UTC时区',
  `change_password`       BIT(1)        NOT NULL DEFAULT 0                  COMMENT '下次登录是否需要更换密码',
  `valid_time`            DATETIME      DEFAULT NULL                        COMMENT '账号生效时间，UTC时区',
  `expired_time`          DATETIME      DEFAULT NULL                        COMMENT '账号过期时间，UTC时区',
  `comment`               TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '备注',
  `predefined`            BIT(1)        NOT NULL DEFAULT 0                  COMMENT '是否预定义数据',
  `create_time`           DATETIME      NOT NULL                            COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME      DEFAULT NULL                        COMMENT '最后一次修改时间，UTC时区',
  `delete_time`           DATETIME      DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username` (`username`),
  UNIQUE INDEX `mobile` (`mobile`),
  UNIQUE INDEX `email` (`email`),
  INDEX `name` (`name`),
  INDEX `nickname` (`nickname`),
  INDEX `gender` (`gender`),
  FOREIGN KEY `organization` (`organization_id`) REFERENCES `organization` (`id`) ON DELETE SET NULL,
  INDEX `state` (`state`),
  INDEX `last_login_time` (`last_login_time` DESC),
  INDEX `valid_time` (`valid_time` DESC),
  INDEX `expired_time` (`expired_time` DESC),
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储用户信息，对应于对象User';
