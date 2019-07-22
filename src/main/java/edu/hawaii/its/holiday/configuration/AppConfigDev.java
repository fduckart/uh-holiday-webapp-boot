package edu.hawaii.its.holiday.configuration;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;

@Profile(value = { "default", "dev" })
@Configuration
@ComponentScan(basePackages = "edu.hawaii.its.holiday")
@EntityScan(basePackages = { "edu.hawaii.its.holiday.type" })
@PropertySource(value = { "classpath:custom.properties" })
public class AppConfigDev {

    private static final Log logger = LogFactory.getLog(AppConfigDev.class);

    @Value("${spring.datasource.initialize}")
    private boolean springDatasourceInitialize;

    @PostConstruct
    public void init() {
        logger.info("init starting");
        logger.info("init; springDatasourceInitialize: " + springDatasourceInitialize);
        Assert.isTrue(springDatasourceInitialize,
                "Property 'spring.datasource.initialize' should be true.");
        logger.info("init finished");
    }

}
