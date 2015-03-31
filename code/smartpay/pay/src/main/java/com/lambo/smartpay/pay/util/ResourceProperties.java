package com.lambo.smartpay.pay.util;

import com.lambo.smartpay.core.util.PropertiesLoader;

import java.util.Properties;

/**
 * Created by swang on 3/28/2015.
 */
public abstract class ResourceProperties {

    public static final String PAY_PROPERTIES_FILE = "pay.properties";
    public static final String HOOPPAY_URL_KEY = "hoopPayUrl";
    public static final String MERCHANT_ID_KEY = "merchantId";
    public static final String MER_KEY_KEY = "merKey";
    public static final String REFERER_KEY = "referer";
    public static final String SHOP_NAME_KEY = "shopName";

    public static final String ORDER_STATUS_INITIATED_CODE = "400";
    public static final String ORDER_STATUS_SHIPPED_CODE = "502";
    public static final String ORDER_STATUS_PAID_CODE = "401";
    public static final String CUSTOMER_STATUS_NORMAL_CODE = "200";
    public static final String CURRENCY_USD_NAME ="USD";
    public static final String CURRENCY_RMB_NAME ="RMB";

}
