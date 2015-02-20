SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `smartpay` ;
CREATE SCHEMA IF NOT EXISTS `smartpay` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `smartpay` ;

-- -----------------------------------------------------
-- Table `smartpay`.`MERCHANT_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`MERCHANT_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`MERCHANT_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`CREDENTIAL_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`CREDENTIAL_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`CREDENTIAL_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`CREDENTIAL_TYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`CREDENTIAL_TYPE` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`CREDENTIAL_TYPE` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`CREDENTIAL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`CREDENTIAL` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`CREDENTIAL` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `CONTENT` VARCHAR(32) NOT NULL,
  `EXPIRATION_TIME` DATETIME NOT NULL,
  `REMARK` TEXT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `CREDENTIAL_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `CREDENTIAL_TYPE_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `CREDENTIAL_STATUS_ID`, `CREDENTIAL_TYPE_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Credential_CredentialStatus1_idx` (`CREDENTIAL_STATUS_ID` ASC),
  INDEX `fk_Credential_CredentialType1_idx` (`CREDENTIAL_TYPE_ID` ASC),
  CONSTRAINT `fk_Credential_CredentialStatus`
    FOREIGN KEY (`CREDENTIAL_STATUS_ID`)
    REFERENCES `smartpay`.`CREDENTIAL_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Credential_CredentialType`
    FOREIGN KEY (`CREDENTIAL_TYPE_ID`)
    REFERENCES `smartpay`.`CREDENTIAL_TYPE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`FEE_TYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`FEE_TYPE` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`FEE_TYPE` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`FEE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`FEE` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`FEE` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `VALUE` FLOAT NOT NULL,
  `REMARK` TEXT NULL,
  `FEE_TYPE_ID` BIGINT UNSIGNED NOT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  PRIMARY KEY (`ID`, `FEE_TYPE_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Fee_FeeType1_idx` (`FEE_TYPE_ID` ASC),
  CONSTRAINT `fk_Fee_FeeType`
    FOREIGN KEY (`FEE_TYPE_ID`)
    REFERENCES `smartpay`.`FEE_TYPE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ENCRYPTION_TYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ENCRYPTION_TYPE` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ENCRYPTION_TYPE` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ENCRYPTION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ENCRYPTION` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ENCRYPTION` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `KEY` VARCHAR(128) NOT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `ENCRYPTION_TYPE_ID` BIGINT UNSIGNED NOT NULL,
  `REMARK` TEXT NULL,
  PRIMARY KEY (`ID`, `ENCRYPTION_TYPE_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `key_UNIQUE` (`KEY` ASC),
  INDEX `fk_Encryption_EncryptionType1_idx` (`ENCRYPTION_TYPE_ID` ASC),
  CONSTRAINT `fk_Encryption_EncryptionType`
    FOREIGN KEY (`ENCRYPTION_TYPE_ID`)
    REFERENCES `smartpay`.`ENCRYPTION_TYPE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`MERCHANT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`MERCHANT` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`MERCHANT` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `ADDRESS` VARCHAR(128) NULL,
  `CONTACT` VARCHAR(32) NULL,
  `TEL` VARCHAR(32) NULL,
  `EMAIL` VARCHAR(32) NULL,
  `LOGO_IMAGE` BLOB NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `REMARK` TEXT NULL,
  `MERCHANT_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `CREDENTIAL_ID` BIGINT UNSIGNED NOT NULL,
  `COMMISSION_FEE_ID` BIGINT UNSIGNED NOT NULL,
  `ENCRYPTION_ID` BIGINT UNSIGNED NOT NULL,
  `RETURN_FEE_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `MERCHANT_STATUS_ID`, `CREDENTIAL_ID`, `COMMISSION_FEE_ID`, `ENCRYPTION_ID`, `RETURN_FEE_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Merchant_MerchantStatus_idx` (`MERCHANT_STATUS_ID` ASC),
  INDEX `fk_Merchant_Credential1_idx` (`CREDENTIAL_ID` ASC),
  INDEX `fk_Merchant_Fee1_idx` (`COMMISSION_FEE_ID` ASC),
  INDEX `fk_Merchant_Encryption1_idx` (`ENCRYPTION_ID` ASC),
  INDEX `fk_Merchant_Fee1_idx1` (`RETURN_FEE_ID` ASC),
  CONSTRAINT `fk_Merchant_MerchantStatus`
    FOREIGN KEY (`MERCHANT_STATUS_ID`)
    REFERENCES `smartpay`.`MERCHANT_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Merchant_Credential`
    FOREIGN KEY (`CREDENTIAL_ID`)
    REFERENCES `smartpay`.`CREDENTIAL` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Merchant_Commission_Fee`
    FOREIGN KEY (`COMMISSION_FEE_ID`)
    REFERENCES `smartpay`.`FEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Merchant_Encryption`
    FOREIGN KEY (`ENCRYPTION_ID`)
    REFERENCES `smartpay`.`ENCRYPTION` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Merchant_Return_Fee`
    FOREIGN KEY (`RETURN_FEE_ID`)
    REFERENCES `smartpay`.`FEE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ACCOUNT_TYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ACCOUNT_TYPE` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ACCOUNT_TYPE` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ACCOUNT_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ACCOUNT_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ACCOUNT_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ACCOUNT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ACCOUNT` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ACCOUNT` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `REMARK` TEXT NULL,
  `MERCHANT_ID` BIGINT UNSIGNED NOT NULL,
  `ACCOUNT_TYPE_ID` BIGINT UNSIGNED NOT NULL,
  `ACCOUNT_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `MERCHANT_ID`, `ACCOUNT_TYPE_ID`, `ACCOUNT_STATUS_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Account_Merchant1_idx` (`MERCHANT_ID` ASC),
  INDEX `fk_Account_AccountType1_idx` (`ACCOUNT_TYPE_ID` ASC),
  INDEX `fk_Account_AccountStatus1_idx` (`ACCOUNT_STATUS_ID` ASC),
  CONSTRAINT `fk_Account_Merchant1`
    FOREIGN KEY (`MERCHANT_ID`)
    REFERENCES `smartpay`.`MERCHANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Account_AccountType`
    FOREIGN KEY (`ACCOUNT_TYPE_ID`)
    REFERENCES `smartpay`.`ACCOUNT_TYPE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Account_AccountStatus`
    FOREIGN KEY (`ACCOUNT_STATUS_ID`)
    REFERENCES `smartpay`.`ACCOUNT_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`SITE_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`SITE_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`SITE_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`SITE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`SITE` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`SITE` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `URL` VARCHAR(128) NOT NULL,
  `LOGO_IMAGE` BLOB NULL,
  `REMARK` TEXT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `SITE_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `MERCHANT_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `SITE_STATUS_ID`, `MERCHANT_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `url_UNIQUE` (`URL` ASC),
  INDEX `fk_Site_SiteStatus1_idx` (`SITE_STATUS_ID` ASC),
  INDEX `fk_Site_Merchant1_idx` (`MERCHANT_ID` ASC),
  CONSTRAINT `fk_Site_SiteStatus`
    FOREIGN KEY (`SITE_STATUS_ID`)
    REFERENCES `smartpay`.`SITE_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Site_Merchant`
    FOREIGN KEY (`MERCHANT_ID`)
    REFERENCES `smartpay`.`MERCHANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`USER_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`USER_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`USER_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`USER` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`USER` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR(32) NOT NULL,
  `PASSWORD` VARCHAR(32) NOT NULL,
  `FIRST_NAME` VARCHAR(32) NOT NULL,
  `LAST_NAME` VARCHAR(32) NOT NULL,
  `EMAIL` VARCHAR(32) NOT NULL,
  `PROFILE_IMAGE` BLOB NULL,
  `REMARK` TEXT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `MERCHANT_ID` BIGINT UNSIGNED NOT NULL,
  `USER_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `MERCHANT_ID`, `USER_STATUS_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `username_UNIQUE` (`USERNAME` ASC),
  UNIQUE INDEX `email_UNIQUE` (`EMAIL` ASC),
  INDEX `fk_User_Merchant1_idx` (`MERCHANT_ID` ASC),
  INDEX `fk_User_UserStatus1_idx` (`USER_STATUS_ID` ASC),
  CONSTRAINT `fk_User_Merchant`
    FOREIGN KEY (`MERCHANT_ID`)
    REFERENCES `smartpay`.`MERCHANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_UserStatus`
    FOREIGN KEY (`USER_STATUS_ID`)
    REFERENCES `smartpay`.`USER_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ROLE` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ROLE` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(64) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`PERMISSION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`PERMISSION` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`PERMISSION` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(64) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ORDER_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ORDER_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ORDER_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`CURRENCY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`CURRENCY` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`CURRENCY` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`CUSTOMER_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`CUSTOMER_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`CUSTOMER_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`CUSTOMER_LOGIN_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`CUSTOMER_LOGIN_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`CUSTOMER_LOGIN_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`CUSTOMER_LOGIN`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`CUSTOMER_LOGIN` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`CUSTOMER_LOGIN` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `LOGIN_EMAIL` VARCHAR(32) NOT NULL,
  `LOGIN_PASSWORD` VARCHAR(32) NOT NULL,
  `FIRST_NAME` VARCHAR(32) NOT NULL,
  `LAST_NAME` VARCHAR(32) NOT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `REMARK` TEXT NULL,
  `PROFILE_IMAGE` BLOB NULL,
  `CUSTOMER_LOGIN_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `CUSTOMER_LOGIN_STATUS_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `loginEmail_UNIQUE` (`LOGIN_EMAIL` ASC),
  INDEX `fk_CustomerLogin_CustomerLoginStatus1_idx` (`CUSTOMER_LOGIN_STATUS_ID` ASC),
  CONSTRAINT `fk_CustomerLogin_CustomerLoginStatus`
    FOREIGN KEY (`CUSTOMER_LOGIN_STATUS_ID`)
    REFERENCES `smartpay`.`CUSTOMER_LOGIN_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`CUSTOMER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`CUSTOMER` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`CUSTOMER` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` VARCHAR(32) NOT NULL,
  `LAST_NAME` VARCHAR(32) NOT NULL,
  `EMAIL` VARCHAR(32) NOT NULL,
  `ADDRESS_1` VARCHAR(128) NOT NULL,
  `ADDRESS_2` VARCHAR(128) NULL,
  `CITY` VARCHAR(32) NOT NULL,
  `STATE` VARCHAR(32) NOT NULL,
  `ZIP_CODE` VARCHAR(32) NOT NULL,
  `COUNTRY` VARCHAR(32) NOT NULL,
  `TEL` VARCHAR(32) NULL,
  `CUSTOMER_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `CUSTOMER_LOGIN_ID` BIGINT UNSIGNED NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NULL,
  `REMARK` TEXT NULL,
  PRIMARY KEY (`ID`, `CUSTOMER_STATUS_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Customer_CustomerStatus1_idx` (`CUSTOMER_STATUS_ID` ASC),
  INDEX `fk_Customer_CustomerLogin_idx` (`CUSTOMER_LOGIN_ID` ASC),
  CONSTRAINT `fk_Customer_CustomerStatus`
    FOREIGN KEY (`CUSTOMER_STATUS_ID`)
    REFERENCES `smartpay`.`CUSTOMER_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Customer_CustomerLogin`
    FOREIGN KEY (`CUSTOMER_LOGIN_ID`)
    REFERENCES `smartpay`.`CUSTOMER_LOGIN` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ORDER` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ORDER` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `MERCHANT_NUMBER` VARCHAR(32) NOT NULL,
  `AMOUNT` FLOAT NOT NULL,
  `GOODS_NAME` VARCHAR(128) NOT NULL,
  `GOODS_AMOUNT` VARCHAR(128) NOT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `REMARK` TEXT NULL,
  `SITE_ID` BIGINT UNSIGNED NOT NULL,
  `ORDER_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `CURRENCY_ID` BIGINT UNSIGNED NOT NULL,
  `CUSTOMER_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `SITE_ID`, `ORDER_STATUS_ID`, `CURRENCY_ID`, `CUSTOMER_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Order_Site1_idx` (`SITE_ID` ASC),
  INDEX `fk_Order_OrderStatus1_idx` (`ORDER_STATUS_ID` ASC),
  INDEX `fk_Order_Currency1_idx` (`CURRENCY_ID` ASC),
  INDEX `fk_Order_Customer1_idx` (`CUSTOMER_ID` ASC),
  CONSTRAINT `fk_Order_Site`
    FOREIGN KEY (`SITE_ID`)
    REFERENCES `smartpay`.`SITE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_OrderStatus`
    FOREIGN KEY (`ORDER_STATUS_ID`)
    REFERENCES `smartpay`.`ORDER_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_Currency`
    FOREIGN KEY (`CURRENCY_ID`)
    REFERENCES `smartpay`.`CURRENCY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_Customer1`
    FOREIGN KEY (`CUSTOMER_ID`)
    REFERENCES `smartpay`.`CUSTOMER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`PAYMENT_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`PAYMENT_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`PAYMENT_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`PAYMENT_TYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`PAYMENT_TYPE` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`PAYMENT_TYPE` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`PAYMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`PAYMENT` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`PAYMENT` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `AMOUNT` FLOAT NOT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `SUCCESS_TIME` DATETIME NULL,
  `BANK_TRANSACTION_NUMBER` VARCHAR(128) NOT NULL,
  `BANK_NAME` VARCHAR(128) NULL,
  `BANK_RETURN_CODE` VARCHAR(128) NOT NULL,
  `BANK_ACCOUNT_NUMBER` VARCHAR(128) NOT NULL,
  `BANK_ACCOUNT_EXP_DATE` DATE NOT NULL,
  `REMARK` TEXT NULL,
  `BILL_FIRST_NAME` VARCHAR(32) NOT NULL,
  `BILL_LAST_NAME` VARCHAR(32) NULL,
  `BILL_ADDRESS_1` VARCHAR(128) NOT NULL,
  `BILL_ADDRESS_2` VARCHAR(128) NULL,
  `BILL_CITY` VARCHAR(32) NOT NULL,
  `BILL_STATE` VARCHAR(32) NOT NULL,
  `BILL_ZIP_CODE` VARCHAR(32) NOT NULL,
  `BILL_COUNTRY` VARCHAR(32) NULL,
  `PAYMENT_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `PAYMENT_TYPE_ID` BIGINT UNSIGNED NOT NULL,
  `ORDER_ID` BIGINT UNSIGNED NOT NULL,
  `CURRENCY_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `PAYMENT_STATUS_ID`, `PAYMENT_TYPE_ID`, `ORDER_ID`, `CURRENCY_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Payment_PaymentStatus1_idx` (`PAYMENT_STATUS_ID` ASC),
  INDEX `fk_Payment_PaymentType1_idx` (`PAYMENT_TYPE_ID` ASC),
  INDEX `fk_Payment_Order1_idx` (`ORDER_ID` ASC),
  INDEX `fk_Payment_Currency1_idx` (`CURRENCY_ID` ASC),
  CONSTRAINT `fk_Payment_PaymentStatus`
    FOREIGN KEY (`PAYMENT_STATUS_ID`)
    REFERENCES `smartpay`.`PAYMENT_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Payment_PaymentType`
    FOREIGN KEY (`PAYMENT_TYPE_ID`)
    REFERENCES `smartpay`.`PAYMENT_TYPE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Payment_Order`
    FOREIGN KEY (`ORDER_ID`)
    REFERENCES `smartpay`.`ORDER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Payment_Currency`
    FOREIGN KEY (`CURRENCY_ID`)
    REFERENCES `smartpay`.`CURRENCY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`SHIPMENT_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`SHIPMENT_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`SHIPMENT_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`SHIPMENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`SHIPMENT` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`SHIPMENT` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `CARRIER` VARCHAR(32) NOT NULL,
  `TRACKING_NUMBER` VARCHAR(64) NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `REMARK` TEXT NULL,
  `FIRST_NAME` VARCHAR(32) NOT NULL,
  `LAST_NAME` VARCHAR(32) NOT NULL,
  `ADDRESS_1` VARCHAR(128) NOT NULL,
  `ADDRESS_2` VARCHAR(128) NULL,
  `CITY` VARCHAR(32) NOT NULL,
  `STATE` VARCHAR(32) NOT NULL,
  `COUNTRY` VARCHAR(32) NOT NULL,
  `ZIP_CODE` VARCHAR(16) NOT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `SHIPMENT_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `ORDER_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `SHIPMENT_STATUS_ID`, `ORDER_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Shipment_ShipmentStatus1_idx` (`SHIPMENT_STATUS_ID` ASC),
  INDEX `fk_Shipment_Order1_idx` (`ORDER_ID` ASC),
  CONSTRAINT `fk_Shipment_ShipmentStatus`
    FOREIGN KEY (`SHIPMENT_STATUS_ID`)
    REFERENCES `smartpay`.`SHIPMENT_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shipment_Order`
    FOREIGN KEY (`ORDER_ID`)
    REFERENCES `smartpay`.`ORDER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ADMINISTRATOR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ADMINISTRATOR` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ADMINISTRATOR` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR(32) NOT NULL,
  `PASSWORD` VARCHAR(32) NOT NULL,
  `FIRST_NAME` VARCHAR(32) NOT NULL,
  `LAST_NAME` VARCHAR(32) NOT NULL,
  `EMAIL` VARCHAR(32) NOT NULL,
  `PROFILE_IMAGE` BLOB NULL,
  `REMARK` TEXT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `ROLE_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `ROLE_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `username_UNIQUE` (`USERNAME` ASC),
  UNIQUE INDEX `email_UNIQUE` (`EMAIL` ASC),
  INDEX `fk_Administrator_Role1_idx` (`ROLE_ID` ASC),
  CONSTRAINT `fk_Administrator_Role`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `smartpay`.`ROLE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`RETURN_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`RETURN_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`RETURN_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`REFUND_STATUS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`REFUND_STATUS` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`REFUND_STATUS` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(32) NOT NULL,
  `DESCRIPTION` TEXT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  UNIQUE INDEX `name_UNIQUE` (`NAME` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`RETURN`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`RETURN` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`RETURN` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `MERCHANT_NUMBER` VARCHAR(32) NOT NULL,
  `GOODS_NAME` VARCHAR(128) NOT NULL,
  `GOODS_AMOUNT` VARCHAR(128) NOT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `REMARK` TEXT NULL,
  `RETURN_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `ORDER_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `RETURN_STATUS_ID`, `ORDER_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Return_ReturnStatus1_idx` (`RETURN_STATUS_ID` ASC),
  INDEX `fk_Return_Order1_idx` (`ORDER_ID` ASC),
  CONSTRAINT `fk_Return_ReturnStatus`
    FOREIGN KEY (`RETURN_STATUS_ID`)
    REFERENCES `smartpay`.`RETURN_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Return_Order`
    FOREIGN KEY (`ORDER_ID`)
    REFERENCES `smartpay`.`ORDER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`REFUND`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`REFUND` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`REFUND` (
  `ID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `AMOUNT` FLOAT NOT NULL,
  `CREATED_TIME` DATETIME NOT NULL,
  `UPDATED_TIME` DATETIME NOT NULL,
  `SUCCESS_TIME` DATETIME NULL,
  `BANK_TRANSACTION_NUMBER` VARCHAR(128) NOT NULL,
  `BANK_NAME` VARCHAR(128) NULL,
  `BANK_RETURN_CODE` VARCHAR(128) NOT NULL,
  `BANK_ACCOUNT_NUMBER` VARCHAR(128) NOT NULL,
  `BANK_ACCOUNT_EXP_DATE` DATE NOT NULL,
  `REMARK` TEXT NULL,
  `BILL_FIRST_NAME` VARCHAR(32) NOT NULL,
  `BILL_LAST_NAME` VARCHAR(32) NULL,
  `BILL_ADDRESS_1` VARCHAR(128) NOT NULL,
  `BILL_ADDRESS_2` VARCHAR(128) NULL,
  `BILL_CITY` VARCHAR(32) NOT NULL,
  `BILL_STATE` VARCHAR(32) NOT NULL,
  `BILL_ZIP_CODE` VARCHAR(32) NOT NULL,
  `BILL_COUNTRY` VARCHAR(32) NULL,
  `REFUND_STATUS_ID` BIGINT UNSIGNED NOT NULL,
  `ORDER_ID` BIGINT UNSIGNED NOT NULL,
  `CURRENCY_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`, `REFUND_STATUS_ID`, `ORDER_ID`, `CURRENCY_ID`),
  UNIQUE INDEX `id_UNIQUE` (`ID` ASC),
  INDEX `fk_Refund_RefundStatus1_idx` (`REFUND_STATUS_ID` ASC),
  INDEX `fk_Refund_Order1_idx` (`ORDER_ID` ASC),
  INDEX `fk_REFUND_CURRENCY1_idx` (`CURRENCY_ID` ASC),
  CONSTRAINT `fk_Refund_RefundStatus`
    FOREIGN KEY (`REFUND_STATUS_ID`)
    REFERENCES `smartpay`.`REFUND_STATUS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Refund_Order`
    FOREIGN KEY (`ORDER_ID`)
    REFERENCES `smartpay`.`ORDER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_REFUND_CURRENCY1`
    FOREIGN KEY (`CURRENCY_ID`)
    REFERENCES `smartpay`.`CURRENCY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`ROLE_PERMISSION_MAP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`ROLE_PERMISSION_MAP` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`ROLE_PERMISSION_MAP` (
  `ROLE_ID` BIGINT UNSIGNED NOT NULL,
  `PERMISSION_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ROLE_ID`, `PERMISSION_ID`),
  INDEX `fk_Role_has_Permission_Permission1_idx` (`PERMISSION_ID` ASC),
  INDEX `fk_Role_has_Permission_Role1_idx` (`ROLE_ID` ASC),
  CONSTRAINT `fk_Role_has_Permission_Role`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `smartpay`.`ROLE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Role_has_Permission_Permission`
    FOREIGN KEY (`PERMISSION_ID`)
    REFERENCES `smartpay`.`PERMISSION` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `smartpay`.`USER_ROLE_MAP`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `smartpay`.`USER_ROLE_MAP` ;

CREATE TABLE IF NOT EXISTS `smartpay`.`USER_ROLE_MAP` (
  `USER_ID` BIGINT UNSIGNED NOT NULL,
  `ROLE_ID` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`USER_ID`, `ROLE_ID`),
  INDEX `fk_User_has_Role_Role1_idx` (`ROLE_ID` ASC),
  INDEX `fk_User_has_Role_User1_idx` (`USER_ID` ASC),
  CONSTRAINT `fk_User_has_Role_User`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `smartpay`.`USER` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Role_Role`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `smartpay`.`ROLE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `smartpay`.`MERCHANT_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`MERCHANT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Normal', 'Normal');
INSERT INTO `smartpay`.`MERCHANT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Frozen', 'Frozen');
INSERT INTO `smartpay`.`MERCHANT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`CREDENTIAL_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`CREDENTIAL_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Submitted', 'The credential is submitted for approval.');
INSERT INTO `smartpay`.`CREDENTIAL_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Approved', 'The credential is approved.');
INSERT INTO `smartpay`.`CREDENTIAL_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Denied', 'The credential is denied.');
INSERT INTO `smartpay`.`CREDENTIAL_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Expired', 'The credential has expired.');
INSERT INTO `smartpay`.`CREDENTIAL_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`CREDENTIAL_TYPE`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`CREDENTIAL_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Certificate', 'Certificate');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`FEE_TYPE`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`FEE_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Static', 'Static amount for every transaction');
INSERT INTO `smartpay`.`FEE_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Percentage', 'Percentage based on the transaction amount');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`ENCRYPTION_TYPE`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`ENCRYPTION_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'MD5', 'MD5 Algorithm');
INSERT INTO `smartpay`.`ENCRYPTION_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'SHA', 'SHA Algorithm');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`ACCOUNT_TYPE`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`ACCOUNT_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Bank', 'The account is the bank account number.');
INSERT INTO `smartpay`.`ACCOUNT_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Card', 'The account is a bank card.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`ACCOUNT_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`ACCOUNT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Normal', 'The merchant account is normal.');
INSERT INTO `smartpay`.`ACCOUNT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Frozen', 'The merchant account is frozen.');
INSERT INTO `smartpay`.`ACCOUNT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`SITE_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`SITE_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Created', 'Created and wait for approval');
INSERT INTO `smartpay`.`SITE_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Approved', 'Approved to operation');
INSERT INTO `smartpay`.`SITE_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Frozen', 'Frozen');
INSERT INTO `smartpay`.`SITE_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Declined', 'Declined');
INSERT INTO `smartpay`.`SITE_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`USER_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`USER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Normal', 'Normal');
INSERT INTO `smartpay`.`USER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Deactived', 'Deactived');
INSERT INTO `smartpay`.`USER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Frozen', 'Frozen');
INSERT INTO `smartpay`.`USER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`ROLE`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`ROLE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Admin', 'System Admin');
INSERT INTO `smartpay`.`ROLE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Merchant Admin', 'Merchant Admin');
INSERT INTO `smartpay`.`ROLE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Merchant Operator', 'Merchant Operator');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`ORDER_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Initiated', 'The order is initiated.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Confirmed', 'The order is confirmed.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Paid', 'The order is paid.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Preparing for Shipment', 'The order is parpared for shipment, cannot be cancel at this stage.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Cancelled', 'The order is cancelled.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Shipped', 'The order is shipped.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Delivered', 'The order is delivered.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Returned', 'The order is returned.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Refunded', 'The order is refunded.');
INSERT INTO `smartpay`.`ORDER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`CURRENCY`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`CURRENCY` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'USD', 'US Dollar');
INSERT INTO `smartpay`.`CURRENCY` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'RMB', 'Chinese Yuan');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`CUSTOMER_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`CUSTOMER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Normal', 'Normal');
INSERT INTO `smartpay`.`CUSTOMER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Frozen', 'Frozen');
INSERT INTO `smartpay`.`CUSTOMER_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`CUSTOMER_LOGIN_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`CUSTOMER_LOGIN_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Normal', 'Normal');
INSERT INTO `smartpay`.`CUSTOMER_LOGIN_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Frozen', 'Frozen');
INSERT INTO `smartpay`.`CUSTOMER_LOGIN_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`PAYMENT_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`PAYMENT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Approved', 'Approved');
INSERT INTO `smartpay`.`PAYMENT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Declined', 'Declined');
INSERT INTO `smartpay`.`PAYMENT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`PAYMENT_TYPE`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`PAYMENT_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Credit Card', 'Credit Card');
INSERT INTO `smartpay`.`PAYMENT_TYPE` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Debit Card', 'Debit Card');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`SHIPMENT_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`SHIPMENT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Shipped', 'Shipped');
INSERT INTO `smartpay`.`SHIPMENT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Delivered', 'Delivered');
INSERT INTO `smartpay`.`SHIPMENT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Lost', 'Lost');
INSERT INTO `smartpay`.`SHIPMENT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Damaged', 'Damaged');
INSERT INTO `smartpay`.`SHIPMENT_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`ADMINISTRATOR`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`ADMINISTRATOR` (`ID`, `USERNAME`, `PASSWORD`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PROFILE_IMAGE`, `REMARK`, `CREATED_TIME`, `UPDATED_TIME`, `ROLE_ID`) VALUES (NULL, 'admin', 'admin', 'Admin', 'Admin', 'ironaire@gmail.com', NULL, NULL, 'NOW()', 'NOW()', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`RETURN_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`RETURN_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Shipped', 'Shipped');
INSERT INTO `smartpay`.`RETURN_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Delivered', 'Delivered');
INSERT INTO `smartpay`.`RETURN_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;


-- -----------------------------------------------------
-- Data for table `smartpay`.`REFUND_STATUS`
-- -----------------------------------------------------
START TRANSACTION;
USE `smartpay`;
INSERT INTO `smartpay`.`REFUND_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Issued', 'Issued');
INSERT INTO `smartpay`.`REFUND_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Funded', 'Funded');
INSERT INTO `smartpay`.`REFUND_STATUS` (`ID`, `NAME`, `DESCRIPTION`) VALUES (NULL, 'Archived', 'Archived');

COMMIT;

