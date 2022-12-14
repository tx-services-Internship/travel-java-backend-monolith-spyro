CREATE TABLE IF NOT EXISTS `exchange_rates` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(3) DEFAULT NULL,
    `amount_in_rsd` DECIMAL(13,6) UNSIGNED DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_code` (`code`)
    );

CREATE TABLE IF NOT EXISTS `travel_expense_forms` (
    `id` VARCHAR(36) NOT NULL,
    `breakfasts_provided` INT UNSIGNED DEFAULT 0,
    `lunches_provided` INT UNSIGNED DEFAULT 0,
    `dinners_provided` INT UNSIGNED DEFAULT 0,
    `submission_date` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `expenses` (
    `id` VARCHAR(36) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `cost` DECIMAL(20,6) UNSIGNED NOT NULL,
    `currency_code` VARCHAR(3),
    `tef_id` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_expense_tef_id_constraint` FOREIGN KEY (`tef_id`) REFERENCES `travel_expense_forms` (`id`)
    );

CREATE TABLE IF NOT EXISTS `travel_authorization_forms` (
    `id` VARCHAR(36) NOT NULL,
    `trip_end_date` TIMESTAMP NOT NULL,
    `arrival_date` TIMESTAMP NOT NULL,
    `accommodation` VARCHAR(255) NOT NULL,
    `breakfast_number` INT UNSIGNED NOT NULL,
    `lunch_number` INT UNSIGNED NOT NULL,
    `dinner_number` INT UNSIGNED NOT NULL,
    `type_of_transportation` VARCHAR(50) NOT NULL,
    `vehicle_registration_number` VARCHAR(20),
    `purpose_of_travel` TEXT,
    `daily_allowance_fee` DECIMAL(13,6) UNSIGNED NOT NULL,
    `total_allowance` DECIMAL(13,6) UNSIGNED NOT NULL,
    `total_allowance_rsd` DECIMAL(13,6) UNSIGNED NOT NULL,
    `first_day_granted_allowance_percentage` DECIMAL(5,2) UNSIGNED NOT NULL,
    `last_day_granted_allowance_percentage` DECIMAL(5,2) UNSIGNED NOT NULL,
    `breakfast_fee_percentage` DECIMAL(5,2) UNSIGNED NOT NULL,
    `lunch_fee_percentage` DECIMAL(5,2) UNSIGNED NOT NULL,
    `dinner_fee_percentage` DECIMAL(5,2) UNSIGNED NOT NULL,
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
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `region` VARCHAR(100) NOT NULL,
    `amount` DECIMAL(13,6) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_region` (`region`)
);

CREATE TABLE IF NOT EXISTS `cost_centers` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(7) NOT NULL,
    `name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_code` (`code`)
);

