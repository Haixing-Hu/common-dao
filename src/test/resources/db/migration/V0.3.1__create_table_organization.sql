/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 organization 用于存储组织机构信息，对应于对象 Organization
 *
 * @author 胡海星
 */
CREATE TABLE `organization` (
  `id`                                  BIGINT        NOT NULL                            COMMENT 'ID',
  `code`                                VARCHAR(64)   NOT NULL                            COMMENT '代码，不可重复',
  `name`                                VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL      COMMENT '名称，不可重复',
  `category_id`                         BIGINT        DEFAULT NULL                        COMMENT '所属类别ID，关联表category的id字段',
  `parent_id`                           BIGINT        DEFAULT NULL                        COMMENT '上级机构ID，关联表organization的id字段',
  `state`                               VARCHAR(64)   NOT NULL                            COMMENT '状态，枚举值',
  `icon`                                VARCHAR(512)  DEFAULT NULL                        COMMENT '图标',
  `description`                         TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '描述',
  `comment`                             TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '备注',
  `contact_phone`                       VARCHAR(128)  DEFAULT NULL                        COMMENT '固定电话号码',
  `contact_phone_verified`              VARCHAR(64)   DEFAULT NULL                        COMMENT '固定电话验证状态，枚举值',
  `contact_mobile`                      VARCHAR(128)  DEFAULT NULL                        COMMENT '移动电话号码',
  `contact_mobile_verified`             VARCHAR(64)   DEFAULT NULL                        COMMENT '移动电话验证状态，枚举值',
  `contact_email`                       VARCHAR(512)  DEFAULT NULL                        COMMENT '电子邮件地址',
  `contact_email_verified`              VARCHAR(64)   DEFAULT NULL                        COMMENT '电子邮件地址验证状态，枚举值',
  `contact_url`                         VARCHAR(512)  DEFAULT NULL                        COMMENT '网址',
  `contact_address_street_id`           BIGINT        DEFAULT NULL                        COMMENT '地址所属街道ID，关联表street的id字段',
  `contact_address_detail`              VARCHAR(4096) CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '地址详细门牌号码',
  `contact_address_postalcode`          VARCHAR(64)   DEFAULT NULL                        COMMENT '地址邮政编码',
  `contact_address_location_latitude`   DECIMAL(9, 6) DEFAULT NULL                        COMMENT '地理位置维度',
  `contact_address_location_longitude`  DECIMAL(9, 6) DEFAULT NULL                        COMMENT '地理位置经度',
  `contact_address_verified`            VARCHAR(64)   DEFAULT NULL                        COMMENT '地址验证状态，枚举值',
  `principal_id`                        BIGINT        DEFAULT NULL                        COMMENT '法人或负责人的ID',
  `principal_name`                      VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '法人或负责人的姓名',
  `principal_username`                  VARCHAR(64)   DEFAULT NULL                        COMMENT '法人或负责人所对应用户的用户名，关联表user的username字段，可以为空',
  `principal_gender`                    VARCHAR(64)   DEFAULT NULL                        COMMENT '法人或负责人的性别，枚举值',
  `principal_birthday`                  DATE          DEFAULT NULL                        COMMENT '法人或负责人的出生日期',
  `principal_credential_id`             BIGINT        DEFAULT NULL                        COMMENT '法人或负责人的身份证件ID',
  `principal_credential_type`           VARCHAR(64)   DEFAULT NULL                        COMMENT '法人或负责人的身份证件类型，枚举值',
  `principal_credential_number`         VARCHAR(128)  DEFAULT NULL                        COMMENT '法人或负责人的身份证件号码',
  `principal_credential_verified`       VARCHAR(64)   DEFAULT NULL                        COMMENT '法人或负责人的身份证件验证状态，枚举值',
  `principal_contact_mobile`            VARCHAR(128)  DEFAULT NULL                        COMMENT '法人或负责人的移动电话号码',
  `principal_contact_email`             VARCHAR(512)  DEFAULT NULL                        COMMENT '法人或负责人的电子邮件地址',
  `tax_payer_type`                      VARCHAR(64)   DEFAULT NULL                        COMMENT '纳税人类型',
  `tax_number`                          VARCHAR(64)   DEFAULT NULL                        COMMENT '纳税号',
  `predefined`                          BIT(1)        NOT NULL DEFAULT 0                  COMMENT '是否预定义数据',
  `create_time`                         DATETIME      NOT NULL                            COMMENT '创建时间，UTC时区',
  `modify_time`                         DATETIME      DEFAULT NULL                        COMMENT '最后一次修改时间，UTC时区',
  `delete_time`                         DATETIME      DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name` (`name`),
  UNIQUE INDEX `code` (`code`),
  FOREIGN KEY `category` (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  FOREIGN KEY `parent` (`parent_id`) REFERENCES `organization` (`id`) ON DELETE SET NULL,
  INDEX `state` (`state`),
  INDEX `phone` (`contact_phone`),
  INDEX `phone_verified` (`contact_phone_verified`),
  INDEX `mobile` (`contact_mobile`),
  INDEX `mobile_verified` (`contact_mobile_verified`),
  INDEX `email` (`contact_email`),
  INDEX `email_verified` (`contact_email_verified`),
  FOREIGN KEY `address_street` (`contact_address_street_id`) REFERENCES `street` (`id`) ON DELETE SET NULL,
  INDEX `address_verified` (`contact_address_verified`),
  INDEX `principal_name` (`principal_name`),
  INDEX `principal_username` (`principal_username`),
  INDEX `principal_mobile` (`principal_contact_mobile`),
  INDEX `principal_email` (`principal_contact_email`),
  INDEX `principal_credential` (`principal_credential_type`, `principal_credential_number`),
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储组织机构信息，对应于对象Organization';
