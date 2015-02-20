package com.lambo.smartpay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

/**
 * Configure common service layer beans such as PropertySourcesPlaceholderConfigurer, JavaMailSender and CacheManager.
 * We need to exclude scanning of ecs.* package otherwise the
 * java.lang.IllegalArgumentException: A ServletContext is required to configure default servlet handling will be
 * thrown when running junit test.
 *
 * Created by swang on 2/12/2015.
 */
@Configuration
@ComponentScan(basePackages = {"com.lambo.smartpay"}/*,
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = {"com.lambo.smartpay.ecs.*"})*/)
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
public class AppConfig {

}
