/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 host 用于存储应用所在服务器信息，对应于对象`Host`
 *
 * @author 胡海星
 */
CREATE TABLE `host` (
  `id`          BIGINT                                NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `provider`    VARCHAR(128) CHARACTER SET UTF8MB4    NOT NULL                COMMENT '主机供应商名称',
  `udid`        VARCHAR(128) COLLATE ASCII_BIN        NOT NULL                COMMENT '主机UDID，不可重复',
  PRIMARY KEY (`id`),
  INDEX `provider` (`provider`),
  UNIQUE INDEX `udid` (`udid`)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储应用所在服务器信息，对应于对象Host';
