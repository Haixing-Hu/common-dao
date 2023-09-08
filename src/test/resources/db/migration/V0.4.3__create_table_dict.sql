/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 dict 用于存储字典信息，对应于对象 Dict
 *
 * @author 胡海星
 */
CREATE TABLE `dict` (
  `id`                          BIGINT          NOT NULL                            COMMENT 'ID',
  `code`                        VARCHAR(64)     NOT NULL                            COMMENT '代码，不可重复',
  `name`                        VARCHAR(128)    CHARACTER SET UTF8MB4 NOT NULL      COMMENT '名称',
  `standard_doc`                VARCHAR(128)    CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '所遵循的标准规范文件',
  `standard_code`               VARCHAR(64)     CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '在其所遵循的标准规范文件中的编码',
  `url`                         VARCHAR(512)    DEFAULT NULL                        COMMENT '网址',
  `description`                 TEXT            CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '描述',
  `comment`                     TEXT            CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '备注',
  `app_id`                      BIGINT          DEFAULT NULL                        COMMENT '所属App的ID，关联表app的id字段',
  `category_id`                 BIGINT          DEFAULT NULL                        COMMENT '所属类别ID，关联表category的id字段',
  `state`                       VARCHAR(64)     NOT NULL                            COMMENT '状态，枚举值',
  `predefined`                  BIT(1)          NOT NULL DEFAULT 0                  COMMENT '是否预定义数据',
  `create_time`                 DATETIME        NOT NULL                            COMMENT '创建时间，UTC时区',
  `modify_time`                 DATETIME        DEFAULT NULL                        COMMENT '最后一次修改时间，UTC时区',
  `delete_time`                 DATETIME        DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code` (`code`),
  INDEX `name` (`name`),
  INDEX `standard_doc` (`standard_doc`),
  INDEX `standard_code` (`standard_code`),
  FOREIGN KEY `app` (`app_id`) REFERENCES `app` (`id`) ON DELETE CASCADE,
  FOREIGN KEY `category` (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  INDEX `state` (`state`),
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储字典信息，对应于对象Dict';
