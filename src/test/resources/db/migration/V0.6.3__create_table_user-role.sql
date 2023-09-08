/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 user_role 用于存储用户的角色信息，对应于对象 UserRole
 *
 * @author 孙建
 */
CREATE TABLE `user_role` (
  `id`                      BIGINT          NOT NULL                    COMMENT 'ID',
  `user_id`                 BIGINT          NOT NULL                    COMMENT '用户ID，关联表user的id字段',
  `app_id`                  BIGINT          NOT NULL                    COMMENT '所属APP的ID, 关联表app的id字段',
  `role_id`                 BIGINT          NOT NULL                    COMMENT '用户角色的ID，关联表role的id字段',
  `create_time`             DATETIME        NOT NULL                    COMMENT '记录创建时间，UTC时区',
  `delete_time`             DATETIME        DEFAULT NULL                COMMENT '记录标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  FOREIGN KEY `user` (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  FOREIGN KEY `app` (`app_id`) REFERENCES `app` (`id`) ON DELETE CASCADE,
  FOREIGN KEY `role_reference` (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  UNIQUE INDEX `role` (`user_id`, `app_id`, `role_id`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储用户的角色信息，对应于对象UserRole';
