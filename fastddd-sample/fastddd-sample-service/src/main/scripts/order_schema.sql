CREATE DATABASE `order` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

CREATE TABLE `order`.`tb_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_type` TINYINT DEFAULT 1 NOT NULL,
  `order_status` TINYINT DEFAULT 1 NOT NULL,
  `total_amount` decimal(20,6) DEFAULT NULL,
  `create_time` datetime not null  DEFAULT current_timestamp,
  `update_time` datetime not null DEFAULT current_timestamp on update current_timestamp,
  PRIMARY KEY (`order_id`),
  KEY `IX_CREATE_TIME` (`CREATE_TIME`),
  KEY `IX_UPDATE_TIME` (`UPDATE_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `order`.`tb_order_line` (
  `order_line_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `item_id` bigint(20) DEFAULT 0 NOT NULL,
  `quantity` bigint(20) DEFAULT 1 NOT NULL,
  `unit_price` decimal(20,6) DEFAULT NULL,
  `create_time` datetime not null  DEFAULT current_timestamp,
  `update_time` datetime not null DEFAULT current_timestamp on update current_timestamp,
  PRIMARY KEY (`order_line_id`),
  KEY `IX_CREATE_TIME` (`CREATE_TIME`),
  KEY `IX_UPDATE_TIME` (`UPDATE_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
