-- -----------------------------------------------------
-- Data for table MERCHANT_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO MERCHANT_STATUSES (MCST_ID, MCST_NAME, MCST_DESCRIPTION, MCST_ACTIVE, MCST_CODE) VALUES (NULL, 'Normal', 'Normal', 1, '200');
INSERT INTO MERCHANT_STATUSES (MCST_ID, MCST_NAME, MCST_DESCRIPTION, MCST_ACTIVE, MCST_CODE) VALUES (NULL, 'Frozen', 'Frozen', 1, '500');

COMMIT;


-- -----------------------------------------------------
-- Data for table CREDENTIAL_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO CREDENTIAL_STATUSES (CRST_ID, CRST_NAME, CRST_DESCRIPTION, CRST_ACTIVE, CRST_CODE) VALUES (NULL, 'Submitted', 'The credential is submitted for approval.', 1, '400');
INSERT INTO CREDENTIAL_STATUSES (CRST_ID, CRST_NAME, CRST_DESCRIPTION, CRST_ACTIVE, CRST_CODE) VALUES (NULL, 'Approved', 'The credential is approved.', 1, '500');
INSERT INTO CREDENTIAL_STATUSES (CRST_ID, CRST_NAME, CRST_DESCRIPTION, CRST_ACTIVE, CRST_CODE) VALUES (NULL, 'Denied', 'The credential is denied.', 1, '502');
INSERT INTO CREDENTIAL_STATUSES (CRST_ID, CRST_NAME, CRST_DESCRIPTION, CRST_ACTIVE, CRST_CODE) VALUES (NULL, 'Expired', 'The credential has expired.', 1, '404');

COMMIT;


-- -----------------------------------------------------
-- Data for table CREDENTIAL_TYPES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO CREDENTIAL_TYPES (CRTP_ID, CRTP_NAME, CRTP_DESCRIPTION, CRTP_ACTIVE, CRTP_CODE) VALUES (NULL, 'Certificate', 'Certificate', 1, '100');

COMMIT;


-- -----------------------------------------------------
-- Data for table FEE_TYPES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO FEE_TYPES (FETP_ID, FETP_NAME, FETP_DESCRIPTION, FETP_ACTIVE, FETP_CODE) VALUES (NULL, 'Static', 'Static amount for every transaction', 1, '100');
INSERT INTO FEE_TYPES (FETP_ID, FETP_NAME, FETP_DESCRIPTION, FETP_ACTIVE, FETP_CODE) VALUES (NULL, 'Percentage', 'Percentage based on the transaction amount', 1, '101');

COMMIT;


-- -----------------------------------------------------
-- Data for table ENCRYPTION_TYPES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO ENCRYPTION_TYPES (ENTP_ID, ENTP_NAME, ENTP_DESCRIPTION, ENTP_ACTIVE, ENTP_CODE) VALUES (NULL, 'MD5', 'MD5 Algorithm', 1, '100');
INSERT INTO ENCRYPTION_TYPES (ENTP_ID, ENTP_NAME, ENTP_DESCRIPTION, ENTP_ACTIVE, ENTP_CODE) VALUES (NULL, 'SHA', 'SHA Algorithm', 1, '101');

COMMIT;


-- -----------------------------------------------------
-- Data for table ACCOUNT_TYPES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO ACCOUNT_TYPES (ACTP_ID, ACTP_NAME, ACTP_DESCRIPTION, ACTP_ACTIVE, ACTP_CODE) VALUES (NULL, 'Bank', 'The account is the bank account number.', 1, '100');
INSERT INTO ACCOUNT_TYPES (ACTP_ID, ACTP_NAME, ACTP_DESCRIPTION, ACTP_ACTIVE, ACTP_CODE) VALUES (NULL, 'Card', 'The account is a bank card.', 1, '200');

COMMIT;


-- -----------------------------------------------------
-- Data for table ACCOUNT_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO ACCOUNT_STATUSES (ACST_ID, ACST_NAME, ACST_DESCRIPTION, ACST_ACTIVE, ACST_CODE) VALUES (NULL, 'Normal', 'The merchant account is normal.', 1, '200');
INSERT INTO ACCOUNT_STATUSES (ACST_ID, ACST_NAME, ACST_DESCRIPTION, ACST_ACTIVE, ACST_CODE) VALUES (NULL, 'Frozen', 'The merchant account is frozen.', 1, '400');

COMMIT;


-- -----------------------------------------------------
-- Data for table SITE_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO SITE_STATUSES (SIST_ID, SIST_NAME, SIST_DESCRIPTION, SIST_ACTIVE, SIST_CODE) VALUES (NULL, 'Created', 'Created and wait for approval', 1, '400');
INSERT INTO SITE_STATUSES (SIST_ID, SIST_NAME, SIST_DESCRIPTION, SIST_ACTIVE, SIST_CODE) VALUES (NULL, 'Approved', 'Approved to operation', 1, '500');
INSERT INTO SITE_STATUSES (SIST_ID, SIST_NAME, SIST_DESCRIPTION, SIST_ACTIVE, SIST_CODE) VALUES (NULL, 'Frozen', 'Frozen', 1, '401');
INSERT INTO SITE_STATUSES (SIST_ID, SIST_NAME, SIST_DESCRIPTION, SIST_ACTIVE, SIST_CODE) VALUES (NULL, 'Declined', 'Declined', 1, '501');

COMMIT;


-- -----------------------------------------------------
-- Data for table USER_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO USER_STATUSES (USST_ID, USST_NAME, USST_DESCRIPTION, USST_ACTIVE, USST_CODE) VALUES (NULL, 'Normal', 'Normal', 1, '100');
INSERT INTO USER_STATUSES (USST_ID, USST_NAME, USST_DESCRIPTION, USST_ACTIVE, USST_CODE) VALUES (NULL, 'Deactived', 'Deactived', 1, '400');
INSERT INTO USER_STATUSES (USST_ID, USST_NAME, USST_DESCRIPTION, USST_ACTIVE, USST_CODE) VALUES (NULL, 'Frozen', 'Frozen', 1, '501');

COMMIT;


-- -----------------------------------------------------
-- Data for table ROLES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO ROLES (ROLE_ID, ROLE_NAME, ROLE_DESCRIPTION, ROLE_ACTIVE, ROLE_CODE) VALUES (NULL, 'ROLE_ADMIN', 'System Admin', 1, '100');
INSERT INTO ROLES (ROLE_ID, ROLE_NAME, ROLE_DESCRIPTION, ROLE_ACTIVE, ROLE_CODE) VALUES (NULL, 'ROLE_MERCHANT_ADMIN', 'Merchant Admin', 1, '200');
INSERT INTO ROLES (ROLE_ID, ROLE_NAME, ROLE_DESCRIPTION, ROLE_ACTIVE, ROLE_CODE) VALUES (NULL, 'ROLE_MERCHANT_OPERATOR', 'Merchant Operator', 1, '201');

COMMIT;


-- -----------------------------------------------------
-- Data for table ORDER_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Initiated', 'The order is initiated.', 1, '400');
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Confirmed', 'The order is confirmed.', 1, '500');
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Paid', 'The order is paid.', 1, '401');
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Preparing for Shipment', 'The order is parpared for shipment, cannot be cancel at this stage.', 1, '501');
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Cancelled', 'The order is cancelled.', 1, '402');
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Shipped', 'The order is shipped.', 1, '502');
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Delivered', 'The order is delivered.', 1, '503');
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Returned', 'The order is returned.', 1, '403');
INSERT INTO ORDER_STATUSES (ORST_ID, ORST_NAME, ORST_DESCRIPTION, ORST_ACTIVE, ORST_CODE) VALUES (NULL, 'Refunded', 'The order is refunded.', 1, '504');

COMMIT;


-- -----------------------------------------------------
-- Data for table CURRENCIES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO CURRENCIES (CRCY_ID, CRCY_NAME, CRCY_DESCRIPTION, CRCY_ACTIVE, CRCY_CODE) VALUES (NULL, 'USD', 'US Dollar', 1, '100');
INSERT INTO CURRENCIES (CRCY_ID, CRCY_NAME, CRCY_DESCRIPTION, CRCY_ACTIVE, CRCY_CODE) VALUES (NULL, 'RMB', 'Chinese Yuan', 1, '101');

COMMIT;


-- -----------------------------------------------------
-- Data for table CUSTOMER_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO CUSTOMER_STATUSES (CSST_ID, CSST_NAME, CSST_DESCRIPTION, CSST_ACTIVE, CSST_CODE) VALUES (NULL, 'Normal', 'Normal', 1, '200');
INSERT INTO CUSTOMER_STATUSES (CSST_ID, CSST_NAME, CSST_DESCRIPTION, CSST_ACTIVE, CSST_CODE) VALUES (NULL, 'Frozen', 'Frozen', 1, '500');

COMMIT;


-- -----------------------------------------------------
-- Data for table CUSTOMER_LOGIN_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO CUSTOMER_LOGIN_STATUSES (CSLS_ID, CSLS_NAME, CSLS_DESCRIPTION, CSLS_ACTIVE, CSLS_CODE) VALUES (NULL, 'Frozen', 'Frozen', 1, '500');
INSERT INTO CUSTOMER_LOGIN_STATUSES (CSLS_ID, CSLS_NAME, CSLS_DESCRIPTION, CSLS_ACTIVE, CSLS_CODE) VALUES (NULL, 'Normal', 'Normal', 1, '200');

COMMIT;


-- -----------------------------------------------------
-- Data for table PAYMENT_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO PAYMENT_STATUSES (PYST_ID, PYST_NAME, PYST_DESCRIPTION, PYST_ACTIVE, PYST_CODE) VALUES (NULL, 'Approved', 'Approved', 1, '500');
INSERT INTO PAYMENT_STATUSES (PYST_ID, PYST_NAME, PYST_DESCRIPTION, PYST_ACTIVE, PYST_CODE) VALUES (NULL, 'Declined', 'Declined', 1, '501');

COMMIT;


-- -----------------------------------------------------
-- Data for table PAYMENT_TYPES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO PAYMENT_TYPES (PYTP_ID, PYTP_NAME, PYTP_DESCRIPTION, PYTP_ACTIVE, PYTP_CODE) VALUES (NULL, 'Credit Card', 'Credit Card', 1, '100');
INSERT INTO PAYMENT_TYPES (PYTP_ID, PYTP_NAME, PYTP_DESCRIPTION, PYTP_ACTIVE, PYTP_CODE) VALUES (NULL, 'Debit Card', 'Debit Card', 1, '101');

COMMIT;


-- -----------------------------------------------------
-- Data for table SHIPMENT_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO SHIPMENT_STATUSES (SHST_ID, SHST_NAME, SHST_DESCRIPTION, SHST_ACTIVE, SHST_CODE) VALUES (NULL, 'Shipped', 'Shipped', 1, '500');
INSERT INTO SHIPMENT_STATUSES (SHST_ID, SHST_NAME, SHST_DESCRIPTION, SHST_ACTIVE, SHST_CODE) VALUES (NULL, 'Delivered', 'Delivered', 1, '501');
INSERT INTO SHIPMENT_STATUSES (SHST_ID, SHST_NAME, SHST_DESCRIPTION, SHST_ACTIVE, SHST_CODE) VALUES (NULL, 'Lost', 'Lost', 1, '400');
INSERT INTO SHIPMENT_STATUSES (SHST_ID, SHST_NAME, SHST_DESCRIPTION, SHST_ACTIVE, SHST_CODE) VALUES (NULL, 'Damaged', 'Damaged', 1, '401');

COMMIT;


-- -----------------------------------------------------
-- Data for table ADMINISTRATORS
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO ADMINISTRATORS (ADMN_ID, ADMN_USERNAME, ADMN_PASSWORD, ADMN_FIRST_NAME, ADMN_LAST_NAME, ADMN_EMAIL, ADMN_PROFILE_IMAGE, ADMN_REMARK, ADMN_CREATED_TIME, ADMN_UPDATED_TIME, ADMN_ACTIVE, ADMN_ROLE_ID) VALUES (NULL, 'admin', 'admin', 'Admin', 'Admin', 'ironaire@gmail.com', NULL, NULL, NOW(), NULL, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table RETURN_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO RETURN_STATUSES (RTST_ID, RTST_NAME, RTST_DESCRIPTION, RTST_ACTIVE, RTST_CODE) VALUES (NULL, 'Shipped', 'Shipped', 1, '400');
INSERT INTO RETURN_STATUSES (RTST_ID, RTST_NAME, RTST_DESCRIPTION, RTST_ACTIVE, RTST_CODE) VALUES (NULL, 'Delivered', 'Delivered', 1, '401');

COMMIT;


-- -----------------------------------------------------
-- Data for table REFUND_STATUSES
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO REFUND_STATUSES (RFST_ID, RFST_NAME, RFST_DESCRIPTION, RFST_ACTIVE, RFST_CODE) VALUES (NULL, 'Issued', 'Issued', 1, '500');
INSERT INTO REFUND_STATUSES (RFST_ID, RFST_NAME, RFST_DESCRIPTION, RFST_ACTIVE, RFST_CODE) VALUES (NULL, 'Funded', 'Funded', 1, '501');

COMMIT;
