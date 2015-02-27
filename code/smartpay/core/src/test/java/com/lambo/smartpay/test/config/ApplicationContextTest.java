package com.lambo.smartpay.test.config;

import com.lambo.smartpay.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;

/**
 * Created by swang on 2/20/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
// profile related config will be loaded by ActiveProfiles
@ActiveProfiles(profiles = {"dev"})
public class ApplicationContextTest {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContextTest.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testContextBeans() {
        LOG.debug("Testing all beans are initialized successfully.");
        assertNotNull(applicationContext);
        assertNotNull(cacheManager);
        assertNotNull(mailSender);
        assertNotNull(transactionManager);
        assertNotNull(entityManagerFactory);
        assertNotNull(dataSource);
    }
}
