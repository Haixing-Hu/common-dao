/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 attachment 用于存储附件信息，对应于对象 Attachment
 *
 * @author 胡海星
 */
CREATE TABLE `attachment` (
  `id`                    BIGINT        NOT NULL                              COMMENT 'ID',
  `owner_type`            VARCHAR(64)   NOT NULL                              COMMENT '所有者对象类型',
  `owner_id`              BIGINT        NOT NULL                              COMMENT '所有者对象ID',
  `owner_property`        VARCHAR(64)   DEFAULT NULL                          COMMENT '所有者的实体的属性名称',
  `type`                  VARCHAR(64)   NOT NULL                              COMMENT '附件类型',
  `category_id`           BIGINT        DEFAULT NULL                          COMMENT '分类ID，关联表category的id字段',
  `index`                 INT           NOT NULL DEFAULT 0                    COMMENT '此附件在所有者的指定属性的附件列表中的顺序',
  `title`                 VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '标题',
  `description`           TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '描述',
  `upload_id`             BIGINT        NOT NULL                              COMMENT '对应的上传文件的ID，关联表upload的id字段',
  `state`                 VARCHAR(64)   NOT NULL                              COMMENT '状态，枚举值',
  `visible`               BIT           NOT NULL DEFAULT 1                    COMMENT '是否可见',
  `create_time`           DATETIME      NOT NULL                              COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME      DEFAULT NULL                          COMMENT '最后一次修改时间，UTC时区',
  `delete_time`           DATETIME      DEFAULT NULL                          COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  INDEX `owner` (`owner_type`, `owner_id`, `owner_property`),
  INDEX `type` (`type`),
  FOREIGN KEY `category` (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  UNIQUE INDEX `index` (`owner_type`, `owner_id`, `owner_property`, `index` ASC),
  INDEX `title` (`title`),
  FOREIGN KEY `upload` (`upload_id`) REFERENCES `upload` (`id`) ON DELETE RESTRICT,
  INDEX `state` (`state`),
  INDEX `visible` (`visible`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储附件信息，对应于对象Attachment';
