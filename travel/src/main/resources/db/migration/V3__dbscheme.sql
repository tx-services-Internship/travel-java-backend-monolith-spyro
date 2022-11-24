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
    `breakfasts_provided` INT(3) DEFAULT 0,
    `lunches_provided` INT(3) DEFAULT 0,
    `dinners_provided` INT(3) DEFAULT 0,
    `submission_date` DATE NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `expenses` (
    `id` VARCHAR(36) NOT NULL,
    `claim` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255),
    `amount` INT DEFAULT 1,
    `currency` VARCHAR(20),
    `tef_id` VARCHAR(36) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_expense_tef_id_constraint` FOREIGN KEY (`tef_id`) REFERENCES `travel_expense_forms` (`id`)
    );

CREATE TABLE IF NOT EXISTS `travel_authorization_forms` (
    `id` VARCHAR(36) NOT NULL,

    `business_trip_end_date` TIMESTAMP NOT NULL,
    `arrival_date` TIMESTAMP NOT NULL,
    `accommodation` VARCHAR(255) NOT NULL,
    `num_breakfast` INT(3) NOT NULL,
    `num_lunch` INT(3) NOT NULL,
    `num_dinner` INT(3) NOT NULL,
    `type_of_transportation` VARCHAR(50) NOT NULL,
    `vehicle_registration_number` VARCHAR(20),
    `purpose` TEXT,
    `daily_allowance_fee` INT NOT NULL,
    `total_allowance` FLOAT NOT NULL,
    `total_allowance_rsd` FLOAT NOT NULL,
    `allowance_first_day` FLOAT NOT NULL,
    `allowance_last_day` FLOAT NOT NULL,
    `breakfast_fee` FLOAT NOT NULL,
    `lunch_fee` FLOAT NOT NULL,
    `dinner_fee` FLOAT NOT NULL,
    `submission_date` DATE NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `trips` (
    `id` VARCHAR(36) NOT NULL,
    `taf_id` VARCHAR(36) NOT NULL,
    `tef_id` VARCHAR(36) NOT NULL,

    `departure_date` TIMESTAMP NOT NULL,
    `status` VARCHAR(30) NOT NULL,
    `country` VARCHAR(30) NOT NULL,
    `city` VARCHAR(30) NOT NULL,
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

