/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 country 用于存储国家信息，对应于对象 Country
 *
 * @author 胡海星
 */
CREATE TABLE `country` (
  `id`                    BIGINT        NOT NULL                              COMMENT 'ID',
  `code`                  VARCHAR(64)   NOT NULL                              COMMENT 'ISO-3166 国家代码，不可重复',
  `name`                  VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '国家的名称，不可重复',
  `phone_area`            VARCHAR(16)   NOT NULL                              COMMENT '电话区号',
  `postalcode`            VARCHAR(64)   DEFAULT NULL                          COMMENT '邮政编码',
  `icon`                  VARCHAR(512)  DEFAULT NULL                          COMMENT '图标',
  `url`                   VARCHAR(512)  DEFAULT NULL                          COMMENT '网址',
  `description`           TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '描述',
  `predefined`            BIT(1)        NOT NULL DEFAULT 0                    COMMENT '是否预定义数据',
  `create_time`           DATETIME      NOT NULL                              COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME      DEFAULT NULL                          COMMENT '最后一次修改时间，UTC时区',
  `delete_time`           DATETIME      DEFAULT NULL                          COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code` (`code`),
  UNIQUE INDEX `name` (`name`),
  INDEX `phone_area` (`phone_area`),
  INDEX `postalcode` (`postalcode`),
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储国家信息，对应于对象Country';
