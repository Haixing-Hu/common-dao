/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 social_network_account 用于存储用户的社交网络账号信息，对应于对象 SocialNetworkAccount
 *
 * @author 胡海星
 */
CREATE TABLE `social_network_account` (
  `id`                    BIGINT        NOT NULL                            COMMENT 'ID',
  `username`              VARCHAR(64)   NOT NULL                            COMMENT '用户名，关联于表user的username字段',
  `social_network`        VARCHAR(64)   NOT NULL                            COMMENT '社交网络类型，枚举值',
  `open_id`               VARCHAR(64)   NOT NULL                            COMMENT '社交网络上的OpenId，在同一社交网络下不可重复',
  `nickname`              VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '社交网络上的昵称',
  `profiles`              TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '通过key-value键值形式存储用户的个人信息',
  `create_time`           DATETIME      NOT NULL                            COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME      DEFAULT NULL                        COMMENT '最后一次修改时间，UTC时区',
  `delete_time`           DATETIME      DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  FOREIGN KEY `username` (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE,
  INDEX `social_network` (`social_network`),
  UNIQUE INDEX `open_id` (`social_network`, `open_id`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储用户的社交网络账号信息，对应于对象SocialNetworkAccount';
