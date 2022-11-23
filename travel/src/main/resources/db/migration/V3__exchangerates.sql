CREATE TABLE IF NOT EXISTS `exchange_rates` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(20) DEFAULT NULL,
    `amountInRsd` FLOAT UNSIGNED DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_currency` (`code`)
);