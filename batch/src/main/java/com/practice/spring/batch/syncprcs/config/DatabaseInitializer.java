package com.practice.spring.batch.syncprcs.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@Profile("local-db")
public class DatabaseInitializer {

    @Bean
    DataSourceInitializer sourceDbInit(
            @Qualifier("sourceDbDatasource") DataSource dataSource,
            @Value("${spring.datasource.source-db.init-script}") Resource initScript) {

        return initializer(dataSource, initScript);
    }

    @Bean
    DataSourceInitializer targetDbInit(
            @Qualifier("destDbDatasource") DataSource dataSource,
            @Value("${spring.datasource.dest-db.init-script}") Resource initScript) {

        return initializer(dataSource, initScript);
    }

    private DataSourceInitializer initializer(DataSource dataSource, Resource initScript) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setEnabled(true);

        ResourceDatabasePopulator populate = new ResourceDatabasePopulator();
        populate.addScript(initScript);
        populate.setSeparator("//");
        initializer.setDatabasePopulator(populate);

        ResourceDatabasePopulator cleaner = new ResourceDatabasePopulator();
        initializer.setDatabaseCleaner(cleaner);

        return initializer;
    }
}
