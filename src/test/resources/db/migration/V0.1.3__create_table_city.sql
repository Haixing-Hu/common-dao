/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 city 用于存储城市信息，对应于对象 City
 *
 * @author 胡海星
 */
CREATE TABLE `city` (
  `id`                    BIGINT        NOT NULL                              COMMENT 'ID',
  `code`                  VARCHAR(64)   NOT NULL                              COMMENT '编码，不可重复',
  `name`                  VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '名称，同一省份内不可重复',
  `province_id`           BIGINT        NOT NULL                              COMMENT '所属省份ID，关联表province的id字段',
  `postalcode`            VARCHAR(64)   DEFAULT NULL                          COMMENT '邮政编码',
  `level`                 INT           DEFAULT NULL                          COMMENT '城市级别',
  `phone_area`            VARCHAR(16)   DEFAULT NULL                          COMMENT '电话区号',
  `icon`                  VARCHAR(512)  DEFAULT NULL                          COMMENT '图标',
  `url`                   VARCHAR(512)  DEFAULT NULL                          COMMENT '网址',
  `description`           TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '描述',
  `latitude`              DECIMAL(9, 6) DEFAULT NULL                          COMMENT '地理位置维度',
  `longitude`             DECIMAL(9, 6) DEFAULT NULL                          COMMENT '地理位置经度',
  `predefined`            BIT(1)        NOT NULL DEFAULT 0                    COMMENT '是否预定义数据',
  `create_time`           DATETIME      NOT NULL                              COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME      DEFAULT NULL                          COMMENT '最后一次修改时间，UTC时区',
  `delete_time`           DATETIME      DEFAULT NULL                          COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code` (`code`),
  UNIQUE INDEX `name` (`province_id`, `name`),
  FOREIGN KEY `province` (`province_id`) REFERENCES `province` (`id`) ON DELETE CASCADE,
  INDEX `postalcode` (`postalcode`),
  INDEX `level` (`level`),
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储城市信息，对应于对象City';
