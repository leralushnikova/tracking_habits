package com.lushnikova.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Configuration
@ComponentScan(value = "com.lushnikova")
@PropertySource("classpath:application.yml")
public class AppConfig{

    private final Environment environment;

    @Autowired
    public AppConfig(Environment environment) {
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
