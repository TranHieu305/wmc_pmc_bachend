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
-- Table structure for table `partner`
--
DROP TABLE IF EXISTS `partner`;
CREATE TABLE `partner` (
    `id` int AUTO_INCREMENT,
    `partner_type` varchar(31),
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
-- Table structure for table `supplier`
--
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
    `id` int AUTO_INCREMENT,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_SUP_ID` FOREIGN KEY (`id`) REFERENCES `partner`(`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `order`
--
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    `id` int AUTO_INCREMENT,
    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

    `partner_name` VARCHAR(255),
    `partner_email`  VARCHAR(255),
    `partner_phone` VARCHAR(255),
    `partner_address` VARCHAR(255),
    `total_order_cost` decimal(16,6),
    `tax` decimal(16,6),

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
     FOREIGN KEY (id) REFERENCES `order`(id),
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
     CONSTRAINT `FK_ORDER` FOREIGN KEY (`order_id`) REFERENCES `order`(`id`)
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
    `product_type` varchar(63),

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

--
-- Table structure for table `product_warehouse`
--
DROP TABLE IF EXISTS `product_warehouse`;
CREATE TABLE `product_warehouse` (
    `id` int AUTO_INCREMENT,

    `product_id` int NOT NULL,
    `warehouse_id` int NOT NULL,
    `quantity_on_hand` decimal(16,6),

    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

    PRIMARY KEY (`id`),
    KEY `FK_PW_PRODUCT_idx` (`product_id`),
    KEY `FK_PW_WAREHOUSE_idx` (`warehouse_id`),

    CONSTRAINT `FK_PW_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT `FK_PW_WAREHOUSE` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse`(`id`)

    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `product_warehouse`
--
DROP TABLE IF EXISTS `inventory_item`;
CREATE TABLE `inventory_item` (
    `id` int AUTO_INCREMENT,

    `lot_id` int NOT NULL,
    `assigned_order_item_id` int NOT NULL,
    `product_id` int NOT NULL,
    `warehouse_id` int NOT NULL,
    `quantity_on_hand` decimal(16,6),
    `unit_price` decimal(16,6),
    `datetime_received` TIMESTAMP NULL,
    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

    PRIMARY KEY (`id`),
    KEY `FK_II_PRODUCT_idx` (`product_id`),
    KEY `FK_II_WAREHOUSE_idx` (`warehouse_id`),


    CONSTRAINT `FK_II_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT `FK_II_WAREHOUSE` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse`(`id`),
    CONSTRAINT `FK_II_AOI` FOREIGN KEY (`assigned_order_item_id`) REFERENCES `assigned_order_item`(`id`),
    CONSTRAINT `FK_II_LOT` FOREIGN KEY (`lot_id`) REFERENCES `lot`(`id`)

    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `product_price`
--
DROP TABLE IF EXISTS `product_price`;
CREATE TABLE `product_price` (
    `id` int AUTO_INCREMENT,

    `product_id` int NOT NULL,
    `partner_id` int NOT NULL,
    `price` decimal(16,6),
    `date_apply` DATE NULL,
    `description` varchar(255) DEFAULT NULL,

    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

    PRIMARY KEY (`id`),
    KEY `FK_PP_PRODUCT_idx` (`product_id`),
    KEY `FK_PP_PARTNER_idx` (`partner_id`),

    CONSTRAINT `FK_PP_PRODUCT` FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT `FK_PP_PARTNER` FOREIGN KEY (`partner_id`) REFERENCES `partner`(`id`)


    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `lot`
--
DROP TABLE IF EXISTS `lot`;
CREATE TABLE `lot` (
    `id` int AUTO_INCREMENT,

    `name` varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `order_id` int,
    `warehouse_id` int,
    `type` varchar(45) NOT NULL,
    `date` DATE NULL,
    `status` varchar(45) DEFAULT NULL,

    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

    PRIMARY KEY (`id`),
    KEY `FK_LOT_idx` (`order_id`),

    CONSTRAINT `FK_LOT_ORDER` FOREIGN KEY (`order_id`) REFERENCES `order`(`id`),
    CONSTRAINT `FK_LOT_WAREHOUSE` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse`(`id`)

    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `assigned_order_item`
--
DROP TABLE IF EXISTS `assigned_order_item`;
CREATE TABLE `assigned_order_item` (
    `id` int AUTO_INCREMENT,
    `order_item_id` int,
    `product_id` int,
    `lot_id` int,
    `assigned_to_id` int,
    `assigned_quantity` decimal(16,6),
    `status` varchar(45) DEFAULT NULL,
    `delivered_date` DATE NULL,

    `created_at` TIMESTAMP NULL,
    `modified_at` TIMESTAMP NULL,

    PRIMARY KEY (`id`),
    KEY `FK_AOI_LOT_idx` (`lot_id`),
    KEY `FK_AOI_OI_idx` (`order_item_id`),


    CONSTRAINT `FK_AOI_LOT` FOREIGN KEY (`lot_id`) REFERENCES `lot`(`id`),
    CONSTRAINT `FK_AOI_OO` FOREIGN KEY (`order_item_id`) REFERENCES `order_item`(`id`),
    CONSTRAINT `FK_AOI_P` FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)

    ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS=1;
