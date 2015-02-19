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
@PropertySource(
        value = {"classpath: application-dev.properties, classpath: application-${envTarget}.properties"},
        ignoreResourceNotFound = true)
@EnableScheduling
@EnableAspectJAutoProxy
@EnableCaching
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("smtp.host"));
        mailSender.setPort(env.getProperty("smtp.port", Integer.class));
        mailSender.setProtocol(env.getProperty("smtp.protocol"));
        mailSender.setUsername(env.getProperty("smtp.username"));
        mailSender.setPassword(env.getProperty("smtp.password"));

        Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);

        mailSender.setJavaMailProperties(javaMailProps);

        return mailSender;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
