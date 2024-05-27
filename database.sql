-- Create a new database if it does not exist
CREATE DATABASE IF NOT EXISTS wms;

-- Switch to the database context
USE wms;
SET FOREIGN_KEY_CHECKS=0;
--
-- Table structure for table `customer`
--
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
    `id` int AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `address` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `latitude` decimal(16,6) DEFAULT NULL,
    `longitude` decimal(16,6) DEFAULT NULL,
    `phone` varchar(255) DEFAULT NULL,
    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `warehouse`
--
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
    `id` int AUTO_INCREMENT,
    `address` varchar(255) DEFAULT NULL,

    `description` varchar(255) DEFAULT NULL,
    `latitude` decimal(16,6) DEFAULT NULL,
    `longitude` decimal(16,6) DEFAULT NULL,

    `name` varchar(255) NOT NULL,
    `status` varchar(255) DEFAULT NULL,
    `supervisor` varchar(255) DEFAULT NULL,
    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `supplier`
--
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
    `id` int AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `phone` varchar(255) DEFAULT NULL,
    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `abstract_order`
--
DROP TABLE IF EXISTS `abstract_order`;
CREATE TABLE `abstract_order` (
    `id` int AUTO_INCREMENT,
    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,
    `order_type` VARCHAR(31),
     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `material_order`
--
DROP TABLE IF EXISTS `material_order`;
CREATE TABLE `material_order` (
    `id` int AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `supplier_id` int DEFAULT NULL,
    `order_date` date NULL,
    `expected_date` date DEFAULT NULL,
    `actual_date` date DEFAULT NULL,
    `status` varchar(45) DEFAULT NULL,
    `additional_data` text DEFAULT NULL,
    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,
     PRIMARY KEY (`id`),
     FOREIGN KEY (id) REFERENCES `abstract_order`(id),
     KEY `FK_SUPPLIER_idx` (`supplier_id`),
     CONSTRAINT `FK_MATERIAL_ORDER` FOREIGN KEY (`supplier_id`) REFERENCES `supplier`(`id`)
     ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `order_item`
--
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
    `id` int AUTO_INCREMENT,
    `order_type` varchar(255) NOT NULL,
    `order_id` int NOT NULL,
    `product_id` int NOT NULL,
    `product_name` varchar(255) NOT NULL,
    `product_uom` varchar(63) NOT NULL,
    `product_price` decimal(16,6) DEFAULT NULL,
    `quantity` decimal(16,6) DEFAULT NULL,

    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,
     PRIMARY KEY (`id`),

     KEY `FK_ORDER_idx` (`order_id`),
     CONSTRAINT `FK_ORDER` FOREIGN KEY (`order_id`) REFERENCES `abstract_order`(`id`)
     ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `product_category`
--
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
    `id` int AUTO_INCREMENT,

    `name` varchar(255) UNIQUE NOT NULL,
    `description` varchar(255) DEFAULT NULL,

    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `product`
--
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    `id` int AUTO_INCREMENT,

    `name` varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `code` varchar(63) NOT NULL,
    `uom` varchar(63) DEFAULT NULL,
    `category_id` int NOT NULL,
    `custom_fields` text,
    `images` blob,

    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

    PRIMARY KEY (`id`),
    KEY `FK_PRODUCT_idx` (`category_id`),
    CONSTRAINT `unique_name_uom_pair` UNIQUE (`name`, `uom`),
    CONSTRAINT `FK_PRODUCT` FOREIGN KEY (`category_id`) REFERENCES `product_category`(`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS=1;
