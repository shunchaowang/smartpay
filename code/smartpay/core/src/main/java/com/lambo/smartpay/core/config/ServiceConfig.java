package com.lambo.smartpay.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by swang on 3/3/2015.
 */
@Configuration
@ComponentScan({"com.lambo.smartpay.core.service"})
public class ServiceConfig {
}
