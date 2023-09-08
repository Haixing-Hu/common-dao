/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 dict_entry 用于存储字典条目，对应于对象 DictEntry
 *
 * @author 胡海星
 */
CREATE TABLE `dict_entry` (
  `id`                  BIGINT        NOT NULL                            COMMENT 'ID',
  `dict_id`             BIGINT        NOT NULL                            COMMENT '所属字典ID，关联表dict的id字段',
  `code`                VARCHAR(64)   NOT NULL                            COMMENT '编码，同一字典中唯一',
  `name`                VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL      COMMENT '名称',
  `description`         TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '描述',
  `comment`             TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '备注',
  `parent_id`           BIGINT        DEFAULT NULL                        COMMENT '父条目ID，关联表dict_entry的id字段',
  `create_time`         DATETIME      NOT NULL                            COMMENT '创建时间，UTC时区',
  `modify_time`         DATETIME      DEFAULT NULL                        COMMENT '最后一次修改时间，UTC时区',
  `delete_time`         DATETIME      DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  FOREIGN KEY `dict` (`dict_id`) REFERENCES `dict` (`id`) ON DELETE CASCADE,
  UNIQUE INDEX `code` (`dict_id`, `code`),
  INDEX `name` (`name`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `modify_time` (`modify_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储字典条目，对应于对象DictEntry';
