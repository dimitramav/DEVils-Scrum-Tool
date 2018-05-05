SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `ScrumTool_db` DEFAULT CHARACTER SET utf8 ;
USE `ScrumTool_db` ;

-- -----------------------------------------------------
-- Table `ScrumTool_db`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ScrumTool_db`.`User` ;

CREATE TABLE IF NOT EXISTS `ScrumTool_db`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `mail` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `password` VARCHAR(300) NOT NULL,
  `country` VARCHAR(45) NULL,
  `job` VARCHAR(45) NULL,
  `company` VARCHAR(45) NULL,
  `description` VARCHAR(500) NULL,
  `bio` VARCHAR(1000) NULL,
  `photo` VARCHAR(200) NULL,
  `isAdmin` TINYINT(1) NOT NULL,
  `numProjects` INT NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE INDEX `mail_UNIQUE` (`mail` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ScrumTool_db`.`Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ScrumTool_db`.`Project` ;

CREATE TABLE IF NOT EXISTS `ScrumTool_db`.`Project` (
  `idProject` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `isDone` TINYINT(1) NOT NULL,
  `deadlineDate` DATE NULL,
  PRIMARY KEY (`idProject`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ScrumTool_db`.`Sprint`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ScrumTool_db`.`Sprint` ;

CREATE TABLE IF NOT EXISTS `ScrumTool_db`.`Sprint` (
  `idSprint` INT NOT NULL AUTO_INCREMENT,
  `deadlineDate` DATE NOT NULL,
  `goal` VARCHAR(200) NULL,
  `plan` VARCHAR(500) NULL,
  `isCurrent` TINYINT(1) NOT NULL,
  `numSprint` INT NULL,
  `Project_id` INT NOT NULL,
  PRIMARY KEY (`idSprint`, `Project_id`),
  INDEX `fk_Sprint_Project1_idx` (`Project_id` ASC),
  CONSTRAINT `fk_Sprint_Project1`
    FOREIGN KEY (`Project_id`)
    REFERENCES `ScrumTool_db`.`Project` (`idProject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ScrumTool_db`.`PBI`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ScrumTool_db`.`PBI` ;

CREATE TABLE IF NOT EXISTS `ScrumTool_db`.`PBI` (
  `idPBI` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(500) NULL,
  `order` INT NOT NULL,
  `isEpic` TINYINT(1) NOT NULL,
  `Project_id` INT NOT NULL,
  `Epic_id` INT NULL,
  `Sprint_id` INT NULL,
  PRIMARY KEY (`idPBI`, `Project_id`),
  INDEX `fk_PBI_Project1_idx` (`Project_id` ASC),
  INDEX `fk_PBI_PBI1_idx` (`Epic_id` ASC),
  INDEX `fk_PBI_Sprint1_idx` (`Sprint_id` ASC),
  CONSTRAINT `fk_PBI_Project1`
    FOREIGN KEY (`Project_id`)
    REFERENCES `ScrumTool_db`.`Project` (`idProject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PBI_PBI1`
    FOREIGN KEY (`Epic_id`)
    REFERENCES `ScrumTool_db`.`PBI` (`idPBI`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PBI_Sprint1`
    FOREIGN KEY (`Sprint_id`)
    REFERENCES `ScrumTool_db`.`Sprint` (`idSprint`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ScrumTool_db`.`Project_has_User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ScrumTool_db`.`Project_has_User` ;

CREATE TABLE IF NOT EXISTS `ScrumTool_db`.`Project_has_User` (
  `Project_id` INT NOT NULL,
  `User_id` INT NOT NULL,
  `role` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Project_id`, `User_id`),
  INDEX `fk_Project_has_User_User1_idx` (`User_id` ASC),
  INDEX `fk_Project_has_User_Project_idx` (`Project_id` ASC),
  CONSTRAINT `fk_Project_has_User_Project`
    FOREIGN KEY (`Project_id`)
    REFERENCES `ScrumTool_db`.`Project` (`idProject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `ScrumTool_db`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ScrumTool_db`.`Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ScrumTool_db`.`Task` ;

CREATE TABLE IF NOT EXISTS `ScrumTool_db`.`Task` (
  `idTask` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(500) NOT NULL,
  `state` INT NOT NULL COMMENT '0 is todo\n1 is doing\n2 is done.',
  `PBI_id` INT NOT NULL,
  PRIMARY KEY (`idTask`, `PBI_id`),
  INDEX `fk_Task_PBI1_idx` (`PBI_id` ASC),
  CONSTRAINT `fk_Task_PBI1`
    FOREIGN KEY (`PBI_id`)
    REFERENCES `ScrumTool_db`.`PBI` (`idPBI`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ScrumTool_db`.`Issue`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ScrumTool_db`.`Issue` ;

CREATE TABLE IF NOT EXISTS `ScrumTool_db`.`Issue` (
  `idIssue` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(500) NULL,
  `Task_id` INT NOT NULL,
  PRIMARY KEY (`idIssue`, `Task_id`),
  INDEX `fk_Issue_Task1_idx` (`Task_id` ASC),
  CONSTRAINT `fk_Issue_Task1`
    FOREIGN KEY (`Task_id`)
    REFERENCES `ScrumTool_db`.`Task` (`idTask`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ScrumTool_db`.`Project_User_has_Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ScrumTool_db`.`Project_User_has_Task` ;

CREATE TABLE IF NOT EXISTS `ScrumTool_db`.`Project_User_has_Task` (
  `Project_id` INT NOT NULL,
  `User_id` INT NOT NULL,
  `Task_id` INT NOT NULL,
  `Task_PBI_id` INT NOT NULL,
  PRIMARY KEY (`Project_id`, `User_id`, `Task_id`, `Task_PBI_id`),
  INDEX `fk_Project_has_User_has_Task_Task1_idx` (`Task_id` ASC, `Task_PBI_id` ASC),
  INDEX `fk_Project_has_User_has_Task_Project_has_User1_idx` (`Project_id` ASC, `User_id` ASC),
  CONSTRAINT `fk_Project_has_User_has_Task_Project_has_User1`
    FOREIGN KEY (`Project_id` , `User_id`)
    REFERENCES `ScrumTool_db`.`Project_has_User` (`Project_id` , `User_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_User_has_Task_Task1`
    FOREIGN KEY (`Task_id` , `Task_PBI_id`)
    REFERENCES `ScrumTool_db`.`Task` (`idTask` , `PBI_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
