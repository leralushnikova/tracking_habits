package com.lushnikova.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Класс конфигурации базы данных
 */
@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource source(Environment environment) {
        DataSourceBuilder<?> dSB = DataSourceBuilder.create();
        dSB.driverClassName(environment.getProperty("spring.datasource.driver"));
        dSB.url(environment.getProperty("spring.datasource.url"));
        dSB.username(environment.getProperty("spring.datasource.username"));
        dSB.password(environment.getProperty("spring.datasource.password"));

        return dSB.build();
    }
}
