package com.practice.spring.batch.syncprcs.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.source-db")
    public DataSourceProperties dataSourcePropertiesSourceDb() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.dest-db")
    public DataSourceProperties dataSourcePropertiesDestDb() {
        return new DataSourceProperties();
    }

    @Bean("sourceDbDatasource")
    @Primary
    @ConfigurationProperties("spring.datasource.source-db.hikari")
    public DataSource sourceDbDatasource(@Qualifier("dataSourcePropertiesSourceDb") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean("destDbDatasource")
    @ConfigurationProperties("spring.datasource.dest-db.hikari")
    public DataSource destDbDatasource(@Qualifier("dataSourcePropertiesDestDb") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
}
