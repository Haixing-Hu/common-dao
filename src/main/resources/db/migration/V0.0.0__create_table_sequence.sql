/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/
/*******************************************************************************
 *
 *    Copyright (c) 2017 - 2020.
 *    Nanjing Smart Medical Investment Operation Service Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 sequence 存储从1开始的整数序列，用于辅助实现一些统计查询功能，例如统计过去24小时
 * 销售量。
 *
 * @author 胡海星
 */
CREATE TABLE `sequence` (
  `id`  INT NOT NULL AUTO_INCREMENT COMMENT '自增ID，用于存储从1开始的整数序列',
  PRIMARY KEY (`id`)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储从1开始的整数序列，用于辅助实现一些统计查询功能';

/**
 * 此存储过程用于在`sequence`表中插入指定数目的行，从而利用 AUTO_INCREMENT 的`id`字段
 * 生成连续的序列。
 */
DELIMITER |
CREATE PROCEDURE GENERATE_SEQUENCE(IN n INT)
COMMENT '在`sequence`表中插入指定的n行'
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < n DO
        INSERT INTO `sequence` () values ();
        SET i = i + 1;
    END WHILE;
END;
|

/*
 * 生成从1--10000的整数序列
 */
CALL GENERATE_SEQUENCE(10000)
