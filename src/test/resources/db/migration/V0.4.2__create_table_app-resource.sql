/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 app_resource 用于存储App和资源的关联信息，对应于对象 AppResource
 *
 * @author 潘凯，胡海星
 */
CREATE TABLE `app_resource` (
  `id`                            BIGINT        NOT NULL        COMMENT 'ID',
  `app_id`                        BIGINT        NOT NULL        COMMENT '所属App的ID，关联表app的id字段',
  `resource_type`                 VARCHAR(64)   NOT NULL        COMMENT '资源类型，枚举值',
  `resource_id`                   BIGINT        NOT NULL        COMMENT '资源ID',
  `create_time`                   DATETIME      NOT NULL        COMMENT '创建时间，UTC时区',
  `modify_time`                   DATETIME      DEFAULT NULL    COMMENT '最后一次修改时间，UTC时区',
  `delete_time`                   DATETIME      DEFAULT NULL    COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  FOREIGN KEY `app` (`app_id`) REFERENCES `app` (`id`) ON DELETE CASCADE,
  INDEX `resource`(`resource_type`, `resource_id`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储App和资源的关联信息，对应于对象AppResource';
