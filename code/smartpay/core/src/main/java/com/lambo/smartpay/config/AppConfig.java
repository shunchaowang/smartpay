package com.lambo.smartpay.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configure common service layer beans such as PropertySourcesPlaceholderConfigurer,
 * JavaMailSender and CacheManager.
 * We need to exclude scanning of ecs.* package otherwise the
 * java.lang.IllegalArgumentException: A ServletContext is required to configure default servlet
 * handling will be
 * thrown when running junit test.
 * <p/>
 * Created by swang on 2/12/2015.
 */
@Configuration
@ComponentScan(basePackages = {"com.lambo.smartpay"}/*,
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {"com.lambo
        .smartpay.ecs.*"})*/)
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
public class AppConfig {

}
