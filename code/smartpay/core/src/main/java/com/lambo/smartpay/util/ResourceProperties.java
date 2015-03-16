package com.lambo.smartpay.util;

/**
 * Created by swang on 2/11/2015.
 */
public abstract class ResourceProperties {

    public static final String SESSION_INFO = "session_info";
    public static final Integer PAGE_SIZE = 10;
    public static final int ENCRYPTION_KEY_LENGTH = 8;
    public static final String MERCHANT_STATUS_FROZEN_CODE = "500";
    public static final String MERCHANT_STATUS_NORMAL_CODE = "200";
    public static final String SPRING_PROPERTIES_FILE = "spring.properties";
    public static final String PROFILES_DEV = "dev";
    public static final String PROFILES_PROD = "prod";

    // user role code
    public static final String ROLE_ADMIN_CODE = "100";
    public static final String ROLE_MERCHANT_ADMIN_CODE = "200";
    public static final String ROLE_MERCHANT_OPERATOR_CODE = "201";

    // user status code
    public static final String USER_STATUS_NORMAL_CODE = "100";

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
