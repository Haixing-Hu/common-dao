/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 department 用于存储部门信息，对应于对象 Department
 *
 * @author 胡海星
 */
CREATE TABLE `department` (
  `id`                          BIGINT        NOT NULL                            COMMENT 'ID',
  `code`                        VARCHAR(64)   NOT NULL                            COMMENT '全局编码，全局不可重复',
  `internal_code`               VARCHAR(64)   DEFAULT NULL                        COMMENT '在所属机构内部编码，在所属机构内部不可重复',
  `name`                        VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL      COMMENT '名称，同一机构下不可重复',
  `organization_id`             BIGINT        NOT NULL                            COMMENT '所属机构ID，关联表organization的id字段',
  `parent_id`                   BIGINT        DEFAULT NULL                        COMMENT '上级部门ID，关联表department的id字段',
  `category_id`                 BIGINT        DEFAULT NULL                        COMMENT '所属类别ID，关联表category的id字段',
  `state`                       VARCHAR(64)   NOT NULL                            COMMENT '状态，枚举值',
  `icon`                        VARCHAR(512)  DEFAULT NULL                        COMMENT '图标',
  `description`                 TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '描述',
  `phone`                       VARCHAR(128)  DEFAULT NULL                        COMMENT '固定电话号码',
  `phone_verified`              VARCHAR(64)   DEFAULT NULL                        COMMENT '固定电话验证状态，枚举值',
  `mobile`                      VARCHAR(128)  DEFAULT NULL                        COMMENT '移动电话号码',
  `mobile_verified`             VARCHAR(64)   DEFAULT NULL                        COMMENT '移动电话验证状态，枚举值',
  `email`                       VARCHAR(512)  DEFAULT NULL                        COMMENT '电子邮件地址',
  `email_verified`              VARCHAR(64)   DEFAULT NULL                        COMMENT '电子邮件地址验证状态，枚举值',
  `url`                         VARCHAR(512)  DEFAULT NULL                        COMMENT '网址',
  `address_street_id`           BIGINT        DEFAULT NULL                        COMMENT '地址所属街道ID，关联表street的id字段',
  `address_detail`              VARCHAR(4096) CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '地址详细门牌号码',
  `address_postalcode`          VARCHAR(64)   DEFAULT NULL                        COMMENT '地址邮政编码',
  `address_location_latitude`   DECIMAL(9, 6) DEFAULT NULL                        COMMENT '地理位置维度',
  `address_location_longitude`  DECIMAL(9, 6) DEFAULT NULL                        COMMENT '地理位置经度',
  `address_verified`            VARCHAR(64)   DEFAULT NULL                        COMMENT '地址验证状态，枚举值',
  `predefined`                  BIT(1)        NOT NULL DEFAULT 0                  COMMENT '是否预定义数据',
  `create_time`                 DATETIME      NOT NULL                            COMMENT '创建时间，UTC时区',
  `modify_time`                 DATETIME      DEFAULT NULL                        COMMENT '最后一次修改时间，UTC时区',
  `delete_time`                 DATETIME      DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code` (`code`),
  UNIQUE INDEX `internal_code` (`organization_id`, `internal_code`),
  UNIQUE INDEX `name` (`organization_id`, `name`),
  FOREIGN KEY `organization` (`organization_id`) REFERENCES `organization` (`id`) ON DELETE CASCADE,
  FOREIGN KEY `parent` (`parent_id`) REFERENCES `department` (`id`) ON DELETE SET NULL,
  FOREIGN KEY `category` (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  INDEX `state` (`state`),
  INDEX `phone` (`phone`),
  INDEX `phone_verified` (`phone_verified`),
  INDEX `mobile` (`mobile`),
  INDEX `mobile_verified` (`mobile_verified`),
  INDEX `email` (`email`),
  INDEX `email_verified` (`email_verified`),
  INDEX `address_verified` (`address_verified`),
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储部门信息，对应于对象Department';
