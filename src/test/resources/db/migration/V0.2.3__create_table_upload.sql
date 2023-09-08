/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表 upload 用于存储上传的文件的信息，对应于对象 Upload
 *
 * @author 胡海星
 */
CREATE TABLE `upload` (
  `id`                            BIGINT        NOT NULL                            COMMENT 'ID',
  `original_filename`             VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL  COMMENT '原始文件的原始文件名，可能包含中文',
  `type`                          VARCHAR(64)   NOT NULL                            COMMENT '附件类型',
  `file_path`                     VARCHAR(512)  NOT NULL                            COMMENT '原始文件的路径，只能包含ASCII字符',
  `file_format`                   VARCHAR(128)  DEFAULT NULL                        COMMENT '原始文件的格式',
  `file_width`                    INT           DEFAULT NULL                        COMMENT '原始文件的宽度，单位为像素',
  `file_height`                   INT           DEFAULT NULL                        COMMENT '原始文件的高度，单位为像素',
  `file_size`                     BIGINT        DEFAULT NULL                        COMMENT '原始文件的大小，单位为字节',
  `file_duration`                 INT           DEFAULT NULL                        COMMENT '原始文件的时长，单位为秒',
  `file_quality`                  DECIMAL(5,2)  DEFAULT NULL                        COMMENT '原始文件的压缩质量，单位为100%',
  `file_content_type`             VARCHAR(128)  DEFAULT NULL                        COMMENT '原始文件的Content-type',
  `screenshot_path`               VARCHAR(512)  DEFAULT NULL                        COMMENT '截屏文件的路径，只能包含ASCII字符',
  `screenshot_format`             VARCHAR(128)  DEFAULT NULL                        COMMENT '截屏文件的格式',
  `screenshot_width`              INT           DEFAULT NULL                        COMMENT '截屏文件的宽度，单位为像素',
  `screenshot_height`             INT           DEFAULT NULL                        COMMENT '截屏文件的高度，单位为像素',
  `screenshot_duration`           INT           DEFAULT NULL                        COMMENT '截屏文件的时长，单位为秒；此字段存在仅为了保持数据结构一致性',
  `screenshot_size`               BIGINT        DEFAULT NULL                        COMMENT '截屏文件的大小，单位为字节',
  `screenshot_quality`            DECIMAL(5,2)  DEFAULT NULL                        COMMENT '截屏文件的压缩质量，单位为100%',
  `screenshot_content_type`       VARCHAR(128)  DEFAULT NULL                        COMMENT '截屏文件的Content-type',
  `small_thumbnail_path`          VARCHAR(512)  DEFAULT NULL                        COMMENT '小缩略图文件的路径，只能包含ASCII字符',
  `small_thumbnail_format`        VARCHAR(128)  DEFAULT NULL                        COMMENT '小缩略图文件的格式',
  `small_thumbnail_width`         INT           DEFAULT NULL                        COMMENT '小缩略图文件的宽度，单位为像素',
  `small_thumbnail_height`        INT           DEFAULT NULL                        COMMENT '小缩略图文件的高度，单位为像素',
  `small_thumbnail_duration`      INT           DEFAULT NULL                        COMMENT '小缩略图文件的时长，单位为秒；此字段存在仅为了保持数据结构一致性',
  `small_thumbnail_size`          BIGINT        DEFAULT NULL                        COMMENT '小缩略图文件的大小，单位为字节',
  `small_thumbnail_quality`       DECIMAL(5,2)  DEFAULT NULL                        COMMENT '小缩略图文件的压缩质量，单位为100%',
  `small_thumbnail_content_type`  VARCHAR(128)  DEFAULT NULL                        COMMENT '小缩略图文件的Content-type',
  `large_thumbnail_path`          VARCHAR(512)  DEFAULT NULL                        COMMENT '大缩略图文件的路径，只能包含ASCII字符',
  `large_thumbnail_format`        VARCHAR(128)  DEFAULT NULL                        COMMENT '大缩略图文件的格式',
  `large_thumbnail_width`         INT           DEFAULT NULL                        COMMENT '大缩略图文件的宽度，单位为像素',
  `large_thumbnail_height`        INT           DEFAULT NULL                        COMMENT '大缩略图文件的高度，单位为像素',
  `large_thumbnail_duration`      INT           DEFAULT NULL                        COMMENT '大缩略图文件的时长，单位为秒；此字段存在仅为了保持数据结构一致性',
  `large_thumbnail_size`          BIGINT        DEFAULT NULL                        COMMENT '大缩略图文件的大小，单位为字节',
  `large_thumbnail_quality`       DECIMAL(5,2)  DEFAULT NULL                        COMMENT '大缩略图文件的压缩质量，单位为100%',
  `large_thumbnail_content_type`  VARCHAR(128)  DEFAULT NULL                        COMMENT '大缩略图文件的Content-type',
  `create_time`                   DATETIME      NOT NULL                            COMMENT '创建时间，UTC时区',
  `delete_time`                   DATETIME      DEFAULT NULL                        COMMENT '标记删除时间，UTC时区',
  PRIMARY KEY (`id`),
  INDEX `type` (`type`),
  INDEX `file_path`(`file_path`),
  INDEX `screenshot_path`(`screenshot_path`),
  INDEX `small_thumbnail_path`(`small_thumbnail_path`),
  INDEX `large_thumbnail_path`(`large_thumbnail_path`),
  INDEX `create_time` (`create_time` DESC),
  INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储上传的文件的信息，对应于对象Upload';
