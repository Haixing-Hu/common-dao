/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 category 用于存储系统中实体的类别，对应于对象 Category
 *
 * @author 胡海星
 */
CREATE TABLE `category` (
  `id`                    BIGINT        NOT NULL                              COMMENT 'ID',
  `entity`                VARCHAR(64)   NOT NULL                              COMMENT '所属实体，枚举值',
  `code`                  VARCHAR(64)   CHARACTER SET UTF8MB4 NOT NULL        COMMENT '代码（允许中文），不可重复',
  `name`                  VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '名称，同App同实体内不可重复',
  `title`                 VARCHAR(4096) CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '显示标题',
  `icon`                  VARCHAR(512)  DEFAULT NULL                          COMMENT '图标',
  `description`           TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '描述',
  `parent_id`             BIGINT        DEFAULT NULL                          COMMENT '父类别ID，关联表category的ID字段',
  `predefined`            BIT(1)        NOT NULL DEFAULT 0                    COMMENT '是否预定义数据',
  `create_time`           DATETIME      NOT NULL                              COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME      DEFAULT NULL                          COMMENT '最后一次修改时间，UTC时区',
  `delete_time`           DATETIME      DEFAULT NULL                          COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code` (`code`),
  UNIQUE INDEX `name` (`entity`, `name`),
  INDEX `entity` (`entity`),
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储系统中实体的类别，对应于对象Category';

ALTER TABLE `category`
ADD FOREIGN KEY `parent` (`parent_id`)
REFERENCES `category` (`id`)
ON DELETE CASCADE;
