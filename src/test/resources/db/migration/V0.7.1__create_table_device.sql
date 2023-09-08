/*******************************************************************************
 *
 *    Copyright (c) 2022 - 2023.
 *    Haixing Hu, Qubit Co. Ltd.
 *
 *    All rights reserved.
 *
 ******************************************************************************/

/**
 * 表`device`用于存储设备的信息，对应于对象`Device`。
 *
 * @author 胡海星
 */
CREATE TABLE `device` (
    `id`                                  BIGINT        NOT NULL                              COMMENT 'ID，由程序生成',
    `code`                                VARCHAR(64)   NOT NULL                              COMMENT '代码，全局不重复',
    `name`                                VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '名称',
    `app_id`                              BIGINT        NOT NULL                              COMMENT '所属App的ID',
    `brand`                               VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '硬件品牌',
    `model`                               VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '硬件型号',
    `version`                             VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '硬件版本号',
    `manufacturer`                        VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '硬件制造商名称',
    `description`                         TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '描述',
    `os_type`                             VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '设备运行的操作系统类型',
    `os_name`                             VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '设备运行的操作系统的名称设备运行的操作系统的名称',
    `os_version`                          VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '设备运行的操作系统的版本号',
    `client_app_name`                     VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '客户端应用名称',
    `client_app_version`                  VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '客户端应用版本',
    `client_app_manufacturer`             VARCHAR(128)  CHARACTER SET UTF8MB4 NOT NULL        COMMENT '客户端应用制造商名称',
    `sim_card_iccid`                      VARCHAR(64)   DEFAULT NULL                          COMMENT 'SIM卡的ICCID号码',
    `sim_card_imei`                       VARCHAR(64)   DEFAULT NULL                          COMMENT 'SIM卡的IMEI号码',
    `sim_card_meid`                       VARCHAR(64)   DEFAULT NULL                          COMMENT 'SIM卡的MEID号码',
    `sim_card_phone`                      VARCHAR(128)  DEFAULT NULL                          COMMENT 'SIM卡的手机号码',
    `sim_card_operator`                   VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT 'SIM卡的运营商的名称',
    `sim_card_country_id`                 BIGINT        DEFAULT NULL                          COMMENT 'SIM卡的运营商所属国家的ID',
    `sim_card_location_latitude`          DECIMAL(9, 6) DEFAULT NULL                          COMMENT '设备所处地理位置维度',
    `sim_card_location_longitude`         DECIMAL(9, 6) DEFAULT NULL                          COMMENT '设备所处地理位置经度',
    `sim_card_network_type`               VARCHAR(64)   DEFAULT NULL                          COMMENT 'SIM卡的数据网络类型，枚举值',
    `sim_card_status`                     VARCHAR(64)   DEFAULT NULL                          COMMENT 'SIM卡的当前状态，枚举值',
    `location_latitude`                   DECIMAL(9, 6) DEFAULT NULL                          COMMENT '设备所处地理位置维度',
    `location_longitude`                  DECIMAL(9, 6) DEFAULT NULL                          COMMENT '设备所处地理位置经度',
    `udid`                                VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '硬件设备唯一ID',
    `mac_address`                         VARCHAR(128)  CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '网卡MAC地址网卡MAC地址',
    `ip_address`                          VARCHAR(128)  DEFAULT NULL                          COMMENT '公网IP地址',
    `owner_id`                            BIGINT        DEFAULT NULL                          COMMENT '所有者ID，对应于表user的id字段',
    `deploy_address_street_id`            BIGINT        DEFAULT NULL                          COMMENT '部署地址所属街道ID，关联表street的id字段',
    `deploy_address_detail`               VARCHAR(4096) CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '部署地址详细门牌号码',
    `deploy_address_postalcode`           VARCHAR(64)   DEFAULT NULL                          COMMENT '部署地址邮政编码',
    `deploy_address_location_latitude`    DECIMAL(9, 6) DEFAULT NULL                          COMMENT '部署地理位置维度',
    `deploy_address_location_longitude`   DECIMAL(9, 6) DEFAULT NULL                          COMMENT '部署地理位置经度',
    `deploy_address_verified`             VARCHAR(64)   DEFAULT NULL                          COMMENT '地址验证状态，枚举值',
    `state`                               VARCHAR(64)   NOT NULL                              COMMENT '状态，枚举值',
    `comment`                             TEXT          CHARACTER SET UTF8MB4 DEFAULT NULL    COMMENT '备注',
    `register_time`                       DATETIME      DEFAULT NULL                          COMMENT '注册激活时间，UTC时区',
    `last_startup_time`                   DATETIME      DEFAULT NULL                          COMMENT '上次启动时间，UTC时区',
    `last_heartbeat_time`                 DATETIME      DEFAULT NULL                          COMMENT '上次心跳连接时间，UTC时区',
    `create_time`                         DATETIME      NOT NULL                              COMMENT '创建时间，UTC时区',
    `modify_time`                         DATETIME      DEFAULT NULL                          COMMENT '最后一次修改时间，UTC时区',
    `delete_time`                         DATETIME      DEFAULT NULL                          COMMENT '标记删除时间，UTC时区',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `code` (`code`),
    INDEX `name` (`name`),
    FOREIGN KEY `app` (`app_id`) REFERENCES `app` (`id`) ON DELETE CASCADE,
    INDEX `brand` (`brand`),
    INDEX `model` (`model`),
    INDEX `version` (`version`),
    INDEX `manufacturer` (`manufacturer`),
    INDEX `os_type` (`os_type`),
    INDEX `os_name` (`os_name`),
    INDEX `os_version` (`os_version`),
    UNIQUE INDEX `udid` (`udid`),
    INDEX `owner` (`owner_id`),
    INDEX `client_app_name` (`client_app_name`),
    INDEX `client_app_version` (`client_app_version`),
    INDEX `client_app_manufacturer` (`client_app_manufacturer`),
    FOREIGN KEY `deploy_address_street` (`deploy_address_street_id`) REFERENCES `street` (`id`) ON DELETE SET NULL,
    INDEX `state` (`state`),
    INDEX `register_time` (`register_time` DESC),
    INDEX `last_startup_time` (`last_startup_time` DESC),
    INDEX `last_heartbeat_time` (`last_heartbeat_time` DESC),
    INDEX `create_time` (`create_time` DESC),
    INDEX `modify_time` (`modify_time` DESC),
    INDEX `delete_time` (`delete_time` DESC)
) CHARACTER SET ASCII ENGINE=InnoDB COMMENT '存储设备的信息，对应于对象Device';
