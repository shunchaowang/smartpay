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
    public static String SPRING_PROPERTIES_FILE = "spring.properties";
    public static String PROFILES_DEV = "dev";
    public static String PROFILES_PROD = "prod";

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
