package com.lambo.smartpay.pay.util;

import com.lambo.smartpay.core.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by swang on 3/28/2015.
 */
public class PayConfiguration {

    private static Logger logger = LoggerFactory.getLogger(PayConfiguration.class);
    private static PayConfiguration instance = null;
    private static Properties properties = null;

    private PayConfiguration() {
        logger.info("Loading properties from " + ResourceProperties.PAY_PROPERTIES_FILE);
        PropertiesLoader loader = new PropertiesLoader();
        Properties properties = loader.load(ResourceProperties.PAY_PROPERTIES_FILE);
    }

    public static PayConfiguration getInstance() {
        synchronized (PayConfiguration.class) {
            if (instance == null) {
                instance = new PayConfiguration();
            }
        }
        return instance;
    }

    public String getValue(String name) {
        return properties.getProperty(name);
    }

}
