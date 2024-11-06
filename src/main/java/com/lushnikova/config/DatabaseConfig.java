package com.lushnikova.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс конфигурации базы данных
 */
@Configuration
public class DatabaseConfig {

    private final Environment environment;

    @Autowired
    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }

    private DataSource source() {
        DataSourceBuilder<?> dSB = DataSourceBuilder.create();
        dSB.driverClassName(environment.getProperty("spring.datasource.driver"));
        dSB.url(environment.getProperty("spring.datasource.url"));
        dSB.username(environment.getProperty("spring.datasource.username"));
        dSB.password(environment.getProperty("spring.datasource.password"));

        return dSB.build();
    }

    @Bean
    public Connection getConnectionFromDriverManager() throws SQLException{
        return source().getConnection();
    }
}
