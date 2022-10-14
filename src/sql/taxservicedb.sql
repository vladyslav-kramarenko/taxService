-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema taxservicedb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema taxservicedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `taxservicedb` DEFAULT CHARACTER SET utf8 ;
USE `taxservicedb` ;

DROP TABLE IF EXISTS `taxservicedb`.`role` ;
DROP TABLE IF EXISTS `taxservicedb`.`user` ;
DROP TABLE IF EXISTS `taxservicedb`.`user_details` ;
DROP TABLE IF EXISTS `taxservicedb`.`status` ;
DROP TABLE IF EXISTS `taxservicedb`.`type` ;
DROP TABLE IF EXISTS `taxservicedb`.`report` ;
DROP TABLE IF EXISTS `taxservicedb`.`user_report` ;

-- -----------------------------------------------------
-- Table `taxservicedb`.`role`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `taxservicedb`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);


-- -----------------------------------------------------
-- Table `taxservicedb`.`user`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `taxservicedb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `first_name` VARCHAR(32) NULL,
  `last_name` VARCHAR(32) NULL,
  `patronymic` VARCHAR(32) NULL,
  `code_passport` VARCHAR(15) NULL,
  `phone` VARCHAR(15) NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_role1_idx` (`role_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_user_role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `taxservicedb`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `taxservicedb`.`user_details`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `taxservicedb`.`user_details` (
  `user_id` INT NOT NULL,
  `last_accesed_ip` VARCHAR(255) NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_details_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_details_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `taxservicedb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `taxservicedb`.`status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `taxservicedb`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxservicedb`.`type`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `taxservicedb`.`type` (
  `id` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_520_ci' NOT NULL,
  `name` VARCHAR(100) NULL,
  `xml` VARCHAR(100) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxservicedb`.`report`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `taxservicedb`.`report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `xml` VARCHAR(100) NULL,
  `Comment` VARCHAR(300) NULL,
  `status_id` INT NOT NULL,
  `type_id` VARCHAR(45) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_520_ci' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_report_status1_idx` (`status_id` ASC) VISIBLE,
  INDEX `fk_report_type1_idx` (`type_id` ASC) VISIBLE,
  CONSTRAINT `fk_report_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `taxservicedb`.`status` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_report_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `taxservicedb`.`type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `taxservicedb`.`user_report`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `taxservicedb`.`user_report` (
  `report_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`report_id`, `user_id`),
  INDEX `fk_userReport_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_userReport_report1`
    FOREIGN KEY (`report_id`)
    REFERENCES `taxservicedb`.`report` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_userReport_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `taxservicedb`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `taxservicedb`.`role`
-- -----------------------------------------------------
START TRANSACTION;
USE `taxservicedb`;
INSERT INTO `taxservicedb`.`role` (`id`, `name`) VALUES (1, 'inspector');
INSERT INTO `taxservicedb`.`role` (`id`, `name`) VALUES (2, 'user');
INSERT INTO `taxservicedb`.`role` (`id`, `name`) VALUES (3, 'admin');
INSERT INTO `taxservicedb`.`role` (`id`, `name`) VALUES (4, 'banned');

COMMIT;


-- -----------------------------------------------------
-- Data for table `taxservicedb`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `taxservicedb`;
INSERT INTO `taxservicedb`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `patronymic`, `code_passport`, `phone`, `create_time`, `role_id`) VALUES (DEFAULT, 'admin@taxservice.com', '4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2', 'Super', 'Admin', NULL, NULL, NULL, NULL, 3);
INSERT INTO `taxservicedb`.`user` (`id`, `email`, `password`, `first_name`, `last_name`, `patronymic`, `code_passport`, `phone`, `create_time`, `role_id`) VALUES (DEFAULT, 'inspector@taxservice.com', '98fe442255035a1459bb5b86fda03d7c34c23d512b1b5bf3a5ecb7a802601895', 'Super', 'Inspector', NULL, NULL, NULL, NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `taxservicedb`.`status`
-- -----------------------------------------------------
START TRANSACTION;
USE `taxservicedb`;
INSERT INTO `taxservicedb`.`status` (`id`, `name`) VALUES (DEFAULT, 'draft');
INSERT INTO `taxservicedb`.`status` (`id`, `name`) VALUES (DEFAULT, 'sent');
INSERT INTO `taxservicedb`.`status` (`id`, `name`) VALUES (DEFAULT, 'accepted');
INSERT INTO `taxservicedb`.`status` (`id`, `name`) VALUES (DEFAULT, 'refused');

COMMIT;


-- -----------------------------------------------------
-- Data for table `taxservicedb`.`type`
-- -----------------------------------------------------
START TRANSACTION;
USE `taxservicedb`;
INSERT INTO `taxservicedb`.`type` (`id`, `name`, `xml`) VALUES ('F0103405', 'ПОДАТКОВА ДЕКЛАРАЦІЯ ПЛАТНИКА ЄДИНОГО ПОДАТКУ ФІЗИЧНОЇ ОСОБИ - ПІДПРИЄМЦЯ', NULL);

COMMIT;

