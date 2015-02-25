SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `Smartpay`;
CREATE SCHEMA IF NOT EXISTS `Smartpay` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `Smartpay`;

-- -----------------------------------------------------
-- Table `Smartpay`.`MerchantStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`MerchantStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`MerchantStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`CredentialStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`CredentialStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`CredentialStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`CredentialType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`CredentialType`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`CredentialType` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Credential`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Credential`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Credential` (
  `id`               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `content`          VARCHAR(32) NOT NULL,
  `expirationDate`   DATE        NOT NULL,
  `remark`           TEXT        NULL,
  `createdTime`      DATETIME    NOT NULL,
  `updatedTime`      DATETIME    NOT NULL,
  `credentialStatus` BIGINT UNSIGNED NOT NULL,
  `credentialType`   BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `credentialStatus`, `credentialType`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Credential_CredentialStatus1_idx` (`credentialStatus` ASC),
  INDEX `fk_Credential_CredentialType1_idx` (`credentialType` ASC),
  CONSTRAINT `fk_Credential_CredentialStatus`
  FOREIGN KEY (`credentialStatus`)
  REFERENCES `Smartpay`.`CredentialStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Credential_CredentialType`
  FOREIGN KEY (`credentialType`)
  REFERENCES `Smartpay`.`CredentialType` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`FeeType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`FeeType`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`FeeType` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Fee`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Fee`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Fee` (
  `id`      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `value`   FLOAT NOT NULL,
  `remark`  TEXT  NULL,
  `feeType` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `feeType`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Fee_FeeType1_idx` (`feeType` ASC),
  CONSTRAINT `fk_Fee_FeeType`
  FOREIGN KEY (`feeType`)
  REFERENCES `Smartpay`.`FeeType` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`EncryptionType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`EncryptionType`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`EncryptionType` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` MEDIUMTEXT  NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Encryption`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Encryption`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Encryption` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `key`            VARCHAR(256) NOT NULL,
  `createdTime`    DATETIME     NOT NULL,
  `updatedTime`    DATETIME     NOT NULL,
  `encryptionType` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `encryptionType`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC),
  INDEX `fk_Encryption_EncryptionType1_idx` (`encryptionType` ASC),
  CONSTRAINT `fk_Encryption_EncryptionType`
  FOREIGN KEY (`encryptionType`)
  REFERENCES `Smartpay`.`EncryptionType` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Merchant`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Merchant`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Merchant` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number`         VARCHAR(32)  NOT NULL,
  `name`           VARCHAR(32)  NOT NULL,
  `address`        VARCHAR(128) NULL,
  `contact`        VARCHAR(32)  NULL,
  `tel`            VARCHAR(32)  NULL,
  `email`          VARCHAR(32)  NULL,
  `logo`           BLOB         NULL,
  `createdTime`    DATETIME     NOT NULL,
  `updatedTime`    DATETIME     NOT NULL,
  `remark`         TEXT         NULL,
  `merchantStatus` BIGINT UNSIGNED NOT NULL,
  `credential`     BIGINT UNSIGNED NOT NULL,
  `commissionFee`  BIGINT UNSIGNED NOT NULL,
  `encryption`     BIGINT UNSIGNED NOT NULL,
  `returnFee`      BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `merchantStatus`, `credential`, `commissionFee`, `encryption`, `returnFee`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  INDEX `fk_Merchant_MerchantStatus_idx` (`merchantStatus` ASC),
  INDEX `fk_Merchant_Credential1_idx` (`credential` ASC),
  INDEX `fk_Merchant_Fee1_idx` (`commissionFee` ASC),
  INDEX `fk_Merchant_Encryption1_idx` (`encryption` ASC),
  INDEX `fk_Merchant_Fee1_idx1` (`returnFee` ASC),
  CONSTRAINT `fk_Merchant_MerchantStatus`
  FOREIGN KEY (`merchantStatus`)
  REFERENCES `Smartpay`.`MerchantStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Merchant_Credential`
  FOREIGN KEY (`credential`)
  REFERENCES `Smartpay`.`Credential` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Merchant_Commission_Fee`
  FOREIGN KEY (`commissionFee`)
  REFERENCES `Smartpay`.`Fee` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Merchant_Encryption`
  FOREIGN KEY (`encryption`)
  REFERENCES `Smartpay`.`Encryption` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Merchant_Return_Fee`
  FOREIGN KEY (`returnFee`)
  REFERENCES `Smartpay`.`Fee` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`AccountType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`AccountType`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`AccountType` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`AccountStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`AccountStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`AccountStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Account`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Account` (
  `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number`        VARCHAR(32) NOT NULL,
  `createdTime`   DATETIME    NOT NULL,
  `updatedTime`   DATETIME    NOT NULL,
  `remark`        TEXT        NULL,
  `merchant`      BIGINT UNSIGNED NOT NULL,
  `accountType`   BIGINT UNSIGNED NOT NULL,
  `accountStatus` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `merchant`, `accountType`, `accountStatus`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  INDEX `fk_Account_Merchant1_idx` (`merchant` ASC),
  INDEX `fk_Account_AccountType1_idx` (`accountType` ASC),
  INDEX `fk_Account_AccountStatus1_idx` (`accountStatus` ASC),
  CONSTRAINT `fk_Account_Merchant1`
  FOREIGN KEY (`merchant`)
  REFERENCES `Smartpay`.`Merchant` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Account_AccountType`
  FOREIGN KEY (`accountType`)
  REFERENCES `Smartpay`.`AccountType` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Account_AccountStatus`
  FOREIGN KEY (`accountStatus`)
  REFERENCES `Smartpay`.`AccountStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`SiteStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`SiteStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`SiteStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Site`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Site`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Site` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32)  NOT NULL,
  `url`         VARCHAR(128) NOT NULL,
  `logo`        BLOB         NULL,
  `remark`      TEXT         NULL,
  `createdTime` DATETIME     NOT NULL,
  `updatedTime` DATETIME     NOT NULL,
  `siteStatus`  BIGINT UNSIGNED NOT NULL,
  `merchant`    BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `siteStatus`, `merchant`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `url_UNIQUE` (`url` ASC),
  INDEX `fk_Site_SiteStatus1_idx` (`siteStatus` ASC),
  INDEX `fk_Site_Merchant1_idx` (`merchant` ASC),
  CONSTRAINT `fk_Site_SiteStatus`
  FOREIGN KEY (`siteStatus`)
  REFERENCES `Smartpay`.`SiteStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Site_Merchant`
  FOREIGN KEY (`merchant`)
  REFERENCES `Smartpay`.`Merchant` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`UserStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`UserStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`UserStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`User`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`User` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username`     VARCHAR(32) NOT NULL,
  `password`     VARCHAR(32) NOT NULL,
  `firstName`    VARCHAR(32) NOT NULL,
  `lastName`     VARCHAR(32) NOT NULL,
  `email`        VARCHAR(32) NOT NULL,
  `profileImage` BLOB        NULL,
  `remark`       TEXT        NULL,
  `createdTime`  DATETIME    NOT NULL,
  `updatedTime`  DATETIME    NOT NULL,
  `merchant`     BIGINT UNSIGNED NOT NULL,
  `userStatus`   BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `merchant`, `userStatus`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_User_Merchant1_idx` (`merchant` ASC),
  INDEX `fk_User_UserStatus1_idx` (`userStatus` ASC),
  CONSTRAINT `fk_User_Merchant`
  FOREIGN KEY (`merchant`)
  REFERENCES `Smartpay`.`Merchant` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_UserStatus`
  FOREIGN KEY (`userStatus`)
  REFERENCES `Smartpay`.`UserStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Role`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Role` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(64) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Permission`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Permission` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(64) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`OrderStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`OrderStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`OrderStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Currency`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Currency`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Currency` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`CustomerStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`CustomerStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`CustomerStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`CustomerLoginStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`CustomerLoginStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`CustomerLoginStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`CustomerLogin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`CustomerLogin`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`CustomerLogin` (
  `id`                  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number`              VARCHAR(32) NOT NULL,
  `loginEmail`          VARCHAR(32) NOT NULL,
  `loginPassword`       VARCHAR(32) NOT NULL,
  `firstName`           VARCHAR(32) NOT NULL,
  `lastName`            VARCHAR(32) NOT NULL,
  `createdTime`         DATETIME    NOT NULL,
  `updatedTime`         DATETIME    NOT NULL,
  `remark`              TEXT        NULL,
  `profileImage`        BLOB        NULL,
  `customerLoginStatus` BIGINT UNSIGNED NOT NULL,
  `customer`            BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `customerLoginStatus`, `customer`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  UNIQUE INDEX `loginEmail_UNIQUE` (`loginEmail` ASC),
  INDEX `fk_CustomerLogin_CustomerLoginStatus1_idx` (`customerLoginStatus` ASC),
  INDEX `fk_CustomerLogin_Customer1_idx` (`customer` ASC),
  CONSTRAINT `fk_CustomerLogin_CustomerLoginStatus`
  FOREIGN KEY (`customerLoginStatus`)
  REFERENCES `Smartpay`.`CustomerLoginStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_CustomerLogin_Customer`
  FOREIGN KEY (`customer`)
  REFERENCES `Smartpay`.`Customer` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Customer`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Customer` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number`         VARCHAR(32)  NOT NULL,
  `firstName`      VARCHAR(32)  NOT NULL,
  `lastName`       VARCHAR(32)  NOT NULL,
  `email`          VARCHAR(32)  NOT NULL,
  `address1`       VARCHAR(128) NOT NULL,
  `address2`       VARCHAR(128) NULL,
  `city`           VARCHAR(32)  NOT NULL,
  `state`          VARCHAR(32)  NOT NULL,
  `zipCode`        VARCHAR(32)  NOT NULL,
  `country`        VARCHAR(32)  NOT NULL,
  `tel`            VARCHAR(32)  NULL,
  `customerStatus` BIGINT UNSIGNED NOT NULL,
  `customerLogin`  BIGINT UNSIGNED NULL,
  PRIMARY KEY (`id`, `customerStatus`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  INDEX `fk_Customer_CustomerStatus1_idx` (`customerStatus` ASC),
  INDEX `fk_Customer_CustomerLogin_idx` (`customerLogin` ASC),
  CONSTRAINT `fk_Customer_CustomerStatus`
  FOREIGN KEY (`customerStatus`)
  REFERENCES `Smartpay`.`CustomerStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Customer_CustomerLogin`
  FOREIGN KEY (`customerLogin`)
  REFERENCES `Smartpay`.`CustomerLogin` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Order`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Order` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number`      VARCHAR(32)  NOT NULL,
  `amount`      FLOAT        NOT NULL,
  `goodsName`   VARCHAR(128) NOT NULL,
  `goodsAmount` VARCHAR(128) NOT NULL,
  `createdTime` DATETIME     NOT NULL,
  `updatedtime` DATETIME     NOT NULL,
  `Remark`      TEXT         NULL,
  `site`        BIGINT UNSIGNED NOT NULL,
  `orderStatus` BIGINT UNSIGNED NOT NULL,
  `currency`    BIGINT UNSIGNED NOT NULL,
  `Customer_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `site`, `orderStatus`, `currency`, `Customer_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  INDEX `fk_Order_Site1_idx` (`site` ASC),
  INDEX `fk_Order_OrderStatus1_idx` (`orderStatus` ASC),
  INDEX `fk_Order_Currency1_idx` (`currency` ASC),
  INDEX `fk_Order_Customer1_idx` (`Customer_id` ASC),
  CONSTRAINT `fk_Order_Site`
  FOREIGN KEY (`site`)
  REFERENCES `Smartpay`.`Site` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_OrderStatus`
  FOREIGN KEY (`orderStatus`)
  REFERENCES `Smartpay`.`OrderStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_Currency`
  FOREIGN KEY (`currency`)
  REFERENCES `Smartpay`.`Currency` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_Customer1`
  FOREIGN KEY (`Customer_id`)
  REFERENCES `Smartpay`.`Customer` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`PaymentStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`PaymentStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`PaymentStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`PaymentType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`PaymentType`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`PaymentType` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Payment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Payment`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Payment` (
  `id`                    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number`                VARCHAR(32)  NOT NULL,
  `createdTime`           DATETIME     NOT NULL,
  `updatedTime`           DATETIME     NOT NULL,
  `successTime`           DATETIME     NULL,
  `bankTransactionNumber` VARCHAR(32)  NOT NULL,
  `bankName`              VARCHAR(32)  NULL,
  `bankReturnCode`        VARCHAR(32)  NOT NULL,
  `bankAccountNumber`     VARCHAR(32)  NOT NULL,
  `bankAccountExpDate`    DATE         NOT NULL,
  `remark`                TEXT         NULL,
  `billFirstName`         VARCHAR(32)  NOT NULL,
  `billLastName`          VARCHAR(32)  NULL,
  `billAddress1`          VARCHAR(128) NOT NULL,
  `billAddress2`          VARCHAR(128) NULL,
  `billCity`              VARCHAR(32)  NOT NULL,
  `billState`             VARCHAR(32)  NOT NULL,
  `billZipCode`           VARCHAR(32)  NOT NULL,
  `billCountry`           VARCHAR(32)  NULL,
  `paymentStatus`         BIGINT UNSIGNED NOT NULL,
  `paymentType`           BIGINT UNSIGNED NOT NULL,
  `order`                 BIGINT UNSIGNED NOT NULL,
  `currency`              BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `paymentStatus`, `paymentType`, `order`, `currency`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  INDEX `fk_Payment_PaymentStatus1_idx` (`paymentStatus` ASC),
  INDEX `fk_Payment_PaymentType1_idx` (`paymentType` ASC),
  INDEX `fk_Payment_Order1_idx` (`order` ASC),
  INDEX `fk_Payment_Currency1_idx` (`currency` ASC),
  CONSTRAINT `fk_Payment_PaymentStatus`
  FOREIGN KEY (`paymentStatus`)
  REFERENCES `Smartpay`.`PaymentStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Payment_PaymentType`
  FOREIGN KEY (`paymentType`)
  REFERENCES `Smartpay`.`PaymentType` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Payment_Order`
  FOREIGN KEY (`order`)
  REFERENCES `Smartpay`.`Order` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Payment_Currency`
  FOREIGN KEY (`currency`)
  REFERENCES `Smartpay`.`Currency` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`ShipmentStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`ShipmentStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`ShipmentStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Shipment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Shipment`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Shipment` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `carrier`        VARCHAR(32)  NOT NULL,
  `tracking`       VARCHAR(64)  NOT NULL,
  `udpatedTime`    DATETIME     NOT NULL,
  `remark`         TEXT         NULL,
  `firstName`      VARCHAR(32)  NOT NULL,
  `lastName`       VARCHAR(32)  NOT NULL,
  `address1`       VARCHAR(128) NOT NULL,
  `address2`       VARCHAR(128) NULL,
  `city`           VARCHAR(32)  NOT NULL,
  `state`          VARCHAR(32)  NOT NULL,
  `country`        VARCHAR(32)  NOT NULL,
  `zipCode`        VARCHAR(16)  NOT NULL,
  `createdTime`    DATETIME     NOT NULL,
  `shipmentStatus` BIGINT UNSIGNED NOT NULL,
  `order`          BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `shipmentStatus`, `order`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_Shipment_ShipmentStatus1_idx` (`shipmentStatus` ASC),
  INDEX `fk_Shipment_Order1_idx` (`order` ASC),
  CONSTRAINT `fk_Shipment_ShipmentStatus`
  FOREIGN KEY (`shipmentStatus`)
  REFERENCES `Smartpay`.`ShipmentStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Shipment_Order`
  FOREIGN KEY (`order`)
  REFERENCES `Smartpay`.`Order` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Administrator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Administrator`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Administrator` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username`     VARCHAR(32) NOT NULL,
  `password`     VARCHAR(32) NOT NULL,
  `firstName`    VARCHAR(32) NOT NULL,
  `lastName`     VARCHAR(32) NOT NULL,
  `email`        VARCHAR(32) NOT NULL,
  `profileImage` BLOB        NULL,
  `remark`       TEXT        NULL,
  `createdTime`  DATETIME    NOT NULL,
  `updatedTime`  DATETIME    NOT NULL,
  `role`         BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `role`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_Administrator_Role1_idx` (`role` ASC),
  CONSTRAINT `fk_Administrator_Role`
  FOREIGN KEY (`role`)
  REFERENCES `Smartpay`.`Role` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`ReturnStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`ReturnStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`ReturnStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`RefundStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`RefundStatus`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`RefundStatus` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32) NOT NULL,
  `description` TEXT        NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Return`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Return`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Return` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number`       VARCHAR(32)  NOT NULL,
  `amount`       FLOAT        NOT NULL,
  `goodsName`    VARCHAR(128) NOT NULL,
  `goodsAmount`  VARCHAR(128) NOT NULL,
  `createdTime`  DATETIME     NOT NULL,
  `updatedtime`  DATETIME     NOT NULL,
  `Remark`       TEXT         NULL,
  `returnStatus` BIGINT UNSIGNED NOT NULL,
  `order`        BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `returnStatus`, `order`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  INDEX `fk_Return_ReturnStatus1_idx` (`returnStatus` ASC),
  INDEX `fk_Return_Order1_idx` (`order` ASC),
  CONSTRAINT `fk_Return_ReturnStatus`
  FOREIGN KEY (`returnStatus`)
  REFERENCES `Smartpay`.`ReturnStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Return_Order`
  FOREIGN KEY (`order`)
  REFERENCES `Smartpay`.`Order` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`Refund`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`Refund`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`Refund` (
  `id`                    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number`                VARCHAR(32)  NOT NULL,
  `createdTime`           DATETIME     NOT NULL,
  `updatedTime`           DATETIME     NOT NULL,
  `successTime`           DATETIME     NULL,
  `bankTransactionNumber` VARCHAR(32)  NOT NULL,
  `bankName`              VARCHAR(32)  NULL,
  `bankReturnCode`        VARCHAR(32)  NOT NULL,
  `bankAccountNumber`     VARCHAR(32)  NOT NULL,
  `bankAccountExpDate`    DATE         NOT NULL,
  `remark`                TEXT         NULL,
  `billFirstName`         VARCHAR(32)  NOT NULL,
  `billLastName`          VARCHAR(32)  NULL,
  `billAddress1`          VARCHAR(128) NOT NULL,
  `billAddress2`          VARCHAR(128) NULL,
  `billCity`              VARCHAR(32)  NOT NULL,
  `billState`             VARCHAR(32)  NOT NULL,
  `billZipCode`           VARCHAR(32)  NOT NULL,
  `billCountry`           VARCHAR(32)  NULL,
  `refundStatus`          BIGINT UNSIGNED NOT NULL,
  `order`                 BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `refundStatus`, `order`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC),
  INDEX `fk_Refund_RefundStatus1_idx` (`refundStatus` ASC),
  INDEX `fk_Refund_Order1_idx` (`order` ASC),
  CONSTRAINT `fk_Refund_RefundStatus`
  FOREIGN KEY (`refundStatus`)
  REFERENCES `Smartpay`.`RefundStatus` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Refund_Order`
  FOREIGN KEY (`order`)
  REFERENCES `Smartpay`.`Order` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`RolePermission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`RolePermission`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`RolePermission` (
  `role`       BIGINT UNSIGNED NOT NULL,
  `permission` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`role`, `permission`),
  INDEX `fk_Role_has_Permission_Permission1_idx` (`permission` ASC),
  INDEX `fk_Role_has_Permission_Role1_idx` (`role` ASC),
  CONSTRAINT `fk_Role_has_Permission_Role`
  FOREIGN KEY (`role`)
  REFERENCES `Smartpay`.`Role` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_Role_has_Permission_Permission`
  FOREIGN KEY (`permission`)
  REFERENCES `Smartpay`.`Permission` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Smartpay`.`UserRole`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Smartpay`.`UserRole`;

CREATE TABLE IF NOT EXISTS `Smartpay`.`UserRole` (
  `user` BIGINT UNSIGNED NOT NULL,
  `role` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`user`, `role`),
  INDEX `fk_User_has_Role_Role1_idx` (`role` ASC),
  INDEX `fk_User_has_Role_User1_idx` (`user` ASC),
  CONSTRAINT `fk_User_has_Role_User`
  FOREIGN KEY (`user`)
  REFERENCES `Smartpay`.`User` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Role_Role`
  FOREIGN KEY (`role`)
  REFERENCES `Smartpay`.`Role` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
)
ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
