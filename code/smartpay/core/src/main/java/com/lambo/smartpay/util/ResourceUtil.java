package com.lambo.smartpay.util;

/**
 * Created by swang on 2/11/2015.
 */
public interface ResourceUtil {

    public static final String SESSION_INFO = "session_info";

    public static final Integer PAGE_SIZE = 10;

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
