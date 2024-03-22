package edu.hawaii.its.holiday.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DataInitializationConfig {

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(holidayDatabasePopulator());
        return initializer;
    }

    @Bean
    public DatabasePopulator holidayDatabasePopulator() {
        ResourceDatabasePopulator populator = new HolidayDatabasePopulator();
        populator.addScript(new ClassPathResource("data-two.sql"));
        // populator.addScript(new ClassPathResource("data-one.sql"));
        return populator;
    }
}
