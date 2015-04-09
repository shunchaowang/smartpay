package com.lambo.smartpay.core.util;

/**
 * Created by swang on 2/11/2015.
 */
public abstract class ResourceProperties {

    public static final String SESSION_INFO = "session_info";
    public static final Integer PAGE_SIZE = 10;
    public static final int ENCRYPTION_KEY_LENGTH = 8;
    public static final String MERCHANT_STATUS_FROZEN_CODE = "500";
    public static final String MERCHANT_STATUS_NORMAL_CODE = "200";
    public static final String SITE_STATUS_CREATED_CODE = "400";
    public static final String SITE_STATUS_APPROVED_CODE = "500";
    public static final String SITE_STATUS_FROZEN_CODE = "401";
    public static final String SITE_STATUS_DECLINED_CODE = "501";
    public static final String SPRING_PROPERTIES_FILE = "spring.properties";
    public static final String PROFILES_DEV = "dev";
    public static final String PROFILES_PROD = "prod";
    // user role code
    public static final String ROLE_ADMIN_CODE = "100";
    public static final String ROLE_MERCHANT_ADMIN_CODE = "200";
    public static final String ROLE_MERCHANT_OPERATOR_CODE = "201";
    // initial password for new user
    public static final String INITIAL_PASSWORD = "asdf1234";
    public static final String USER_STATUS_NORMAL_CODE = "100";
    public static final String USER_STATUS_DEACTIVATED_CODE = "400";
    public static final String USER_STATUS_FROZEN_CODE = "501";

    // order status
    public static final String ORDER_STATUS_PAID_CODE = "401";
    public static final String ORDER_STATUS_SHIPPED_CODE = "502";
    public static final String SHIPMENT_STATUS_SHIPPED_CODE = "500";

    public enum JpaOrderDir {
        ASC("ASC"),
        DESC("DESC");

        private final String dir;

        JpaOrderDir(String dir) {
            this.dir = dir;
        }

        public String getDir() {
            return dir;
        }
    }
}
