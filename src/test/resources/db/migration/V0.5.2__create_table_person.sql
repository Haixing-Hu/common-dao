/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 person 用于存储个人档案资料，对应于对象 Person
 *
 * @author 胡海星
 */
CREATE TABLE `person` (
  `id`                                  BIGINT        NOT NULL                            COMMENT 'ID',
  `source_id`                           BIGINT        DEFAULT NULL                        COMMENT '来源ID，关联表source的id字段',
  `name`                                VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL      COMMENT '姓名',
  `username`                            VARCHAR(64)   DEFAULT NULL                        COMMENT '所对应用户的用户名，关联表user的username字段，可以为空',
  `gender`                              VARCHAR(64)   DEFAULT NULL                        COMMENT '性别，枚举值',
  `birthday`                            DATE          DEFAULT NULL                        COMMENT '出生日期',
  `has_medicare`                        BIT(1)        DEFAULT NULL                        COMMENT '是否有医保',
  `medicare_type`                       VARCHAR(64)   DEFAULT NULL                        COMMENT '医保类别，枚举值',
  `medicare_city_id`                    BIGINT        DEFAULT NULL                        COMMENT '医保所在城市ID，关联表city的id字段',
  `has_social_security`                 BIT(1)        DEFAULT NULL                        COMMENT '是否有社保',
  `social_security_city_id`             BIGINT        DEFAULT NULL                        COMMENT '社保所在城市ID，关联表city的id字段',
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
  `guardian_id`                         BIGINT        DEFAULT NULL                        COMMENT '监护人信息ID，关联表person的id字段',
  `education`                           VARCHAR(64)   DEFAULT NULL                        COMMENT '文化程度，枚举值',
  `ethnic`                              VARCHAR(64)   DEFAULT NULL                        COMMENT '民族，枚举值',
  `blood`                               VARCHAR(64)   DEFAULT NULL                        COMMENT '血型，枚举值',
  `marriage`                            VARCHAR(64)   DEFAULT NULL                        COMMENT '婚姻状况，枚举值',
  `has_child`                           BIT(1)        DEFAULT NULL                        COMMENT '有无子女',
  `sex_orientation`                     VARCHAR(64)   DEFAULT NULL                        COMMENT '性取向，枚举值',
  `religion`                            VARCHAR(64)   DEFAULT NULL                        COMMENT '宗教信仰，枚举值',
  `politics`                            VARCHAR(64)   DEFAULT NULL                        COMMENT '政治面貌，枚举值',
  `industry`                            VARCHAR(64)   DEFAULT NULL                        COMMENT '行业，枚举值',
  `job`                                 VARCHAR(64)   CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '职业',
  `job_title`                           VARCHAR(64)   CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '职称',
  `incoming`                            VARCHAR(512)  DEFAULT NULL                        COMMENT '收入等级，枚举值',
  `organization_id`                     BIGINT        DEFAULT NULL                        COMMENT '工作单位ID，关联表organization的id字段，如工作单位不在系统内则此字段为空',
  `organization_name`                   VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '工作单位名称',
  `height`                              INT           DEFAULT NULL                        COMMENT '身高，单位厘米',
  `weight`                              INT           DEFAULT NULL                        COMMENT '体重，单位千克',
  `allergic_history`                    TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '过敏史',
  `comment`                             TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '备注',
  `create_time`                         DATETIME      NOT NULL                            COMMENT '创建时间，UTC时区',
  `modify_time`                         DATETIME      DEFAULT NULL                        COMMENT '最后一次修改时间，UTC时区',
  `delete_time`                         DATETIME      DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  INDEX `name` (`name`),
  FOREIGN KEY `username` (`username`) REFERENCES `user` (`username`) ON DELETE SET NULL,
  INDEX `gender` (`gender`),
  INDEX `birthday` (`birthday`),
  INDEX `has_medicare` (`has_medicare`),
  INDEX `medicare_type` (`medicare_type`),
  FOREIGN KEY `medicare_city` (`medicare_city_id`) REFERENCES `city` (`id`) ON DELETE SET NULL,
  INDEX `has_social_security` (`has_social_security`),
  FOREIGN KEY `social_security_city` (`social_security_city_id`) REFERENCES `city` (`id`) ON DELETE SET NULL,
  INDEX `phone` (`contact_phone`),
  INDEX `phone_verified` (`contact_phone_verified`),
  INDEX `mobile` (`contact_mobile`),
  INDEX `mobile_verified` (`contact_mobile_verified`),
  INDEX `email` (`contact_email`),
  INDEX `email_verified` (`contact_email_verified`),
  FOREIGN KEY `street` (`contact_address_street_id`) REFERENCES `street` (`id`) ON DELETE SET NULL,
  INDEX `postalcode` (`contact_address_postalcode`),
  INDEX `address_verified` (`contact_address_verified`),
  INDEX `has_child` (`has_child`),
  FOREIGN KEY `organization` (`organization_id`) REFERENCES `organization` (`id`) ON DELETE SET NULL,
  INDEX `organization_name` (`organization_name`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储个人档案资料，对应于对象Person';
