/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 payload 用于存储各类数据携带的额外信息，对应于对象 Payload
 *
 * @author 潘凯，胡海星
 */
CREATE TABLE `payload` (
  `id`              BIGINT        NOT NULL                        COMMENT 'ID',
  `owner_type`      VARCHAR(64)   NOT NULL                        COMMENT '所有者的实体类型，枚举值',
  `owner_id`        BIGINT        NOT NULL                        COMMENT '所有者的实体ID',
  `owner_property`  VARCHAR(64)   NOT NULL                        COMMENT '所有者的实体的属性名称',
  `key`             VARCHAR(128)  CHARSET UTF8MB4 NOT NULL        COMMENT '数据的key',
  `value`           VARCHAR(256)  CHARSET UTF8MB4 DEFAULT NULL    COMMENT '数据的value',
  `create_time`     DATETIME      NOT NULL                        COMMENT '创建时间，UTC时区',
  `modify_time`     DATETIME      DEFAULT NULL                    COMMENT '最后一次修改时间，UTC时区',
  `delete_time`     DATETIME      DEFAULT NULL                    COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  INDEX `owner` (`owner_type`, `owner_id`, `owner_property`),
  UNIQUE INDEX `key` (`owner_type`, `owner_id`, `owner_property`, `key`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) ENGINE=InnoDB CHARSET=ASCII COMMENT '存储各类数据携带的额外信息，对应于对象Payload。';
