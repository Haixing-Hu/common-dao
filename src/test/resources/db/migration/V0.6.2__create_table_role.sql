/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 role 用于存储不同App中的系统角色，对应于对象 Role
 *
 * @author 孙建，胡海星
 */
CREATE TABLE `role` (
  `id`                      BIGINT          NOT NULL                            COMMENT 'ID',
  `app_id`                  BIGINT          NOT NULL                            COMMENT '所属APP的ID, 关联表app的id字段',
  `code`                    VARCHAR(64)     NOT NULL                            COMMENT '编码，同一个App下不可重复',
  `name`                    VARCHAR(128)    CHARACTER SET UTF8MB4 NOT NULL      COMMENT '名称，同一个App下不可重复',
  `description`             TEXT            CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '描述',
  `guest`                   BIT(1)          NOT NULL DEFAULT 0                  COMMENT '此角色是否是访客角色，访客不需要登录，每个App只能最多有一个访客角色',
  `basic`                   BIT(1)          NOT NULL DEFAULT 0                  COMMENT '此角色是否是基本角色，即用户注册后自动分配的角色，每个App只能最多有一个基本角色',
  `privileges`              TEXT            NOT NULL                            COMMENT '操作权限，多个用半角逗号隔开',
  `state`                   VARCHAR(64)     NOT NULL                            COMMENT '状态，枚举值',
  `create_time`             DATETIME        NOT NULL                            COMMENT '记录创建时间，UTC时区',
  `modify_time`             DATETIME        DEFAULT NULL                        COMMENT '记录最后一次修改时间，UTC时区',
  `delete_time`             DATETIME        DEFAULT NULL                        COMMENT '记录标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  FOREIGN KEY `app` (`app_id`) REFERENCES `app` (`id`) ON DELETE CASCADE,
  UNIQUE INDEX `code` (`app_id`, `code`),
  UNIQUE INDEX `name` (`app_id`, `name`),
  INDEX `guest` (`guest`),
  INDEX `basic` (`basic`),
  INDEX `state` (`state`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储不同App中的系统角色，对应于对象Role';
