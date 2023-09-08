/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 source 用于存储实体对象的来源渠道，对应于对象 Source
 *
 * @author 胡海星
 */
CREATE TABLE `source` (
  `id`                    BIGINT        NOT NULL                              COMMENT 'ID',
  `code`                  VARCHAR(64)   CHARACTER SET UTF8MB4 NOT NULL        COMMENT '代码（允许中文），不可重复',
  `name`                  VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '名称，同App的同实体内不可重复',
  `app_id`                BIGINT        NOT NULL                              COMMENT '所属APP的ID，关联表app的id字段',
  `category_id`           BIGINT        DEFAULT NULL                          COMMENT '所属类别的ID，关联表category的id字段',
  `entity`                VARCHAR(64)   NOT NULL                              COMMENT '所属实体，枚举值',
  `description`           TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '描述',
  `provider_app_id`       BIGINT        DEFAULT NULL                          COMMENT '供应商App ID，关联表app的id字段',
  `provider_org_id`       BIGINT        DEFAULT NULL                          COMMENT '供应商机构 ID，关联表organization的id字段',
  `predefined`            BIT(1)        NOT NULL DEFAULT 0                    COMMENT '是否预定义数据',
  `create_time`           DATETIME      NOT NULL                              COMMENT '创建时间，UTC时区',
  `modify_time`           DATETIME      DEFAULT NULL                          COMMENT '最后一次修改时间，UTC时区',
  `delete_time`           DATETIME      DEFAULT NULL                          COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code` (`code`),
  UNIQUE INDEX `name` (`app_id`, `entity`, `name`),
  INDEX `entity` (`entity`),
  FOREIGN KEY `app` (`app_id`) REFERENCES `app` (`id`) ON DELETE CASCADE,
  FOREIGN KEY `category` (`category_id`) REFERENCES `category` (`id`) ON DELETE SET NULL,
  FOREIGN KEY `provider_app` (`provider_app_id`) REFERENCES `app` (`id`) ON DELETE SET NULL,
  FOREIGN KEY `provider_org` (`provider_org_id`) REFERENCES `organization` (`id`) ON DELETE SET NULL,
  INDEX `predefined` (`predefined`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储实体对象的来源渠道，对应于对象Source';
