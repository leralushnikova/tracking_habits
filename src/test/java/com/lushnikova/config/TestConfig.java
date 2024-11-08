package com.lushnikova.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

@TestConfiguration
@TestPropertySource(value = "classpath:application.yml")
public class TestConfig {
    @Bean
    public DataSource dataSource(Environment environment) {
        DataSourceBuilder<?> dSB = DataSourceBuilder.create();
        dSB.driverClassName(environment.getProperty("spring.datasource.driver"));
        dSB.url(environment.getProperty("spring.datasource.url"));
        dSB.username(environment.getProperty("spring.datasource.username"));
        dSB.password(environment.getProperty("spring.datasource.password"));

        return dSB.build();
    }
}
