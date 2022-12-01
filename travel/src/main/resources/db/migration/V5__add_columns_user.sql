ALTER TABLE `users`
    ADD COLUMN IF NOT EXISTS `name` varchar(20) DEFAULT NULL,
    ADD COLUMN IF NOT EXISTS `surname` varchar(20) DEFAULT NULL,
    ADD COLUMN IF NOT EXISTS `passport_no` varchar(20) DEFAULT NULL,
    ADD COLUMN IF NOT EXISTS `id_no` varchar(20) DEFAULT NULL,
    ADD COLUMN IF NOT EXISTS `cost_center_id` bigint(20) NOT NULL,
    ADD COLUMN IF NOT EXISTS `role_id` int(11) NOT NULL,
    ADD CONSTRAINT `FK_roles_role_id_constraint` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
