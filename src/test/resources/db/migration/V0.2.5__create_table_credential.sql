/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 credential 用于存储证件的信息，对应于对象 Credential
 *
 * @author 胡海星
 */
CREATE TABLE `credential` (
  `id`                    BIGINT        NOT NULL                              COMMENT 'ID',
  `type`                  VARCHAR(64)   NOT NULL                              COMMENT '类型，枚举值',
  `number`                VARCHAR(128)  NOT NULL                              COMMENT '号码',
  `verified`              VARCHAR(64)   DEFAULT NULL                          COMMENT '验证状态，枚举值',
  `owner_type`            VARCHAR(64)   NOT NULL                              COMMENT '所有者类型，枚举值',
  `owner_id`              BIGINT        NOT NULL                              COMMENT '所有者ID',
  `owner_property`        VARCHAR(64)   DEFAULT NULL                          COMMENT '所有者的实体的属性名称',
  `index`                 INT           NOT NULL DEFAULT 0                    COMMENT '此证件在所有者的指定属性的附件列表中的顺序',
  `title`                 VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '标题',
  `description`           TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '描述',
  `create_time`           DATETIME      NOT NULL                              COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME      DEFAULT NULL                          COMMENT '最后一次修改时间，UTC时区',
  `delete_time`           DATETIME      DEFAULT NULL                          COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  INDEX `number` (`type`, `number`)         COMMENT '注意不是 UNIQUE INDEX，即允许证件类型+号码重复',
  INDEX `verified` (`verified`),
  INDEX `owner` (`owner_type`, `owner_id`, `owner_property`),
  UNIQUE INDEX `index` (`owner_type`, `owner_id`, `owner_property`, `index` ASC),
  INDEX `title` (`title`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储证件的信息，对应于对象Credential';
