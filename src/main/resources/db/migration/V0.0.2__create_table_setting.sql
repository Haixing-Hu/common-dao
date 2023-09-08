/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 setting 用于存储系统设置，对应于对象 Setting
 *
 * @author 胡海星
 */
CREATE TABLE `setting` (
  `name`                  VARCHAR(256)        NOT NULL                              COMMENT '名称，主键',
  `type`                  VARCHAR(64)         NOT NULL                              COMMENT '类型，枚举',
  `readonly`              BIT(1)              NOT NULL DEFAULT 0                    COMMENT '是否只读',
  `nullable`              BIT(1)              NOT NULL DEFAULT 0                    COMMENT '是否可空',
  `multiple`              BIT(1)              NOT NULL DEFAULT 0                    COMMENT '是否是多值',
  `encrypted`             BIT(1)              NOT NULL DEFAULT 0                    COMMENT '是否加密',
  `description`           TEXT                CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '描述',
  `value`                 TEXT                CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '值，以字符串形式表示',
  `create_time`           DATETIME            NOT NULL                              COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME            DEFAULT NULL                          COMMENT '最后一次修改时间，UTC时区',
  UNIQUE INDEX `name` (`name`),
  INDEX `type` (`type`),
  INDEX `readonly` (`readonly`),
  INDEX `nullable` (`nullable`),
  INDEX `encrypted` (`encrypted`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储系统设置，对应于对象Setting';
