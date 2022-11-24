CREATE TABLE IF NOT EXISTS `exchange_rates` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(3) DEFAULT NULL,
    `amount_in_rsd` DECIMAL(7,6) UNSIGNED DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_code` (`code`)
    );

CREATE TABLE IF NOT EXISTS `travel_expense_forms` (
    `id` VARCHAR(36) NOT NULL,

    `business_trip_end_date` TIMESTAMP NOT NULL,
    `breakfasts_provided` TINYINT UNSIGNED DEFAULT 0,
    `lunches_provided` TINYINT UNSIGNED DEFAULT 0,
    `dinners_provided` TINYINT UNSIGNED DEFAULT 0,
    `submission_date` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `expenses` (
    `id` VARCHAR(36) NOT NULL,
    `claim` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `amount` TINYINT UNSIGNED DEFAULT 1,
    `currency_code` VARCHAR(3),
    `tef_id` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_expense_tef_id_constraint` FOREIGN KEY (`tef_id`) REFERENCES `travel_expense_forms` (`id`)
    );

CREATE TABLE IF NOT EXISTS `travel_authorization_forms` (
    `id` VARCHAR(36) NOT NULL,

    `business_trip_end_date` TIMESTAMP NOT NULL,
    `arrival_date` TIMESTAMP NOT NULL,
    `accommodation` VARCHAR(255) NOT NULL,
    `breakfasts` TINYINT UNSIGNED NOT NULL,
    `lunches` TINYINT UNSIGNED NOT NULL,
    `dinners` TINYINT UNSIGNED NOT NULL,
    `type_of_transportation` VARCHAR(50) NOT NULL,
    `vehicle_registration_number` VARCHAR(20),
    `purpose` TEXT,
    `daily_allowance_fee` DECIMAL(7,6) UNSIGNED NOT NULL,
    `total_allowance` DECIMAL(7,6) UNSIGNED NOT NULL,
    `total_allowance_rsd` DECIMAL(7,6) UNSIGNED NOT NULL,
    `allowance_first_day` FLOAT UNSIGNED NOT NULL,
    `allowance_last_day` FLOAT UNSIGNED NOT NULL,
    `breakfast_fee` DECIMAL(3,3) UNSIGNED NOT NULL,
    `lunch_fee` DECIMAL(3,3) UNSIGNED NOT NULL,
    `dinner_fee` DECIMAL(3,3) UNSIGNED NOT NULL,
    `submission_date` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `trips` (
    `id` VARCHAR(36) NOT NULL,
    `taf_id` VARCHAR(36) NOT NULL,
    `tef_id` VARCHAR(36),

    `departure_date` TIMESTAMP NOT NULL,
    `status` VARCHAR(30) NOT NULL,
    `country` VARCHAR(50) NOT NULL,
    `city` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_trip_taf_id_constraint` FOREIGN KEY (`taf_id`) REFERENCES `travel_authorization_forms` (`id`),
    CONSTRAINT `FK_trip_tef_id_constraint` FOREIGN KEY (`tef_id`) REFERENCES `travel_expense_forms` (`id`)
    );

CREATE TABLE IF NOT EXISTS `daily_allowances` (
    `id` BIGINT(5) NOT NULL,
    `region` VARCHAR(100) NOT NULL,
    `amount` DECIMAL(7,6) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_region` (`region`)
);

CREATE TABLE IF NOT EXISTS `cost_centers` (
    `id` BIGINT(20) NOT NULL,
    `code` VARCHAR(7) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_code` (`code`)
);

