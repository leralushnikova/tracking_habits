package com.lushnikova.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс конфигурации базы данных
 */
@Configuration
@PropertySource("classpath:application.yml")
public class DatabaseConfig {

    private final Environment environment;

    @Autowired
    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }

    private final String URL = "url";
    private final String USER = "user";
    private final String PASSWORD = "password";
    private final String DRIVER = "driver";

    @Bean
    public Connection getConnectionFromDriverManager() throws SQLException, ClassNotFoundException {
        Class.forName(environment.getProperty(DRIVER));
        return DriverManager.getConnection(environment.getProperty(URL), environment.getProperty(USER), environment.getProperty(PASSWORD));
    }
}
